
public class Plats {

	
	private String nomPlats;
	private double prixPlat;

	public Plats(String nomPlats, double prixPlat) {

		this.nomPlats = nomPlats;
		this.prixPlat = prixPlat;

	}

	// Section pour avoir et changer le String nomPlats - Début
	public void setNomPlats(String nomPlats) {
		this.nomPlats = nomPlats;
	}

	public String getNomPlats() {
		return this.nomPlats;
	}
	// Section pour avoir et changer le String nomPlats - Fin

	// Section pour avoir et changer le double prixPlat - Début
	public void setPrixPlats(double prixPlat) {
		this.prixPlat = prixPlat;
	}

	public double getPrixPlats() {
		return this.prixPlat;
	}
	// Section pour avoir et changer le double prixPlat - Fin

}
