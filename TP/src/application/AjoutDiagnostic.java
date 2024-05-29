package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AjoutDiagnostic {
    private Stage primaryStage;
    private Orthophoniste orthophoniste;
    private Patient patient;

    public AjoutDiagnostic(Stage primaryStage, Patient patient, Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.orthophoniste = orthophoniste;
        this.patient = patient;
    }

    public void load(Scene scene) {
        VBox layout = new VBox(10);
        Label instruction = new Label("Ajouter un diagnostic : \n Saisir le nombre de troubles à ajouter :");
        instruction.setFont(Font.font("Ubuntu", 16));
        TextField nombreTroublesField = new TextField();
        nombreTroublesField.setPromptText("Nombre de troubles à ajouter");
        Button btnSuivant = new Button("Suivant");

        layout.getChildren().addAll(instruction, nombreTroublesField, btnSuivant);
        layout.setAlignment(Pos.CENTER);
        scene.setRoot(layout);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        btnSuivant.setOnAction(e -> {
            try {
                int nbTroubles = Integer.parseInt(nombreTroublesField.getText());
                afficherAjoutTroubles(scene, nbTroubles);
            } catch (NumberFormatException ex) {
                showAlert("Format invalide", "Veuillez entrer un nombre valide.");
            }
        });
    }

    private void afficherAjoutTroubles(Scene scene, int nbTroubles) {
        VBox layout = new VBox(10);

        List<TextField> nomTroubleFields = new ArrayList<>();
        List<ComboBox<CatTrouble>> categorieTroubleCombos = new ArrayList<>();

        for (int i = 0; i < nbTroubles; i++) {
            HBox troubleBox = new HBox(10);
            TextField nomTroubleField = new TextField();
            nomTroubleField.setPromptText("Nom du trouble " + (i + 1));
            ComboBox<CatTrouble> categorieCombo = new ComboBox<>();
            categorieCombo.setItems(FXCollections.observableArrayList(CatTrouble.values()));
            categorieCombo.setPromptText("Catégorie");
            troubleBox.getChildren().addAll(nomTroubleField, categorieCombo);
            nomTroubleFields.add(nomTroubleField);
            categorieTroubleCombos.add(categorieCombo);
            layout.getChildren().add(troubleBox);
        }

        Button btnSuivant = new Button("Suivant");
        Button btnAnnuler = new Button("Annuler");
        HBox buttonsBox = new HBox(20);
        buttonsBox.getChildren().addAll(btnAnnuler, btnSuivant);
        buttonsBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(buttonsBox);
        layout.setAlignment(Pos.CENTER);
        scene.setRoot(layout);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        btnSuivant.setOnAction(e -> {
            Diagnostic diagnostic = new Diagnostic();
            for (int i = 0; i < nbTroubles; i++) {
                String nom = nomTroubleFields.get(i).getText();
                CatTrouble categorie = categorieTroubleCombos.get(i).getValue();
                String cat = null;
                switch (categorie) {
        		case Deglutition : cat = "Trouble de déglutition" ; break ;
        		case NeuroDevloppemental : cat = "Trouble neuro-dévloppemental" ; break ;
        		case Cognitif : cat = "Trouble cognitif" ; break ;
        		}
                Trouble trouble = new Trouble(nom, cat);
                try {
					diagnostic.ajouterTrouble(trouble);
				} catch (TroubleDejaExistantException e1) {
					e1.printStackTrace();
				}
            }
            
            List<BO> listeBOs = patient.getDossierPatient().getListeBOs();
            if (!listeBOs.isEmpty()) {
            	patient.getDossierPatient().getLatestBO().setDiagnostic(diagnostic);
                showAlert("Diagnostic ajouté", "Le diagnostic a été ajouté au dernier bilan orthophonique du patient.");
            } else {
                showAlert("Aucun BO trouvé", "Le patient n'a pas encore de bilan orthophonique.");
            }
        });

        btnAnnuler.setOnAction(e -> {
            load(scene);
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
