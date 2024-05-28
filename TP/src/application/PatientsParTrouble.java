package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PatientsParTrouble extends Application {
    private HashSet<Patient> listePatients = new HashSet<Patient>();
    Orthophoniste orthophoniste;
    
    public PatientsParTrouble(Orthophoniste orthophoniste) {
    	this.orthophoniste=orthophoniste;
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Statistiques");

        // Create a form for user input
        Label label = new Label("Si vous souhaitez afficher les noms de patients souffrant d'un trouble spécifique, veuillez entrer le nom de ce trouble ci-dessous :");
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);
        label.getStyleClass().add("label-style");

        TextField textField = new TextField();
        textField.setMaxWidth(400);
        textField.getStyleClass().add("textfield-style");

        
        VBox form = new VBox(10);
        form.setPadding(new Insets(20));
        
        Button button = new Button("Afficher");
        button.getStyleClass().add("button-style");
        button.setOnAction(e -> {
            String  troubleNom = textField.getText();
            
            listePatients=orthophoniste.getListePatients();
            
         // Récupération des pourcentages
            Iterator<Patient> it_Patients = listePatients.iterator();			
    		while(it_Patients.hasNext()) {
    			
    			Patient p = it_Patients.next();
    			ArrayList<BO> listeBOs = p.getDossierPatient().getListeBOs(); // La liste des BOs du patient courrant
    			
    			Iterator<BO> it_BOs = listeBOs.iterator();
    			
    			while (it_BOs.hasNext()) {
    				BO bo = it_BOs.next();				
    				ArrayList<Trouble> listeTroubles= bo.getDiagnostic().listeTroubles;
    				
    				Iterator<Trouble> it_Troubles = listeTroubles.iterator();
    				while (it_Troubles.hasNext()) {
    					Trouble trouble = it_Troubles.next();
    					if (trouble.nom.equalsIgnoreCase(troubleNom)) {
    					    // Create a label to display the patient's name
    					    Label patientNameLabel = new Label(p.getDossierPatient().getNom()+" "+p.getDossierPatient().getPrenom());
    					    patientNameLabel.getStyleClass().add("patient-name-label");
			        
    					    form.getChildren().add(patientNameLabel);
    					}  					
    				}  				
    			}
    		}   
    		
        });

        form.setAlignment(Pos.CENTER); // Center the VBox content
        form.getChildren().addAll(label, textField, button);

        StackPane root = new StackPane(form);
        Scene StatsScene = new Scene(root, 500, 350);
        StatsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(StatsScene);
        primaryStage.show();
    }
}