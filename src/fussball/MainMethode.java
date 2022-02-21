package fussball;

/**
 * 
 * @author *ZTrunks
 * @author my homie
 *
 */

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

import javax.sound.midi.Soundbank;

public class MainMethode {
	static TrainingMenu trainingMenu = new TrainingMenu();
	static MatchMenu matchMenu = new MatchMenu();
	static MannschaftDatabase mannschaftDatabase = new MannschaftDatabase();

	public static void main(String[] args) {
		mannschaftDatabase.addMannschaft(new Mannschaft("Borussia Dortmund", "BVB", true));
		mannschaftDatabase.addMannschaft(new Mannschaft("Manchester United", "MUN", true));
		mainMenu();
	}

	public static void mainMenu() {
		int option = 0;
		Scanner scannerMainMenu = new Scanner(System.in);

		System.out.println("----------------------------------------------------------------");
		System.out.println("***Hauptmenue***");
		System.out.println("1. Teamverwaltung");
		System.out.println("2. Friendly-Spiel");
		System.out.println("3. Training");
		System.out.println("0. Exit");

		do {
			System.out.print(">> ");
			option = getInteger();
			if (option < 0 || option > 3) {
				System.out.println("Ungueltige Auswahl! Bitte zwischen 0,1,2 oder 3 waehlen");
			}
		} while (option < 0 || option > 3);

		mainMenuOption(option);
	}

	public static void mainMenuOption(int option) {
		switch (option) {

		case 1:
			teamManagementMenu();
			break;

		case 2:
			spielMenu();
			break;

		case 3:
			trainingMenu();
			break;

		case 0:
			System.out.println("Tschuess!");
			System.exit(0);
		}
	}

//Team Management...

	public static void teamManagementMenu() {
		int option = 0;

		System.out.println("----------------------------------------------------------------");
		System.out.println("***Teamverwaltung***");
		System.out.println("1. Neue Mannschaft hinzufuegen");
		System.out.println("2. Overview von Mannschaften");
		System.out.println("0. Zurueck zum Hauptmenue");

		do {
			System.out.print(">> ");
			option = getInteger();
			if (option < 0 || option > 2) {
				System.out.println("Ung�tlige Auswahl! Bitte zwischen 0,1 und 2 waehlen");
			}
		} while (option < 0 || option > 2);

		teamManagementOption(option);
	}

	public static void teamManagementOption(int option) {
		switch (option) {

		case 1:
			addMannschaft();
			pressEnter();
			teamManagementMenu();
			break;

		case 2:
			teamOverviewMenu();
			pressEnter();
			break;

		case 0:
			mainMenu();
			break;
		}
	}

	public static void teamOverviewMenu() {
		int option = 0;
		String teamName = null;
		Mannschaft team = null;

		Scanner scannerTOM = new Scanner(System.in);

		System.out.println("----------------------------------------------------------------");
		System.out.println("***Team Overview***");
		mannschaftDatabase.getMannschaftListe();
		System.out.println();

		if (mannschaftDatabase.getMannschaften().size() == 0) {
			System.out.println("Keine Teams verfuegbar...");
			teamManagementMenu();
		}

		System.out.println("Welches Team?");
		try {
			teamName = scannerTOM.nextLine();
		} catch (NullPointerException e) {
			System.out.println("Es ist ein Fehler aufgetreten. Nichts wurde eingegeben. Zurueck zum Hauptmenue");
			pressEnter();
			mainMenu();
		} catch (IllegalStateException e) {
			System.out.println("Es ist ein Fehler aufgetreten. Scanner wurde geschlossen. Zurueck zum Hauptmenue");
		}

		team = mannschaftDatabase.searchMannschaft(teamName);
		if (team == null) {
			System.out.println("Es gab kein Team mit ihrem Suchwort. Zurueck zum Hauptmenue");
			pressEnter();
			mainMenu();
		}

		System.out.println("***Willkommen in " + team.getTeamname() + "***");
		System.out.println("1. Spielerliste");
		System.out.println("2. Neuen Spieler hinzufuegen");
		System.out.println("3. Team entfernen");
		System.out.println("0. Zurueck zum Hauptmenue");

		do {
			System.out.print(">> ");
			option = getInteger();
			if (option < 0 || option > 3) {
				System.out.println("Unguetlige Auswahl! Bitte zwischen 0,1,2 und 3 w�hlen");
			}
		} while (option < 0 || option > 3);

		teamOverviewMenuOption(option, team);
	}

	public static void teamOverviewMenuOption(int option, Mannschaft team) {
		switch (option) {

		case 1:
			Scanner scannerTOMO = new Scanner(System.in);
			int optionCriterion = 0;

			System.out.println("Spielerliste wird bald erstellt...");
			System.out.println("Nach welchem Kriterium?");
			System.out.println("1. Rating");
			System.out.println("2. Position");
			System.out.println("3. Saisontore");
			System.out.println("4. Alter");
			System.out.println("5. Name");

			do {
				System.out.print(">> ");
				optionCriterion = getInteger();
				if (optionCriterion < 1 || optionCriterion > 5) {
					System.out.println("Unguetlige Auswahl! Bitte zwischen 1,2,3,4 und 5 w�hlen");
				}
			} while (optionCriterion < 1 || optionCriterion > 5);

			playersOutputOption(optionCriterion, team);

			pressEnter();
			teamOverviewMenu();
			break;

		case 2:
			team.addSpieler();
			pressEnter();
			teamOverviewMenu();
			break;

		case 3:
			mannschaftDatabase.removeMannschaft(team);
			System.out.println("Das Team wird geloescht...");
			pressEnter();
			teamOverviewMenu();
			break;

		case 0:
			mainMenu();
			break;
		}
	}

	public static void addMannschaft() {
		Scanner scannerAM = new Scanner(System.in);

		System.out.print("Name der Mannschaft: ");
		String name = scannerAM.nextLine();
		System.out.print("Abkürzung: ");
		String kuerzel = scannerAM.nextLine();

		createFileOutput(kuerzel);
		System.out.println(name + " (" + kuerzel + ") wird erfolgreich erstellt !");
		System.out.println("Du kannst jetzt deine Spieler auf dem Textdatei " + kuerzel + ".txt hinzufügen");
		mannschaftDatabase.addMannschaft(new Mannschaft(name, kuerzel));
	}

	public static void createFileOutput(String kuerzel) {
		File textfile = new File(kuerzel + ".txt");
		try {
			if (!textfile.createNewFile()) {
				System.out.println("Abkuerzung existiert bereits...");
			}
		} catch (IOException e) {
		}
	}

	public static void playersOutputOption(int option, Mannschaft team) {
		Scanner scannerPOO = new Scanner(System.in);
		String ynChoice = null;

		String kriterium = team.sortPlayers(option, team);

		System.out.println(
				"Die Spielerliste wurde erfolgreich als Textdatei " + team.getKuerzel() + "_Spieler.txt ausgegeben");
		System.out.println("Wollen Sie die Spielerliste auch auf der Konsole ausgeben? (y/n)");

		try {
			ynChoice = scannerPOO.nextLine().toLowerCase();
		} catch (NoSuchElementException e) {
			System.out.println("Ein Fehler ist aufgetreten! Es wurde nichts eingegeben");
		} catch (Exception e) {
			System.out.println("Ein unbekannter Fehler ist aufgetreten");
		}

		if (ynChoice.equals("y")) {
			team.spielerOutputConsole(kriterium);
		} else if (ynChoice.equals("n")) {
			System.out.println("Die Spieler werden nicht auf der Konsole angezeigt");
		} else {
			System.out.println("Ein Fehler trat auf.");
		}
	}

//Friendly-Spiel...

	public static void spielMenu() {
		int option = 0;

		System.out.println("----------------------------------------------------------------");
		System.out.println("***Friendly-Spiel***");
		System.out.println("1. Friendly-Spiel starten");
		System.out.println("2. Spielverlauf");
		System.out.println("0. Zurück zum Hauptmenü");

		do {
			System.out.print(">> ");
			option = getInteger();
			if (option < 0 || option > 2) {
				System.out.println("Gebe die Auswahl nochmals (1,2,0)");
			}
		} while (option < 0 || option > 2);

		spielMenuOption(option);
	}

	public static void spielMenuOption(int option) {
		switch (option) {

		case 1:
			Scanner scannerMO = new Scanner(System.in);
			String team1, team2;

			System.out.println("Waehle zwei Teams aus den folgenden Teams aus!");
			System.out.println("Gib dafuer die ersten Buchstaben des Teamnamen ein");
			for (Mannschaft i : mannschaftDatabase.getMannschaften()) {
				if (!i.isTrainingStatus()) {
					System.out.println(i.getTeamname());
				}
			}
			System.out.println();

			System.out.print("Erstes Team: ");
			team1 = scannerMO.nextLine();

			System.out.print("Zweites Team: ");
			team2 = scannerMO.nextLine();
			System.out.println();

			for (Mannschaft i : mannschaftDatabase.getMannschaften()) {
				if (Pattern.compile(Pattern.quote(team1), Pattern.CASE_INSENSITIVE).matcher(i.getTeamname()).find()) {
					for (Mannschaft j : mannschaftDatabase.getMannschaften()) {
						if (Pattern.compile(Pattern.quote(team2), Pattern.CASE_INSENSITIVE).matcher(j.getTeamname())
								.find()) {
							MatchMenu.starteMatch(i, j);
						}
//						else {
//							System.out.println("Ung�ltiger Suchbegriff! Zur�ck zum Men�");
//							break;
//						}
					}
				}
			}

			pressEnter();
			spielMenu();
			break;

		case 2:
			matchMenu.matchHistory();
			pressEnter();
			spielMenu();
			break;

		case 0:
			mainMenu();
			break;
		}
	}

//Training...

	public static void trainingMenu() {
		int option = 0;
		Scanner scannerTM = new Scanner(System.in);

		System.out.println("----------------------------------------------------------------");
		System.out.println("***Training***");
		System.out.println("1. Zustaende der Trainingsplaetze");
		System.out.println("2. Training starten");
		System.out.println("3. Training enden");
		System.out.println("0. Zurueck zum Hauptmenue");

		do {
			System.out.print(">> ");
			option = getInteger();
			if (option < 0 || option > 3) {
				System.out.println("Ung�ltige Auswahl! Bitte zwischen 0,1,2 und 3 w�hlen");
			}
		} while (option < 0 || option > 3);

		trainingMenuOption(option);
	}

	public static void trainingMenuOption(int option) {
		switch (option) {
		case 1:
			trainingMenu.statusTrainingsplatz();
			pressEnter();
			trainingMenu();
			break;

		case 2:
			String teamname = null;
			int count = 0;

			for (Mannschaft i : mannschaftDatabase.getMannschaften()) {
				if (!i.isTrainingStatus()) {
					count++;
				}
			}

			if (count == 0) {
				System.out.println("Alle Teams sind beschaeftigt...");
				pressEnter();
				trainingMenu();
			}

			System.out.println("Welches Team willst du trainieren lassen?");
			for (Mannschaft i : mannschaftDatabase.getMannschaften()) {
				if (!i.isTrainingStatus()) {
					System.out.println(i.getTeamname());
				}
			}

			Scanner scannerTOM1 = new Scanner(System.in);
			teamname = scannerTOM1.nextLine();

			for (Mannschaft i : mannschaftDatabase.getMannschaften()) {
				if (Pattern.compile(Pattern.quote(teamname), Pattern.CASE_INSENSITIVE).matcher(i.getTeamname())
						.find()) {
					trainingMenu.starteTrainingSession(i);
				}
			}
			pressEnter();
			trainingMenu();
			break;

		case 3:
			teamname = null;
			count = 0;

			for (Mannschaft i : mannschaftDatabase.getMannschaften()) {
				if (i.isTrainingStatus()) {
					count++;
				}
			}

			if (count == 0) {
				System.out.println("Kein Team trainiert gerade...");
				pressEnter();
				trainingMenu();
			}

			System.out.println("Welches Team soll aufh�ren zu trainiern?");

			for (Mannschaft i : mannschaftDatabase.getMannschaften()) {
				if (i.isTrainingStatus()) {
					System.out.println(i.getTeamname());
				}
			}

			Scanner scannerTOM2 = new Scanner(System.in);
			teamname = scannerTOM2.nextLine();

			for (Mannschaft i : mannschaftDatabase.getMannschaften()) {
				if (Pattern.compile(Pattern.quote(teamname), Pattern.CASE_INSENSITIVE).matcher(i.getTeamname())
						.find()) {
					trainingMenu.endTrainingSession(i);
				}
			}
			pressEnter();
			trainingMenu();
			break;

		case 0:
			mainMenu();
			break;
		}
	}

	public static int getInteger() {
		Scanner scannerGetInteger = new Scanner(System.in);
		while (true) {
			try {
				return scannerGetInteger.nextInt();
			} catch (InputMismatchException e) {
				scannerGetInteger.next();
				System.out.println("Ungueltige Eingabe. Eingabe muss eine Zahl sein.");
			} catch (Exception e) {
				System.out.println("Ein Unbekannter Fehler beim Scanner ist aufgetreten.");
			}
		}
	}

	public static void pressEnter() {
		Scanner scannerPE = new Scanner(System.in);
		System.out.println();
		System.out.println("[Enter]");
		scannerPE.nextLine();
	}

}