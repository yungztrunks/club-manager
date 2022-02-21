package fussball;

import java.util.ArrayList;

public class Spielfeld {

	private String feldname;
	private boolean belegt;

	Spielfeld(String feldname) {
		this.feldname = feldname;
		this.belegt = false;
	}

	Spielfeld() {
		// Soll ohne Funktion bleiben
	}

	public String getFeldname() {
		return feldname;
	}

	public void setFeldname(String feldname) {
		this.feldname = feldname;
	}

	public boolean isBelegt() {
		return belegt;
	}

	public String isBelegtString() {
		if (belegt == true) {
			return " ist belegt";
		}
		return " ist nicht belegt";
	}

	public void setBelegt(boolean belegt) {
		this.belegt = belegt;
	}

}
