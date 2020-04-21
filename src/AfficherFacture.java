import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.text.SimpleDateFormat;

public class AfficherFacture {
	/**
	 * affiche la facture de chacune des clients avec les erreurs
	 * 
	 * @param fileNameAndExtension
	 */

	private double pourcentageTaxesTPS = 0.09975, pourcentageTaxesTVQ = 0.05;
	private String strJour, tempsHeures, listeClients = "", listePlats = "", listeCommandes = "";
	private String[] arrClients, arrPlats, arrCommandes;

	public AfficherFacture(String fileNameAndExtension) {
		try {
			// Ceci crée un nouveau fichier avec le nom complet du fichier
			File file = new File(fileNameAndExtension);
			// créer arrClients, arrPlats et arrCommandes
			Scanner inputStream = new Scanner(file);
			int typeData = 0;
			int nbLigneCourante = 0;
			while (inputStream.hasNext()) {
				String lineRead = inputStream.nextLine();
				// enleve les spaces de trop dans la ligne
				lineRead = lineRead.trim().replaceAll("\\s+", " ");
				nbLigneCourante += 1;
				if (lineRead.contains("Clients :")) {
					typeData = 1;
				} else if (lineRead.contains("Plats :")) {
					typeData = 2;
				} else if (lineRead.contains("Commandes :")) {
					typeData = 3;
				} else if (typeData == 1 && !(lineRead.contains("Clients :"))) {
					if (verifierNomClient(lineRead, 1)) {
						listeClients += lineRead + ";";
					} else {
						afficherMessageErrPourSection(nbLigneCourante, lineRead, "client");
					}
				} else if (typeData == 2 && !(lineRead.contains("Plats :"))) {
					if (verifierLignePlatEtComm(lineRead, 2)) {
						listePlats += lineRead + ";";
					} else {
						afficherMessageErrPourSection(nbLigneCourante, lineRead, "plat");
					}
				} else if (typeData == 3 && !(lineRead.contains("Commandes :")) && !(lineRead.contains("Fin"))) {
					if (verifierLignePlatEtComm(lineRead, 3)) {
						listeCommandes += lineRead + ";";
					} else {
						afficherMessageErrPourSection(nbLigneCourante, lineRead, "commande");
					}
				}
			}
			if (listeClients != "" && listePlats != "" && listeCommandes != "") {
				creerUnFichierChqClinet();
			} else {
				System.out.println("Le fichier est vide dans une ou plusieurs sections (plat, client, Commande).");
			}
		} catch (FileNotFoundException e) {
			System.out.println("Le Fichier " + fileNameAndExtension + " n'existe pas ");
		}
	}

	private void afficherMessageErrPourSection(int nbLigneCourante , String lineRead, String typeDonnee ) {
		System.out.println("**********************************************************");
		System.out.println("Il y a plus ou moins que trois elements dans la ligne " + nbLigneCourante
				+ " pour la " +typeDonnee  + " : " + lineRead);
		System.out.println("**********************************************************");
	}
	/**
	 * Cette méthode crée un fichier pour chaque client
	 * 
	 * @param listeClients
	 * @param listePlats
	 * @param listeCommandes
	 */
	private void creerUnFichierChqClinet() {
		arrClients = listeClients.split(";");
		arrPlats = listePlats.split(";");
		arrCommandes = listeCommandes.split(";");
		Client[] listeClient = creerChqClient(arrClients.length);

		SimpleDateFormat formatterDate = new SimpleDateFormat("dd-M-yyyy");
		strJour = formatterDate.format(new Date());
		SimpleDateFormat formatHeure = new SimpleDateFormat("HH");
		tempsHeures = formatHeure.format(new Date());
		
		String recuClientFinal = (strJour + " " + tempsHeures + "h");
		for (int i = 0; i < listeClient.length; i++) {
			Commande[] commandeArray = listeClient[i].getCommande();
			if (commandeArray.length != 0) {
				double prixTotalPartiel = calculerPrixTotalPartiel(commandeArray);
				if (prixTotalPartiel <= 0) {
				} else {
					recuClientFinal += afficherUnClient(listeClient[i], prixTotalPartiel);
				}
			} else {
				System.out.println("Le client " + listeClient[i].getNomClient() + " n'a rien acheté.");
			}
		}
		System.out.println(recuClientFinal);
		creerFichierFacture(recuClientFinal);
	}

	/**
	 * Cette methode crée un facture en format txt
	 * 
	 * @param recuClientFinal
	 */
	private void creerFichierFacture(String recuClientFinal) {
		try {
			FileWriter myWriter = new FileWriter("Facture_du_" + strJour + "_" + tempsHeures + "h" + ".txt");
			myWriter.write(recuClientFinal);
			myWriter.close();
		} catch (IOException e) {
			System.out.println("Le fichier de sortie n'a pas peut etre crée ");
			e.printStackTrace();
		}
	}

	/**
	 * Vérifie si le client continent des chiffres
	 * 
	 * @param lineRead
	 * @return vrai ou faux
	 */
	private boolean verifierNomClient(String lineRead, int nbDonne) {
		boolean verifierNomClient = true;
		String[] arrLigneSplited = lineRead.split(" ");
		if (lineRead.matches(".*\\d.*") || lineRead.trim().length() == 0 || arrLigneSplited.length > nbDonne) {
			verifierNomClient = false;
		}
		return verifierNomClient;
	}

	/**
	 * Vérifie si la commande et les plats ont des données illégaux
	 * 
	 * @param lineRead
	 * @return vrai ou faux
	 */
	private boolean verifierLignePlatEtComm(String lineRead, int nbDonne) {
		boolean verifierLigne = true;
		String[] arrLigneSplited = lineRead.split(" ");
		if (arrLigneSplited.length != nbDonne) {
			verifierLigne = false;
		}
		return verifierLigne;
	}

	/**
	 * Crée la facture pour chaque client
	 * 
	 * @param nbClients
	 * @return
	 */

	private Client[] creerChqClient(int nbClients) {
		Client[] arrayClient = null;
		ArrayList<Client> clientArrayList = new ArrayList<Client>();
		for (int i = 0; i < arrClients.length; i++) {
			String nomClient = arrClients[i];
			// Ce bloque crée tous les nomPlat , nbPlat et nomClient
			ArrayList<String> nomPlatArryList = new ArrayList<String>();
			ArrayList<Integer> nbPlatArryList = new ArrayList<Integer>();
			ArrayList<String> nomClientArryList = new ArrayList<String>();
			for (int j = 0; j < arrCommandes.length; j++) {
				String arrCommandeCurrent = arrCommandes[j];
				String[] arrCommandeSplited = arrCommandeCurrent.split(" ");
				if (nomClient.equals(arrCommandeSplited[0])) {
					nomPlatArryList.add(arrCommandeSplited[1]);
					nbPlatArryList.add(Integer.parseInt(arrCommandeSplited[2]));
					nomClientArryList.add(arrCommandeSplited[0]);
				}
			}
			Commande[] CommandeDeChqClient = avoirToutesCommandes(nomPlatArryList, nbPlatArryList, nomClientArryList);
			// Cette ligne crée le Client current
			clientArrayList.add(new Client(nomClient, CommandeDeChqClient));
		}
		arrayClient = clientArrayList.toArray(new Client[0]);
		return arrayClient;
	}

	private Commande[] avoirToutesCommandes(ArrayList<String> nomPlatArryList, ArrayList<Integer> nbPlatArryList,
			ArrayList<String> nomClientArryList) {
		Plats[] tousLesPlatsMenu = tousPlats(arrPlats);
		String[] nomPlatCommandeClient = nomPlatArryList.toArray(new String[0]);
		int[] quantiteCommandeClient = nbPlatArryList.stream().mapToInt(Integer::intValue).toArray();
		String[] nomClientCommandeClient = nomClientArryList.toArray(new String[0]);
		// Ce bloque crée toutes les commandes avec
		ArrayList<Commande> commandeArrayList = new ArrayList<Commande>();
		for (int j = 0; j < nomPlatCommandeClient.length; j++) {
			for (int x = 0; x < tousLesPlatsMenu.length; x++) {
				if (tousLesPlatsMenu[x].getNomPlats().equals(nomPlatCommandeClient[j])) {
					if (quantiteCommandeClient[j] <= 0) {
						System.out.print("La commande : " + nomClientCommandeClient[j] + " "
								+ tousLesPlatsMenu[x].getNomPlats() + " " + quantiteCommandeClient[j]);
						System.out.println(" ne peut pas avoir une quanitité inferieur ou égale à 0");
					} else {

						commandeArrayList.add(new Commande(tousLesPlatsMenu[x], quantiteCommandeClient[j],
								nomClientCommandeClient[j]));
					}
				}
			}
		}
		return commandeArrayList.toArray(new Commande[0]);
	}

	private Plats[] tousPlats(String[] arrPlats) {
		// Ce bloque crée chaque plat existant
		ArrayList<Plats> platsArrayList = new ArrayList<Plats>();
		for (int j = 0; j < arrPlats.length; j++) {
			String arrPlatCurrent = arrPlats[j];
			String[] arrPlatSplited = arrPlatCurrent.split(" ");
			String nomPlat = arrPlatSplited[0];
			double prixPlat = Double.parseDouble(arrPlatSplited[1]);
			platsArrayList.add(new Plats(nomPlat, prixPlat));
		}
		// La prochaines ligne tranforme Arraylist Plat en array de Plat
		Plats[] platchqCommandeDuClient = platsArrayList.toArray(new Plats[0]);
		return platchqCommandeDuClient;
	}

	private String afficherUnClient(Client client, double prixTotalPartiel) {
		String commandePourUnClient = "";
		Commande[] commandeArray = client.getCommande();
		commandePourUnClient = "\n____________________________________\n";
		commandePourUnClient += "*********************\n";
		commandePourUnClient += "Nom du Client :  " + client.getNomClient() + "\n";
		for (int i = 0; i < commandeArray.length; i++) {
			commandePourUnClient += commandeArray[i].getPlats().getNomPlats() + " X" + commandeArray[i].getNbPlats()
					+ "\n";
			commandePourUnClient += "Prix par plat : " + commandeArray[i].getPlats().getPrixPlats() + "\n";
			commandePourUnClient += "*********************" + "\n";
		}
		commandePourUnClient += "Total Partiel : " + prixTotalPartiel + "$\n";
		commandePourUnClient += "Total "
				+ arrondir(caluclerTotal(prixTotalPartiel, calucleraxes(prixTotalPartiel, pourcentageTaxesTPS),
						calucleraxes(prixTotalPartiel, pourcentageTaxesTVQ)), 2)
				+ "$\n";
		return commandePourUnClient;
	}

	// Cette section est pour calculer les taxes et bien plus

	private double calucleraxes(double totalPartiel, double pourcentageTax) {
		return totalPartiel * pourcentageTax;
	}

	private double caluclerTotal(double totalPartiel, double tps, double tvq) {
		return totalPartiel + tps + tvq;
	}

	private double calculerPrixTotalPartiel(Commande[] commandeArray) {
		double prixTotalPartiel = 0;
		for (int j = 0; j < commandeArray.length; j++) {
			prixTotalPartiel += commandeArray[j].getPlats().getPrixPlats() * commandeArray[j].getNbPlats();
		}
		return prixTotalPartiel;
	}

	private static double arrondir(double valeurPorArrondir, int chiffresApresVirgule) {
		if (chiffresApresVirgule < 0) {
			throw new IllegalArgumentException();
		}
		long factor = (long) Math.pow(10, chiffresApresVirgule);
		valeurPorArrondir = valeurPorArrondir * factor;
		long tmp = Math.round(valeurPorArrondir);
		return (double) tmp / factor;
	}
}