package application;

public abstract class Test {

	private String nom;
	private int capacite;
	protected float scoreTotal = 0;
	private String conclusion;

	public Test(String nom, int capacite) {
		this.nom = nom;
		this.capacite = capacite;
	}
	
	
	// Méthode d'affichage d'un test
	public  void afficherTest() {};
		
	
	public String getNom() {
		return (this.nom);

	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getCapacite() {
		return (this.capacite);
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}

	public String getConclusion() {
		return (this.conclusion);
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public float getScoreTotal() {
		return (this.scoreTotal);
	}

	public void setScoreTotal(float scoreTotal) {
		this.scoreTotal = scoreTotal;
	}

	public abstract float calculeScoreTotal();
}
