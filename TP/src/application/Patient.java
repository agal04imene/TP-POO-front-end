package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;

public class Patient implements Serializable {

	private static final long serialVersionUID = -6021587280855638885L;
	
	public static int nbPatients ;
	private Dossier dossierPatient ;
	private int nbRDV ;
	private ArrayList<Anamnese> listeAnamneses ;
	private ArrayList<Questionnaire> listeQuestionnaires;
	private ArrayList<TestExercices> listeSeriesExos;
	
	public Patient(Dossier dossier) {
		
		nbPatients++ ; // le nombre de patients s'incrémente chaque fois qu'on créé un patient
		dossierPatient = dossier ;
		nbRDV = 1 ; // le nombre de RDVs est initialisé à 1
	}
	
	// Méthode de vérification du nombre de RDVs d'un patient (utilisée pour la création de l'anamnèse)
	public boolean VerifierNbBilans() {
		return nbRDV == 1 ;
		// La méthode retroune vrai si le nombre de bilans est égale à 1, et faux sinon
	}
	
	
	// Getters & Setters
    public Dossier getDossierPatient() {
    	return dossierPatient ;
    }
    
    public int getNbRDV () {
    	return nbRDV ;
    }

	public ArrayList<Anamnese> getListeAnamneses() {
		return listeAnamneses;
	}

	public ArrayList<Questionnaire> getListeQuestionnaires() {
		return listeQuestionnaires;
	}

	public ArrayList<TestExercices> getListeSeriesExercices() {
		return listeSeriesExos;
	}

	public void setNbRDV(int i) {
		nbRDV = i;
	}
	
}