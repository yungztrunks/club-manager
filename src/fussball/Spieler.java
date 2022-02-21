package fussball;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Spieler extends Mitglieder {

	private char position; // T fuer Torwart, V fuer Verteidiger, M fuer Mittelfeld, S fuer Stï¿½Stuermer
	private int spieltore;
	private int rating;
	private int saisontore;
	private int rotekarten;

	public Spieler(String name, int alter, char position, int rating) {
		super(name, alter);
		this.position = position;
		this.rotekarten = 0;
		this.saisontore = 0;
		this.spieltore = 0;
		this.rating = rating;
	}

	public char getPosition() {
		return position;
	}

	public String spieltAufPosition() {
		switch (position) {
		case 'T':
			return "Torwart";
		case 'V':
			return "Verteidiger";
		case 'M':
			return "Mittelfeldspieler";
		case 'S':
			return "Stuermer";
		default:
			return "ist noch keiner Position zugeteilt";
		}
	}

	public void setPosition(char position) {
		this.position = position;
	}

	/*
	 * public String geschosseneToreString() { return "Der Spieler " + getName() +
	 * " hat " + getSaisontore() + " Tore geschossen"; }
	 */

	public int getRotekarten() {
		return rotekarten;
	}

	public void setRotekarten(int rotekarten) {
		this.rotekarten = rotekarten;
	}

	public String erhalteneKarten() {
		return "Der Spieler " + getName() + " hat " + getRotekarten() + " in dieser Saison erhalten";
	}

	public boolean torSchiessen() {

		double torWSLK = 0; // WSLK steht für Wahrscheinlichkeit
		boolean torErzielt = true;

		switch (this.position) {
		case 'T':
			torWSLK = (Math.random() * 2) * 26; // Sehr selten
			break;

		case 'V':
			torWSLK = (Math.random() * 2) * 32;
			break;

		case 'M':
			torWSLK = (Math.random() * 2) * 40;
			break;

		case 'S':
			torWSLK = (Math.random() * 2) * 50;
			break;
		}

		if (torWSLK > 50) {
			this.setSpieltore(this.getSpieltore() + 1); // Tore pro Match (zum Rechnen), werden nach Match wieder auf 0
														// gesetzt
			this.setSaisontore(this.getSaisontore() + 1); // Tore pro gesamt Saison
			System.out.println(kommentar(this));
			torErzielt = true;
		} else {
			torErzielt = false;
		}

		return torErzielt;
	}

	public String kommentar(Spieler spieler) {
		ArrayList<String> saetze = new ArrayList<String>();
		saetze.add("Der " + spieler.spieltAufPosition() + " " + spieler.getName() + " hat ein Tor geschossen?!");
		saetze.add("Was fuer ein Tor von " + spieler.getName() + "!");
		saetze.add("Ein heftiges Tor von " + spieler.getName());
		saetze.add(spieler.getName() + " trifft!");

		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(saetze.size());

		return saetze.get(index);
		// Für etwas Variation bei der Konsolenausgabe
	}

	public boolean roteKarte() {
		boolean karteErhalten = false;
		double i = 0;
		switch (position) {
		case 'T':
			i = Math.random() * 100;
			if (i < 0.09) { // Wahrscheinlichkeit von 0,09%;
				karteErhalten = true;
				this.setRotekarten(this.getRotekarten() + 1);
				System.out.println(
						"Der Spieler " + this.getName() + " erhielt eine Rote Karte und wurde vom Platz verwiesen");
			}
		default:
			i = Math.random() * 100;
			if (i < 1.5) { // Wahrscheinlichkeit von 1,5%
				karteErhalten = true;
				this.setRotekarten(this.getRotekarten() + 1);
				System.out.println(
						"Der Spieler " + this.getName() + " erhielt eine Rote Karte und wurde vom Platz verwiesen");
			}
			/*
			 * Ein Torwart erhaelt sehr viel seltener eine Rote Karte als z.B ein Stuermer
			 * oder Verteidiger. Bei diesen 2 sind die Wahrscheinlichkeiten
			 * vernachlaessigbar aehnlich
			 */
		}
		return karteErhalten;
	}

	public int getSaisontore() {
		return saisontore;
	}

	public void setSaisontore(int saisontore) {
		this.saisontore = saisontore;
	}

	public int getSpieltore() {
		return spieltore;
	}

	public void setSpieltore(int spieltore) {
		this.spieltore = spieltore;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String geschosseneTore() {
		return getName() + ", schoss " + getSpieltore() + " Tore";
	}

	public String getInfoSpieler() {
		return String.format("%-20s %7d %10s %12d %9d", getName(), getAlter(), position, rating, saisontore);
	}
	// Cristiano Ronaldo 36 Stuermer 15

	// enum
	static public Comparator<Spieler> compareByPosition() {
		return new Comparator<Spieler>() {
			@Override
			public int compare(Spieler s1, Spieler s2) {
				return s1.getPosition() - s2.getPosition();
			}
		};
	}

	static public Comparator<Spieler> compareByName() {
		return new Comparator<Spieler>() {
			@Override
			public int compare(Spieler s1, Spieler s2) {
				return s1.getName().compareTo(s2.getName());
			}
		};
	}

	static public Comparator<Spieler> compareByAlter() {
		return new Comparator<Spieler>() {
			@Override
			public int compare(Spieler s1, Spieler s2) {
				return s2.getAlter() - s1.getAlter();
			}
		};
	}

	static public Comparator<Spieler> compareByRating() {
		return new Comparator<Spieler>() {
			@Override
			public int compare(Spieler s1, Spieler s2) {
				return s2.getRating() - s1.getRating();
			}
		};
	}

	static public Comparator<Spieler> compareByTore() {
		return new Comparator<Spieler>() {
			@Override
			public int compare(Spieler s1, Spieler s2) {
				return s1.getSaisontore() - s2.getSaisontore();
			}
		};
	}

}
