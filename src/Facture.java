
public class Facture {

	private Client client;
	private Plats plat;
	private Commande commande;
	private double prixTotal;

	
	public Facture(Client client, Plats plat, Commande commande, double prixTotal) {
		this.client = client;
		this.plat = plat;
		this.commande = commande;
		this.prixTotal = prixTotal;
	}

	// Section pour avoir et changer le client - Début
	public void setClient(Client client) {
		this.client = client;
	}

	public Client getClient() {
		return this.client;
	}
	// Section pour avoir et changer le client - Fin

	// Section pour avoir et changer le Plats plats - Début
	public void setPlats(Plats plats) {
		this.plat = plats;
	}

	public Plats getPlats() {
		return this.plat;
	}
	// Section pour avoir et changer le Plats plats - Fin

	// Section pour avoir et changer la Commande commande - Début
	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public Commande getCommande() {
		return this.commande;
	}
	// Section pour avoir et changer la Commande commande - Fin

	// Section pour avoir et changer la double prixTotal - Début
	public void setPrixTotal(double prixTotal) {
		this.prixTotal = prixTotal;
	}

	public double getPrixTotal() {
		return this.prixTotal;
	}
	// Section pour avoir et changer la double prixTotal - Fin

	public void aficherLaFacture() {

		
		System.out.println(client.getNomClient() + " " + getPrixTotal() + "$");
	}

}

