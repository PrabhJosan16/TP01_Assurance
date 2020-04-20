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
<<<<<<< HEAD
	 * affiche la facture de chacune des clients avec les erreurs 
=======
	 * affiche la facture de chacune des clients avec les erreurs
	 * 
>>>>>>> dev
	 * @param fileNameAndExtension
	 */

	public double pourcentageTaxesTPS = 0.09975;
	public double pourcentageTaxesTVQ = 0.05;

	public AfficherFacture(String fileNameAndExtension) {
		String listeClients = "";
		String listePlats = "";
		String listeCommandes = "";

		try {
			// Ceci crée un nouveau fichier avec le nom complet du fichier
			File file = new File(fileNameAndExtension);
			// créer arrClients, arrPlats et arrCommandes
			Scanner inputStream = new Scanner(file); 
			int typeData = 0;
			int nbLigneCourante = 0;
			while (inputStream.hasNext()) {
				String lineRead = inputStream.nextLine();
				lineRead = lineRead.trim().replaceAll("\\s+", " "); // enleve les spaces de trop dans la ligne
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
						System.out.println("**********************************************************");
						System.out.println("Le nom du client est incorrect dans la ligne " + nbLigneCourante
								+ " pour le client : " + lineRead);
						System.out.println("**********************************************************");
					}
				} else if (typeData == 2 && !(lineRead.contains("Plats :"))) {
					if (verifierLignePlatEtComm(lineRead, 2)) {
						listePlats += lineRead + ";";
					} else {
						System.out.println("**********************************************************");
						System.out.println("Il y a plus ou moins que deux elements dans la ligne " + nbLigneCourante
								+ " pour le plat : " + lineRead);
						System.out.println("**********************************************************");
					}
				} else if (typeData == 3 && !(lineRead.contains("Commandes :")) && !(lineRead.contains("Fin"))) {
					if (verifierLignePlatEtComm(lineRead, 3)) {
						listeCommandes += lineRead + ";";
					} else {
						System.out.println("**********************************************************");
						System.out.println("Il y a plus ou moins que trois elements dans la ligne " + nbLigneCourante
								+ " pour la commande : " + lineRead);
						System.out.println("**********************************************************");
					}
				}
			}

			if (listeClients != "" || listePlats != "" || listeCommandes != "") {
				creerUnFichierChqClinet(listeClients, listePlats, listeCommandes);
			} else {
				System.out.println("Le fichier est vide dans une ou plusieurs sections (plat, client, Commande).");
			}
		} catch (FileNotFoundException e) {
			System.out.println("Le Fichier " + fileNameAndExtension + " n'existe pas ");
		}
	}

	/**
	 * Cette méthode crée un fichier pour chaque client
	 * 
	 * @param listeClients
	 * @param listePlats
	 * @param listeCommandes
	 */
	public void creerUnFichierChqClinet(String listeClients, String listePlats, String listeCommandes) {
		String[] arrClients = listeClients.split(";");
		String[] arrPlats = listePlats.split(";");
		String[] arrCommandes = listeCommandes.split(";");
		int nbClients = arrClients.length;
		Client[] listeClient = creerChqClient(arrClients, arrPlats, arrCommandes, nbClients);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String jourAchatStr = formatter.format(new Date());
		System.out.println( jourAchatStr );
		String recuClientFinal = jourAchatStr + "\n";
		
		for (int i = 0; i < listeClient.length; i++) {
			Commande[] commandeArray = listeClient[i].getCommande();
			
			if (commandeArray.length != 0) {
				double prixTotalPartiel = calculerPrixTotalPartiel(commandeArray);
				if (prixTotalPartiel <= 0) {
					System.out.println("Le prix total pour le client " + listeClient[i].getNomClient()
							+ "  ne peut pas être 0 ou un chiffre négative");
				} else {
					recuClientFinal += afficherUnClient(listeClient[i], prixTotalPartiel);
					System.out.println(afficherUnClient(listeClient[i], prixTotalPartiel));
				}
			} else {
				System.out.println("Le client " + listeClient[i].getNomClient() + " n'a rien acheté.");
			}

		}

		SimpleDateFormat formatter2 = new SimpleDateFormat("dd-M-yyyy");
		String strJour = formatter2.format(new Date());
		SimpleDateFormat formatHours = new SimpleDateFormat("HH");
		String tempsHeures = formatHours.format(new Date());

		try {
			FileWriter myWriter = new FileWriter("Facture_du_" + strJour + "_" + tempsHeures + "h_" + ".txt");
			myWriter.write(recuClientFinal);
			myWriter.close();
		} catch (IOException e) {
			System.out.println("Le fichier de sortie n'a pas peut etre crée ");
			e.printStackTrace();
		}

	}

	/**
	 * Vérifie si le client continent des chiffres
<<<<<<< HEAD
=======
	 * 
>>>>>>> dev
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
<<<<<<< HEAD
	 *  Vérifie si la commande et les plats ont des données illégaux
=======
	 * Vérifie si la commande et les plats ont des données illégaux
	 * 
>>>>>>> dev
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
<<<<<<< HEAD
=======
	 * 
>>>>>>> dev
	 * @param arrClients
	 * @param arrPlats
	 * @param arrCommandes
	 * @param nbClients
	 * @return Facture[] arrayFacture
	 */

	private Client[] creerChqClient(String[] arrClients, String[] arrPlats, String[] arrCommandes, int nbClients) {
		Client[] arrayClient = null;
		ArrayList<Client> clientArrayList = new ArrayList<Client>();
		for (int i = 0; i < arrClients.length; i++) {
			String nomClient = arrClients[i];
			Plats[] tousLesPlatsMenu = tousPlats(arrPlats);
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
			Commande[] CommandeDeChqClient = commandeArrayList.toArray(new Commande[0]);
			// Cette ligne crée le Client current
			clientArrayList.add(new Client(nomClient, CommandeDeChqClient));
		}
		arrayClient = clientArrayList.toArray(new Client[0]);
		return arrayClient;
	}

	public Plats[] tousPlats(String[] arrPlats) {
		
		// Ce bloque crée chaque plat existant
		ArrayList<Plats> platsArrayList = new ArrayList<Plats>();
		// int nbPlats = arrPlats.length ;
		for (int j = 0; j < arrPlats.length; j++) {
			String arrPlatCurrent = arrPlats[j];
			String[] arrPlatSplited = arrPlatCurrent.split(" ");
			String nomPlat = arrPlatSplited[0];
			double prixPlat = Double.parseDouble(arrPlatSplited[1]);
			platsArrayList.add(new Plats(nomPlat, prixPlat));
		}
		// The next tranforms array list Plat into an array of Plat
		Plats[] platchqCommandeDuClient = platsArrayList.toArray(new Plats[0]);
		return platchqCommandeDuClient;
	}

<<<<<<< HEAD
	
	/**
	 *  Calcule le prix totale
	 * @param arrCommandes
	 * @param arrPlats
	 * @param nomClient
	 * @return prixTotal
	 */
	private double calculerCoutTotal(String[] arrCommandes, String[] arrPlats, String nomClient) {
=======
	public double calculerPrixTotalPartiel(Commande[] commandeArray) {
>>>>>>> dev

		double prixTotalPartiel = 0;
		for (int j = 0; j < commandeArray.length; j++) {
			prixTotalPartiel += commandeArray[j].getPlats().getPrixPlats() * commandeArray[j].getNbPlats();
		}
		return prixTotalPartiel;
	}

	public String afficherUnClient(Client client, double prixTotalPartiel) {
		String commandePourUnClient = "";
		Commande[] commandeArray = client.getCommande();

	
		
		
		commandePourUnClient = "____________________________________\n";
		
		commandePourUnClient += "*********************\n";
		commandePourUnClient += "Nom du Client :  " + client.getNomClient() + "\n";
		for (int i = 0; i < commandeArray.length; i++) {
			commandePourUnClient += commandeArray[i].getPlats().getNomPlats() + " X" + commandeArray[i].getNbPlats()
					+ "\n";
			commandePourUnClient += "Prix par plat : " + commandeArray[i].getPlats().getPrixPlats() + "\n";
			commandePourUnClient += "*********************" + "\n";
		}
		commandePourUnClient += "Total Partiel : " + prixTotalPartiel + "\n";
		commandePourUnClient += "Total "
				+ arrondir(caluclerTotal(prixTotalPartiel, calucleraxes(prixTotalPartiel, pourcentageTaxesTPS),
						calucleraxes(prixTotalPartiel, pourcentageTaxesTVQ)), 2)
				+ "\n";
		return commandePourUnClient;
	}

	// Cette section est pour calculer les taxes et bieb plus

	private double calucleraxes(double totalPartiel, double pourcentageTax) {
		return totalPartiel * pourcentageTax;
	}

	private double caluclerTotal(double totalPartiel, double tps, double tvq) {
		return totalPartiel + tps + tvq;
	}

	public static double arrondir(double valeurPorArrondir, int chiffresApresVirgule) {
		if (chiffresApresVirgule < 0) {
			throw new IllegalArgumentException();
		}
		long factor = (long) Math.pow(10, chiffresApresVirgule);
		valeurPorArrondir = valeurPorArrondir * factor;
		long tmp = Math.round(valeurPorArrondir);
		return (double) tmp / factor;
	}
}
