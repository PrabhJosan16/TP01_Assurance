import static org.junit.Assert.*;

import org.junit.Test;

public class AfficherFactureTest {

	@Test
	public void test() {
		
		System.out.println("******************************************");
		
		System.out.println("Fichier avec mauvaise extention\n");
		
		AfficherFacture factureMauvaiseExtention = new AfficherFacture("clients.xml");
		
		
		System.out.println("******************************************");
		
		System.out.println("Fichier sans extention\n");
	
		AfficherFacture factureSansExtention = new AfficherFacture("clients");
		
		
		System.out.println("******************************************");
		
		System.out.println("Fichier mauvaise nom\n");

		AfficherFacture facturemauvaiseNom = new AfficherFacture("Cli");
		
		
		System.out.println("******************************************");
		
		System.out.println("Fichier avec extention");
		
		AfficherFacture factureAvecExtention = new AfficherFacture("clients.txt");
		
		System.out.println("******************************************");
		
		
	}

}
