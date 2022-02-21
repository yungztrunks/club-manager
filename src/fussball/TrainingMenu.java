package fussball;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TrainingMenu implements Training {

	private ArrayList<Trainingsplatz> trainingsplatz;
	private int availableGrounds;

	public TrainingMenu() {
		this.trainingsplatz = new ArrayList<Trainingsplatz>();
		trainingsplatz.add(new Trainingsplatz());
		trainingsplatz.add(new Trainingsplatz());
		trainingsplatz.add(new Trainingsplatz());

		availableGrounds = trainingsplatz.size();
	}

	public void statusTrainingsplatz() {

		for (Trainingsplatz platz : trainingsplatz) {
			if (platz.isBelegt()) {
				System.out.println(platz.getFeldname() + " ist gerade von Team"
						+ platz.getMannschaftTraining().getTeamname() + " belegt.");
			} else {
				System.out.println(platz.getFeldname() + platz.isBelegtString());
			}
		}
	}

	public void starteTrainingSession(Mannschaft team) {
		String platzOption = null;

		for (Trainingsplatz platz : trainingsplatz) {
			if (platz.isBelegt()) {
				availableGrounds--;
			}
		}
		if (availableGrounds == 0) {
			System.out.println("Alle Trainingsplätze sind schon besetzt!");
			return;
		}

		System.out.println("In welchem Trainingsplaz soll das Team " + team.getTeamname() + " trainieren?");
		for (Trainingsplatz platz : trainingsplatz) {
			if (!platz.isBelegt()) {
				System.out.println(platz.getFeldname());
			}
		}

		Scanner scannerTrainingMenu = new Scanner(System.in);

		while (true) {
			platzOption = scannerTrainingMenu.nextLine();
			for (Trainingsplatz platz : trainingsplatz) {
				if (Pattern.compile(Pattern.quote(platzOption), Pattern.CASE_INSENSITIVE).matcher(platz.getFeldname())
						.find()) {
					System.out.println(
							"Team " + team.getTeamname() + " startet die Trainingssitzung im " + platz.getFeldname());
					team.setTrainingsstatus(true);
					platz.setBelegt(true);
					platz.setMannschaftTraining(team);
					return;
				}
			}
			System.out.println("Platz existiert nicht oder ist schon belegt.");
			System.out.println("");
		}
	}

	public void endTrainingSession(Mannschaft team) {

		for (Trainingsplatz platz : trainingsplatz) {
			if (platz.getMannschaftTraining() == team) {
				System.out.println(
						"Team " + team.getTeamname() + " endet die Trainingssitzung im " + platz.getFeldname());
				team.setTrainingsstatus(false);
				platz.setBelegt(false);
				return;
			}
		}

	}

}
