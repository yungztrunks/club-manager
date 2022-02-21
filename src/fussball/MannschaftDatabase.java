package fussball;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class MannschaftDatabase {
	private ArrayList<Mannschaft> mannschaften;

	public MannschaftDatabase() {
		mannschaften = new ArrayList<Mannschaft>();
	}

	public ArrayList<Mannschaft> getMannschaften() {
		return mannschaften;
	}

	public void setMannschaften(ArrayList<Mannschaft> mannschaften) {
		this.mannschaften = mannschaften;
	}

	// Methode dient, eine Mannschaft hinzuzufügen
	public void addMannschaft(Mannschaft mannschaft) {
		mannschaften.add(mannschaft);
	}

	// Methode dient, eine Mannschaft zu entfernen
	public void removeMannschaft(Mannschaft mannschaft) {
		mannschaften.remove(mannschaft);
	}

	// Methode dient, nach einer Mannschaft zu suchen
	// return eine Mannschaft
	public Mannschaft searchMannschaft(String name) {
		for (Mannschaft i : mannschaften) {
			if (Pattern.compile(Pattern.quote(name), Pattern.CASE_INSENSITIVE).matcher(i.getTeamname()).find()
					|| Pattern.compile(Pattern.quote(name), Pattern.CASE_INSENSITIVE).matcher(i.getKuerzel()).find()) {
				return i;
			}
		}
		return null;
	}

	public void getMannschaftListe() {
		for (Mannschaft i : mannschaften) {
			System.out.println(i.getTeamname() + " (" + i.getKuerzel() + ") ");
		}
	}

}
