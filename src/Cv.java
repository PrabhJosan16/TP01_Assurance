
public class Cv {

	public static void main(String[] args) {

		String[] competence = { "Gentil", "Has friends", "Better than Daniel" };

		Cv cvPRABH = new Cv("Josan", "Prabhjot Singh", "Informatique", 0, competence, "Bien coder et apprendre GitHub");

		cvPRABH.affiche();
	}

	String nom;
	String prenom;
	String formation;
	int nbAnneesExp;
	String[] competence = new String[0];
	String attantes;

	public Cv(String nom, String prenom, String formation, int nbAnneesExp, String[] competence, String attantes) {
		this.nom = nom;
		this.prenom = prenom;
		this.formation = formation;
		this.nbAnneesExp = nbAnneesExp;
		this.competence = competence;
		this.attantes = attantes;

	}

	public void affiche() {

		System.out.println("*************************");

		System.out.println("Nom : " + nom);

		System.out.println("Pr�nom : " + prenom);

		System.out.println("Formation : " + formation);

		System.out.println("nombre d'ann�es d'exp�riences : " + nbAnneesExp);

		System.out.println("Competence : ");

		for (int i = 0; i < competence.length; i++) {

			System.out.println("- " + competence[i]);

		}

		System.out.println("Attantes vis � vis le cours 4B4: " + attantes);
	}

}
