package application;

public class Exercice{

	private String consigne;
	private String nomMateriel;
	private int[] score ;

	public Exercice(String consigne, String nomMateriel) {
		this.consigne = consigne;
		this.nomMateriel = nomMateriel;
	}

	public String getConsigne() {
		return (this.consigne);
	}

	public void setConsigne(String consigne) {
		this.consigne = consigne;

	}

	public String getNomMaterial() {
		return (this.nomMateriel);
	}

	public void setNomMaterial(String nomMateriel) {
		this.nomMateriel = nomMateriel;
	}

	public float calculeMoyenne() {
		float moyenne = 0;
		for (int m : score) {

			moyenne = +m;
		}
		return (moyenne);
	}

	public boolean equals(String cons) {
		if (this.consigne.equals(cons)) {
			return (true);
		} else {
			return (false);
		}
	}

	public String getEnonce() {
		return consigne;
	}

	public void setScore(int i, int score) {
		this.score[i] = score;
	}

}
