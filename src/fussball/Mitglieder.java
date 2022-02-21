package fussball;

public abstract class Mitglieder {

	private String name;
	private int alter;

	Mitglieder(String name, int alter) {
		this.name = name;
		this.alter = alter;
	}

	public int getAlter() {
		return alter;
	}

	public void setAlter(int alter) {
		this.alter = alter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
