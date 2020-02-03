
public class Cv {


	
	String nom ; 
	String prenom ;
	String formation;
	int nbAnneesExp;
	String[] competence = new String[0] ;
	String attantes ;
	

	public Cv(String nom, String prenom, String formation, int nbAnneesExp, String[] competence, String attantes) {
		this.nom = nom ; 
		this.prenom = prenom;
		this.formation = formation;
		this.nbAnneesExp = nbAnneesExp;
		this.competence = competence;
		this.attantes = attantes;
		
	}
	
}


