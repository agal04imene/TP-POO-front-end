package application;
import java.util.ArrayList;
import java.util.Arrays;

public class EpreuveClinique {
    
    private String[] observationClinique; 
    ArrayList<Test> listeTest = new ArrayList<Test>();
    
    
    public EpreuveClinique() {
        this.observationClinique = new String[0];
      
    }

  
  
    // Méthode d'ajout d'une observation
    public void ajouterObservation(String nouvelleObservation) {
        ArrayList<String> observationsList = new ArrayList<>(Arrays.asList(observationClinique));
        if (!observationsList.contains(nouvelleObservation)) {
            observationsList.add(nouvelleObservation);
            observationClinique = observationsList.toArray(new String[0]);
            System.out.println("Observation ajoutée avec succès.");
        } else {
            System.out.println("L'observation existe déjà.");
        }
    }

    // Méthode de suppression d'une observation
    public void supprimerObservation(String observationASupprimer) {
        ArrayList<String> observationsList = new ArrayList<>(Arrays.asList(observationClinique));
        if (observationsList.contains(observationASupprimer)) {
            observationsList.remove(observationASupprimer);
            observationClinique = observationsList.toArray(new String[0]);
            System.out.println("Observation supprimée avec succès.");
        } else {
            System.out.println("L'observation n'existe pas.");
        }
    }
    
    public ArrayList<Test> getListeTests (){
    	return this.listeTest;
    }


    // Méthode d'affichage des observations (pour vérifier)
    public void afficherObservations() {
        System.out.println("Observations cliniques : ");
        for (String observation : observationClinique) {
            System.out.println("- " + observation);
        }
    }
    
    // Getters and setters for observationClinique
    public String[] getObservationClinique() {
        return observationClinique;
    }

    public void setObservationClinique(String[] observationClinique) {
        this.observationClinique = observationClinique;
    }

  
    
  
   
}
