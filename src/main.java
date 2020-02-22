
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {

		
		// Ceci est le nom par défaut du fichier à prendre
		String fileNameAndExtension = "clients.txt";
		// Ceci est l'extension par défaut du fichier à prendre
		String fileExtension = "txt";

		// Ceci est le scanner qui prends ce que l'utilisateur écrit dans la console
		Scanner userinput = new Scanner(System.in);

		// Ceci demande à l'usager le nom du fichier prendre
		System.out.print("Quelle est le nom de votre fichier (sans l'extension)? : ");
		// Ceci est pour prendre l'entrée de l'utilisateur qui va être retenu dans la
		// variable - String fileName
		String fileName = userinput.next();

		// Ceci demande à l'usager le nom de l'extension du fichier à prendre
		System.out.print("Quel est l'extension de votre fichier? : ");
		// Ceci est pour prendre l'entrée de l'utilisateur qui va être retenu dans la
		// variable - String fileExtension
		fileExtension = userinput.next();

		// ceci crée un nouveau String qui contient le nom, un point et l'extension du
		// fichier
		fileNameAndExtension = fileName + "." + fileExtension;

		new AfficherFacture(fileNameAndExtension);

	}

}