
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AfficherFacture {

	/**
	 * affiche la facture de chacune des clients avec les erreurs 
	 * @param fileNameAndExtension
	 * 
	 */

	
	public AfficherFacture(String fileNameAndExtension) {

		try {

			// Ceci crée un nouveau fichier avec le nom complet du fichier
			File file = new File(fileNameAndExtension);
			/*
			 * Ces lignes lisent chaque ligne et les mettent dans un arrClients, arrPlats et
			 * arrCommandes - Fin
			 */

			Scanner inputStream = new Scanner(file);

			int typeData = 0;
			String listeClients = "";
			String listePlats = "";
			String listeCommandes = "";

			int nbLigneCourante = 0;
			while (inputStream.hasNext()) {
				String lineRead = inputStream.nextLine();
				lineRead = lineRead.trim().replaceAll("\\s+", " "); // enleve les spaces de trop dans la ligne
				nbLigneCourante += 1;

				if (lineRead.contains("Clients :")) {
					typeData += 1;
				} else if (lineRead.contains("Plats :")) {
					typeData += 1;
				} else if (lineRead.contains("Commandes :")) {
					typeData += 1;
				} else if (typeData == 1 && !(lineRead.contains("Clients :"))) { 
					if (verifierNomClient(lineRead)) {
						listeClients += lineRead + ";";
					} else {
						System.out.println("**********************************************************");
						System.out.println("Le nom du client est incorrect dans la ligne " + nbLigneCourante
								+ " pour le client : " + lineRead + ", car le nom contient des chiffres");
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

			
			int nbClients = 0;

			String[] arrClients = listeClients.split(";");
			String[] arrPlats = listePlats.split(";");
			String[] arrCommandes = listeCommandes.split(";");

			nbClients = arrClients.length;

			System.out.println("");
			Facture[] factureChqClient = creerFactureChqClient(arrClients, arrPlats, arrCommandes, nbClients);

			System.out.println("Bienvenue chez Barette!\r\n" + "Factures:");
			for (int i = 0; i < factureChqClient.length; i++) {
				factureChqClient[i].aficherLaFacture();
			}

		} catch (FileNotFoundException e) {

			System.out.println("Le Fichier " + fileNameAndExtension + " n'existe pas ");

		}
	}

	
	/**
	 * Vérifie si le client continent des chiffres
	 * @param lineRead
	 * @return vrai ou faux
	 */
	private boolean verifierNomClient(String lineRead) {
		boolean verifierNomClient = true;
		if (lineRead.matches(".*\\d.*")) {
			verifierNomClient = false;
		}
		return verifierNomClient;
	}

	
	/**
	 *  Vérifie si la commande et les plats ont des données illégaux
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
	 * @param arrClients
	 * @param arrPlats
	 * @param arrCommandes
	 * @param nbClients
	 * @return Facture[] arrayFacture
	 * 
	 * 
	 */

	private Facture[] creerFactureChqClient(String[] arrClients, String[] arrPlats, String[] arrCommandes,
			int nbClients) {

		Facture[] arrayFacture = new Facture[nbClients];
		String nomPlat = null;
		double prixPlat = 0;
		int nbPlats = 0;
		for (int i = 0; i < arrClients.length; i++) {

			String nomClient = arrClients[i];
			double prixTotal = calculerCoutTotal(arrCommandes, arrPlats, nomClient);

			for (int j = 0; j < arrPlats.length; j++) {
				String arrPlatCurrent = arrPlats[j];
				String[] arrPlatSplited = arrPlatCurrent.split(" ");

				nomPlat = arrPlatSplited[0];
				prixPlat = Double.parseDouble(arrPlatSplited[1]);
			}

			for (int j = 0; j < arrCommandes.length; j++) {
				String arrCommandeCurrent = arrCommandes[j];
				String[] arrCommandeSplited = arrCommandeCurrent.split(" ");

				nbPlats = Integer.parseInt(arrCommandeSplited[2]);
			}

			Plats plats = new Plats(nomPlat, prixPlat);
			Commande commande = new Commande(plats, nbPlats);
			Client nouveauClient = new Client(nomClient, commande);
			Facture nouvelleFacture = new Facture(nouveauClient, plats, commande, prixTotal);
			arrayFacture[i] = nouvelleFacture;

		}
		return arrayFacture;
	}

	
	/**
	 *  Calcule le prix totale
	 * @param arrCommandes
	 * @param arrPlats
	 * @param nomClient
	 * @return prixTotal
	 */
	private double calculerCoutTotal(String[] arrCommandes, String[] arrPlats, String nomClient) {

		double prixTotal = 0;

		for (int i = 0; i < arrCommandes.length; i++) {
			String arrCommandeCurrent = arrCommandes[i];
			String[] arrCommandeSplited = arrCommandeCurrent.split(" ");

			if (nomClient.contains(arrCommandeSplited[0])) {
				// This line doesn't work

				for (int j = 0; j < arrPlats.length; j++) {
					String arrPlatsCurrent = arrPlats[j];

					String[] arrPlatSplited = arrPlatsCurrent.split(" ");

					if (arrCommandeSplited[1].contains(arrPlatSplited[0])) {
						prixTotal += Double.parseDouble(arrPlatSplited[1]) * Integer.parseInt(arrCommandeSplited[2]);
					}
				}
			}
		}
		
		return prixTotal;
	}

}
