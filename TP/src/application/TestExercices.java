package application;

import java.util.ArrayList;


public class TestExercices extends Test {
	ArrayList<Exercice> exercices;


	public TestExercices(String nom, int capacite) {
		super(nom, capacite);
		this.exercices = new ArrayList<Exercice>() ;
	}

	
	@Override
	// Méthode de calcul du score total d'un test (série d'exercices)
	public float calculeScoreTotal() {

		for (Exercice ex : exercices) {
			scoreTotal = +ex.calculeMoyenne();

		}
		return (scoreTotal);
	}

	// Méthode d'ajout d'un exercice à la liste des exercices
	public void ajouterExercice(Exercice exercice) {
		exercices.add(exercice);
	}

	// Méthode de recherche d'un exercice (retourne la position de l'exercice dans la liste)
	public int rechercherExercice(String consigne) {
		int pos = 0;
		for (Exercice ex : exercices) {
			if (ex.equals(consigne) == true) {
				break;
			}
			pos++;
		}
		return (pos);
	}

	public void supprimerExercice(String consigne) {
		int p = rechercherExercice(consigne);
		exercices.remove(p);
	}


	public ArrayList<Exercice> getExercices() {
		return exercices;
	}

	
}