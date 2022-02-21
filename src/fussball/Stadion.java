package fussball;

public class Stadion extends Spielfeld {

	Stadion(String feldname) {
		super(feldname);
	}

	@Override
	public String toString() {
		return getFeldname();
	}

}
