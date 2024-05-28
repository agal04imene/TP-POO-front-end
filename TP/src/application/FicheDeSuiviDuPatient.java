package application;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FicheDeSuiviDuPatient {
    private Stage primaryStage;
    private Patient patient;

    public FicheDeSuiviDuPatient(Stage primaryStage, Patient patient) {
        this.primaryStage = primaryStage;
        this.patient = patient;
    }

    public void load(Scene previousScene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Title
        Text title = new Text("Fiche de Suivi du "+patient.getDossierPatient().getNom()+" "+patient.getDossierPatient().getPrenom());
        title.setFont(Font.font("Ubuntu", 30));
        title.setFill(Color.web("#00215E"));
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);

        // Patient Details
        VBox detailsBox = new VBox(20);
        detailsBox.setAlignment(Pos.CENTER);
        detailsBox.setPadding(new Insets(20));

      
         
         ArrayList <FicheDeSuivi>  listeDeFicheDeSuivi = new  ArrayList<FicheDeSuivi> ();
         listeDeFicheDeSuivi = patient.getDossierPatient().getListeFichesDeSuivi();
         
         ArrayList<Objectif> listeObjectifs = new ArrayList<Objectif>();
         
         for (FicheDeSuivi ficheDeSuivi : listeDeFicheDeSuivi ) {  // parcours des listes de suivi du patient 
        
         
        // Objective details (nom de l'objectif, type de l'objectif, note de l'objectif)
        for (Objectif objectif :listeObjectifs ) {
            Text objectifDetails = new Text("Nom de l'objectif: " + objectif.nom + "\nType de l'objectif: " + objectif.typeStr + "\nNote de l'objectif: " + objectif.note);
            objectifDetails.setFont(Font.font("Ubuntu", 18));
            objectifDetails.setFill(Color.web("#00215E"));
            detailsBox.getChildren().add(objectifDetails);
        }
        
        }

        root.setCenter(detailsBox);

        // Back button
        Button backButton = new Button("Retour");
        backButton.getStyleClass().add("button-style");
        backButton.setOnAction(e -> primaryStage.setScene(previousScene));

        BorderPane.setAlignment(backButton, Pos.CENTER);
        root.setBottom(backButton);

        Scene ficheDeSuiviScene = new Scene(root, 800, 700);
        ficheDeSuiviScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(ficheDeSuiviScene);
    }
}
