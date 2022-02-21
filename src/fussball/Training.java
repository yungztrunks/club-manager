package fussball;

public interface Training {

	public void statusTrainingsplatz();
	// gibt aus, ob Trainingsplätze schon belegt sind und wenn ja, von welchem Team

	public void starteTrainingSession(Mannschaft team);
	// versetzt ein Team in den Trainingsmodus

	public void endTrainingSession(Mannschaft team);
	// beendet das Trainingsmodus eines Teams
}
