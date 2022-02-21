package fussball;

public class Trainingsplatz extends Spielfeld {
	private Mannschaft mannschaftTraining;
	private static int number = 0;

	public Trainingsplatz() {
		this.number = number + 1;
		this.setFeldname("Trainingsplatz " + number);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setBelegt(boolean belegt) {
		super.setBelegt(belegt);
		if (belegt == false) {
			this.setMannschaftTraining(null);
		}
	}

	public Mannschaft getMannschaftTraining() {
		return mannschaftTraining;
	}

	public void setMannschaftTraining(Mannschaft mannschaftTraining) {
		this.mannschaftTraining = mannschaftTraining;
	}

}
