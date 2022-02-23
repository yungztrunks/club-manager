package fussball;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MatchMenu {

	final static int SPIELZEIT = 90; // Minuten

	private static ArrayList<Match> spielErgebnisse;

	private Stadion stadion;

	public MatchMenu() {
		this.spielErgebnisse = new ArrayList<Match>();
	}

	public static void starteMatch(Mannschaft heimTeam, Mannschaft auswTeam) {

		Match tempMatch = new Match(heimTeam, auswTeam);

		Stadion feld = new Stadion("Signal Iduna Park"); // BVB Stadion
		Scanner scannerMatch = new Scanner(System.in);

		int anzahlToreM1 = 0;
		int anzahlToreM2 = 0;
		int wartezeit;

		System.out.println(
				"Das Spiel zwischen " + heimTeam.getTeamname() + " und " + auswTeam.getTeamname() + " beginnt bald");
		System.out.println("Gespielt wird im " + feld.getFeldname());
		System.out.println("Es sind " + ((int) (Math.random() * (1000 - 1)) + 1) + " Zuschauer anwesend");
		System.out.println();

		String ynWahl = null; // yes or no Wahl

		System.out.println("Schnelldurchlauf des Spiels? y/n");

		try {
			ynWahl = scannerMatch.nextLine();
			ynWahl.toLowerCase(); // Lowercase, falls caps lock aktiviert ist
		} catch (Exception e) {
			System.out.println("Ein Fehler ist aufgetreten");
		}

		if (ynWahl.equals("y")) {
			System.out.println("Die Spielminuten werden im Schnelldurchlauf ausgegeben");
			wartezeit = 0;
		} else if (ynWahl.equals("n")) {

			System.out.println("Die Spielminuten werden regulaer ausgegeben");
			wartezeit = 6;
		} else {
			System.out.println("Falsche Eingabe! Schnelldurchlauf wird angewendet");
			wartezeit = 0;
		}

		System.out.println(" ");
		System.out.println("Das Spiel beginnt nun!");
		System.out.println("-----------------------------------------------------");

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			System.out.println("Ein unbekannter Fehler ist aufgetreten!");
			e.printStackTrace();
		}

		for (int i = 10; i <= SPIELZEIT; i += 10) {
			/*
			 * Jede 10 Minuten Spielzeit wird die Mannschaft "neu gewuerfelt" Danach wird
			 * ein zuf�lliger Spieler genommen. Mit diesem Spieler werden die Methoden
			 * torSchiessen() und roteKarte() aus der Klasse Spieler durchgefuehrt. Der
			 * zurueckgegebene boolean Wert dient noch weiteren Ablaeufen wie zum Beispiel
			 * angeben dass niemand ein Tor erzielte
			 */
			Spieler tempSpieler;

			if ((tempSpieler = heimTeam.randomSpieler()).torSchiessen()) {
				anzahlToreM1++;
				if (!tempMatch.getSs1().contains(tempSpieler)) {
					tempMatch.getSs1().add(tempSpieler);
				}

			} else if ((tempSpieler = auswTeam.randomSpieler()).torSchiessen()) {
				anzahlToreM2++;
				if (!tempMatch.getSs2().contains(tempSpieler)) {
					tempMatch.getSs2().add(tempSpieler);
				}
			} else {
				System.out.println("Keiner der beiden Mannschaften hat ein Tor erzielt!");
			}

			if (heimTeam.randomSpieler().roteKarte()) {

			}
			if (auswTeam.randomSpieler().roteKarte()) {

			}

			/*
			 * Noch ohne wirkliche Funktion
			 */

			System.out.println("Dies war die " + i + ". Minute des Spiels");
			System.out.println("Aktueller Spielstand: " + heimTeam.getKuerzel() + " " + anzahlToreM1 + "-"
					+ anzahlToreM2 + " " + auswTeam.getKuerzel());
			// Aktueller Spielstand: MUN 3-1 BVB
			System.out.println();
			System.out.println("-----------------------------------------------------");

			try {
				TimeUnit.SECONDS.sleep(wartezeit);
			} catch (InterruptedException e) {
				System.out.println("Ein unbekannter Fehler ist aufgetreten!");
			}

		}

		System.out.println(
				"Das Spiel zwischen " + heimTeam.getTeamname() + " und " + auswTeam.getTeamname() + " ist zu Ende!");
		System.out.println("Das Spiel endete mit " + anzahlToreM1 + " - " + anzahlToreM2);
		System.out.println("Der Gewinner ist somit: ");

		if (anzahlToreM1 > anzahlToreM2) {
			System.out.print(heimTeam.getTeamname());
		} else if (anzahlToreM1 == anzahlToreM2) {
			System.out.println("Niemand! Das Spiel endete als Unentschieden");
		} else {
			System.out.print(auswTeam.getTeamname());
		}

		System.out.println("\n");
		System.out.println("-----------------------------------------------------");

		tempMatch.setHeimTore(anzahlToreM1);
		tempMatch.setAuswTore(anzahlToreM2);

		System.out.println("Soll dieses Match als Datei gespeichert werden? y/n");
		String dateiSpeichern = null;

		try {
			dateiSpeichern = scannerMatch.nextLine().toLowerCase();
		} catch (NoSuchElementException e) {
			System.out.println("Ein Fehler ist aufgetreten! Es wurde nichts eingegeben");
		} catch (Exception e) {
			System.out.println("Ein unbekannter Fehler ist aufgetreten");
		}

		if (dateiSpeichern.equals("y")) {
			saveMatchAlsText(tempMatch);
		} else if (dateiSpeichern.equals("n")) {
			System.out.println("Das Match wurde nicht als Text gespeichert");
		} else {
			System.out.println("Ein Fehler trat auf. Nichts wurde gespeichert");
		}

		for (Spieler s : heimTeam) {
			s.setSpieltore(0);
		}

		for (Spieler s : auswTeam) {
			s.setSpieltore(0);
		}

		spielErgebnisse.add(tempMatch);

	}

	private static void saveMatchAlsText(Match match) {

		try {

			File dateipfad = new File("Match " + match.getHeimTeam().getKuerzel() + "-"
					+ match.getAuswTeam().getKuerzel() + " (" + match.getDatum() + ").txt");
			dateipfad.setWritable(true);
			dateipfad.setReadable(true);

			FileWriter datei = new FileWriter(dateipfad);
			BufferedWriter output = new BufferedWriter(datei);
			output.write("Ergebnis vom Match zwischen " + match.getHeimTeam().getTeamname() + " und "
					+ match.getAuswTeam().getTeamname() + " am " + match.getDatum());
			output.newLine();
			output.write("Das Spiel endete mit " + match.getHeimTore() + " - " + match.getAuswTore());
			output.newLine();
			output.newLine();

			if (match.getSs1().size() != 0) {
				output.write("Die Tore von " + match.getHeimTeam().getTeamname() + " sind von:");
				output.newLine();

				for (Spieler s : match.getSs1()) {
					output.write(s.geschosseneTore());
					output.newLine();
				}
			}

			output.newLine();
			if (match.getSs2().size() != 0) {
				output.write("Die Tore von " + match.getAuswTeam().getTeamname() + " sind von:");
				output.newLine();

				for (Spieler s : match.getSs2()) {
					output.write(s.geschosseneTore());
					output.newLine();
				}
			}

			output.newLine();

			if (match.getHeimTore() > match.getAuswTore()) {
				output.write("Der Sieger ist somit: " + match.getHeimTeam().getTeamname());
			} else {
				output.write("Der Sieger ist somit: " + match.getAuswTeam().getTeamname());
			}
			output.flush();
			datei.close();

			System.out.println("Datei gespeichert!");
			System.out.println("Die Datei befindet sich in: " + dateipfad.getAbsoluteFile());

		} catch (IOException e) {
			System.out.println("Ein Fehler beim Schreiben ist aufgetreten");
		}
	}

	public void matchHistory() {

		if (spielErgebnisse.size() == 0) {
			System.out.println("Es haben noch keine Veranstaltungen stattgefunden");
		}
		for (Match m : spielErgebnisse) {
			System.out.println(m.matchErgebnisse());
		}
	}

}