
public class Client {

	
	private String nomClient;
	private Commande commande;

	public Client(String nomClient, Commande commande) {
		this.nomClient = nomClient;
		this.commande = commande;
	}

	// Section pour avoir et changer le nomClient - Début
	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}

	public String getNomClient() {
		return this.nomClient;
	}
	// Section pour avoir et changer le nomClient - Fin

	// Section pour avoir et changer la Commande commande - Début
	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public Commande getCommande() {
		return this.commande;
	}
	// Section pour avoir et changer la Commande commande - Fin
}
