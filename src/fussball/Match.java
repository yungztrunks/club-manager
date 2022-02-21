package fussball;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Match {

	private String datum;
	private Mannschaft heimTeam;
	private Mannschaft auswTeam;
	private int heimTore;
	private int auswTore;
	private ArrayList<Spieler> ss1; // Spieler vom Heimteam, die Tore getroffen haben.
	private ArrayList<Spieler> ss2; // Spieler vom Auswteam, die Tore getroffen haben.
	private int anzahlRoteKarte;

	public Match(Mannschaft heimTeam, Mannschaft auswTeam) {
		this.heimTeam = heimTeam;
		this.auswTeam = auswTeam;
		heimTore = 0;
		auswTore = 0;
		ss1 = new ArrayList<Spieler>();
		ss2 = new ArrayList<Spieler>();
		anzahlRoteKarte = 0;

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH.mm dd.MM.yyyy");
		LocalDateTime now = LocalDateTime.now();
		datum = dtf.format(now);
		/*
		 * Aktuelle Uhrzeit ermitteln, damit beim Speichern von Matches, ein vorheriges
		 * Match nicht unbedingt überschriebenw wird
		 */
	}

	public ArrayList<Spieler> getSs1() {
		return ss1;
	}

	public ArrayList<Spieler> getSs2() {
		return ss2;
	}

	public String getDatum() {
		return datum;
	}

	public Mannschaft getHeimTeam() {
		return heimTeam;
	}

	public void setHeimTeam(Mannschaft heimTeam) {
		this.heimTeam = heimTeam;
	}

	public Mannschaft getAuswTeam() {
		return auswTeam;
	}

	public void setAuswTeam(Mannschaft auswTeam) {
		this.auswTeam = auswTeam;
	}

	public int getHeimTore() {
		return heimTore;
	}

	public void setHeimTore(int heimTore) {
		this.heimTore = heimTore;
	}

	public int getAuswTore() {
		return auswTore;
	}

	public void setAuswTore(int auswTore) {
		this.auswTore = auswTore;
	}

	// Manchester United 0 - 3 Borussia Dortmund | 21.31 13.01.2022
	public String matchErgebnisse() {
		return this.getHeimTeam().getTeamname() + " " + this.getHeimTore() + " - " + this.getAuswTore() + " "
				+ this.getAuswTeam().getTeamname() + " | " + this.getDatum();
	}
}
