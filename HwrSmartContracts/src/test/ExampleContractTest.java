package test;

import java.io.File;
import java.util.Scanner;

public class ExampleContractTest {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("willkommen zu dem Spiel: 'La Roulette russe'");
		System.out.println("Bitte folgen sie die Folgende Anweidungen um zu spielen");

		System.out.println("Bitte geben sie den Pfad zu ihrem waves Node (.json) ");
		File f=new File(sc.nextLine());

		System.out.println("Wollen sie eine Neu Währung für den Auftrag erstellen? (antworten sie bitte mit true oder false");
		boolean createasset=sc.nextBoolean();
		

		System.out.println("Bitte die waves-Adresse des gegners angeben");
		String partner=sc.next();
		System.out.println("Bitte geben sie den zu wettende Betrag");
		double preis=sc.nextDouble();
		
		ExampleContract contract=new ExampleContract(f,partner,createasset,preis);
		
		contract.wetten();
		contract.bewerteErgebnisse();
		
		
	}
}
