package fussball;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class Mannschaft extends ArrayList<Spieler> {

	private String teamname;
	private String kuerzel; // AbkÃ¼rzung des Namen
	private ArrayList<Spieler> spieler; // Liste der Spieler
	private boolean trainingStatus;
	private boolean vorgegeben;
	private Random randomGenerator;

	Mannschaft(String teamname, String kuerzel) {
		this.teamname = teamname;
		this.kuerzel = kuerzel;
		this.trainingStatus = false;
		this.vorgegeben = false;

		this.spieler = new ArrayList<Spieler>();
		addSpieler(kuerzel + ".txt");
	}

	Mannschaft(String teamname, String kuerzel, boolean vorgegeben) {
		this.teamname = teamname;
		this.kuerzel = kuerzel;
		this.trainingStatus = false;
		this.vorgegeben = true;

		this.spieler = new ArrayList<Spieler>();
		File spielerliste = new File(kuerzel + ".txt");
		try {
			spielerliste.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		addSpieler(kuerzel + ".txt");
	}

	public boolean isTrainingStatus() {
		return trainingStatus;
	}

	public void setTrainingsstatus(boolean trainingStatus) {
		this.trainingStatus = trainingStatus;
	}

	// Diese Methode liest die Liste der Spieler aus der Textdatei ein
	// und ist nicht dazu da um Spieler einer Mannschaft hinzuzufügen
	public void addSpieler(String fileName) {
		File file = new File(fileName);
		Scanner scanner = null;

		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Datei ist nicht gefunden.");
		}

		ArrayList<Spieler> spielerList = new ArrayList<Spieler>();

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();

			String[] items = line.split("\\|");

			String name = items[0];
			int alter = Integer.parseInt(items[1]);
			char position = items[2].charAt(0);
			int rating = Integer.parseInt(items[3]);

			this.spieler.add(new Spieler(name, alter, position, rating));
		}
	}

	public Spieler randomSpieler() {
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(spieler.size());
		return spieler.get(index);
	}

	public void addSpieler() {
		Scanner scannerAP = new Scanner(System.in);

		System.out.print("Name vom Spieler: ");
		String name = scannerAP.nextLine();

		System.out.print("Alter: ");
		int alter = scannerAP.nextInt();

		System.out.println("Position");
		System.out.print("(T fuer Torwart, V fuer Verteildiger, M fuer Mittelfeld, S fuer Stuermer): ");
		char position = scannerAP.next().toUpperCase().charAt(0);

		System.out.println("Rating");
		System.out.println("(zwischen 1 und 100): ");
		int rating = scannerAP.nextInt();

		this.spieler.add(new Spieler(name, alter, position, rating));

		try (BufferedWriter dateiSchreiber = new BufferedWriter(new FileWriter(new File(kuerzel + ".txt"), true))) {
			dateiSchreiber.newLine();
			dateiSchreiber.write(name + "|" + alter + "|" + position + "|" + rating);
			dateiSchreiber.close();
		} catch (IOException ex) {
		}

		System.out.println(name + " ist jetzt Spieler von " + this.teamname);
	}

	public ArrayList<Spieler> getSpieler() {
		return spieler;
	}

	public void setSpieler(ArrayList<Spieler> spieler) {
		this.spieler = spieler;
	}

	public String getTeamname() {
		return teamname;
	}

	public String getKuerzel() {
		return kuerzel;
	}

	public void setKuerzel(String kuerzel) {
		this.kuerzel = kuerzel;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

//	return String.format("%-20s %5d %7s %d", getName(), getAlter(), position, saisontore);

	public void spielerOutputConsole(String kriterium) {
		System.out.println("Spieler von " + teamname + " (" + kuerzel + ") (nach " + kriterium + " sortiert)");
		System.out.println("Name                     Alter     Position     Rating     Tore");
		System.out.println(" ");

		for (Spieler s : this.spieler) {
			System.out.println(s.getInfoSpieler());
		}
	}

	public static String sortPlayers(int option, Mannschaft team) {
		Comparator<Spieler> comparator = null;
		Scanner inputYN = new Scanner(System.in);
		String kriterium = null;
		String ynWahl = null;

		switch (option) {

		case 1:
			comparator = Spieler.compareByRating();
			kriterium = "Rating";
			break;
		case 2:
			comparator = Spieler.compareByPosition();
			kriterium = "Position";
			break;
		case 3:
			comparator = Spieler.compareByTore();
			kriterium = "Tore";
			break;
		case 4:
			comparator = Spieler.compareByAlter();
			kriterium = "Alter";
			break;
		case 5:
			comparator = Spieler.compareByName();
			kriterium = "Name";
			break;
		}

		Collections.sort(team.spieler, comparator);

		try (BufferedWriter dateiSchreiber = new BufferedWriter(
				new FileWriter(new File(team.kuerzel + "_Spieler.txt"), false))) {
			dateiSchreiber.write(
					"Spieler von " + team.teamname + " (" + team.kuerzel + ") (nach " + kriterium + " sortiert)");
			dateiSchreiber.newLine();
			dateiSchreiber.write("Name                    Alter       Position      Rating");
			for (Spieler s : team.spieler) {
				dateiSchreiber.write(s.getInfoSpieler());
				dateiSchreiber.newLine();
			}
			dateiSchreiber.close();
		} catch (IOException ex) {

		}
		return kriterium;
	}
}