
public class Commande {

	private Plats plats;
	private int nbPlats;

	public Commande(Plats plats, int nbPlats) {
		this.plats = plats;
		this.nbPlats = nbPlats;
	}

	// Section pour avoir et changer le Plats plats - Début
	public void setPlats(Plats plats) {
		this.plats = plats;
	}

	public Plats getPlats() {
		return this.plats;
	}
	// Section pour avoir et changer le Plats plats- Fin

	// Section pour avoir et changer le int nbPlats - Début
	public void setNbPlats(int nbPlats) {
		this.nbPlats = nbPlats;
	}

	public int getNbPlats() {
		return this.nbPlats;
	}
	// Section pour avoir et changer le int nbPlats - Fin

}
