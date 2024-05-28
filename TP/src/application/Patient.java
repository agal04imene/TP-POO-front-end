package application;

import java.io.Serializable;

public class Patient implements Serializable {
	public static int nbPatients ;
	private Dossier dossierPatient ;
	private int nbBilansOrthophoniques ;
	
	public Patient(Dossier dossier) {
		nbPatients++ ; // le nombre de patients s'incrémente chaque fois qu'on créé un patient
		dossierPatient = dossier ;
		nbBilansOrthophoniques = 0 ; // le nombre de bilans est initialisé à 0
	}
	
	// Méthode de vérification du nombre de bilans d'un patient (utilisée pour la création de l'anamnèse)
	public boolean VerifierNbBilans() {
		return nbBilansOrthophoniques == 1 ;
		// La méthode retroune vrai si le nombre de bilans est égale à 1, et faux sinon
	}
	
	
	// Getters & Setters
    public Dossier getDossierPatient() {
    	return dossierPatient ;
    }
	
}