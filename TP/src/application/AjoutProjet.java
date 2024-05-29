package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class AjoutProjet {
    private Stage primaryStage;
    private Orthophoniste orthophoniste;
    private Patient patient;

    public AjoutProjet(Stage primaryStage, Patient patient, Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.orthophoniste = orthophoniste;
        this.patient = patient;
    }

    public void load(Scene scene) {
        VBox layout = new VBox(10);
        Label instructionLabel = new Label("Entrez le projet thérapeutique :");
        instructionLabel.setFont(Font.font("Ubuntu", 16));
        TextArea projetTherapeutiqueField = new TextArea();
        projetTherapeutiqueField.getStyleClass().add("centered-text-area");
        projetTherapeutiqueField.setWrapText(true);
        projetTherapeutiqueField.setPromptText("Entrez le projet thérapeutique ici...");

        Button btnAjouterProjet = new Button("Ajouter au dernier BO du patient");
        layout.getChildren().addAll(instructionLabel, projetTherapeutiqueField, btnAjouterProjet);
        layout.setAlignment(Pos.CENTER);
        scene.setRoot(layout);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        btnAjouterProjet.setOnAction(e -> {
            String projetTherapeutique = projetTherapeutiqueField.getText();
            List<BO> listeBOs = patient.getDossierPatient().getListeBOs();
            if (!listeBOs.isEmpty()) {
                BO dernierBO = listeBOs.get(listeBOs.size() - 1);
                dernierBO.setProjetTherapeutique(projetTherapeutique);
                showAlert("Projet thérapeutique ajouté", "Le projet thérapeutique a été ajouté au dernier BO du patient.");
            } else {
                showAlert("Aucun BO trouvé", "Le patient n'a pas encore de bilan orthophonique.");
            }
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
