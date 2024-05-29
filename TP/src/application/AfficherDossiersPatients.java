package application;

import java.util.Iterator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AfficherDossiersPatients {
    private Stage primaryStage;
    private Orthophoniste orthophoniste;

    public AfficherDossiersPatients(Stage primaryStage, Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.orthophoniste = orthophoniste;
    }

    public void load(Scene scene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        
        // Title
        Label titleLabel = new Label("Dossiers des Patients");
        titleLabel.getStyleClass().add("title-label");
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setTop(titleLabel);

        // Create a VBox to hold patient information
        VBox patientInfoBox = new VBox(20);
        patientInfoBox.setAlignment(Pos.CENTER);
        patientInfoBox.setPadding(new Insets(20));

        // Retrieve patient information and add to the VBox
        Iterator<Patient> iterator = orthophoniste.getListePatients().iterator();
        while (iterator.hasNext()) {
            Patient patient = iterator.next();
            Dossier dossier = patient.getDossierPatient();
            
            // Create a GridPane for each patient's information
            GridPane patientGrid = new GridPane();
            patientGrid.setAlignment(Pos.CENTER);
            patientGrid.setPadding(new Insets(20));
            patientGrid.setHgap(10);
            patientGrid.setVgap(10);
            patientGrid.getStyleClass().add("info-grid");

            // Add patient information to the GridPane
            Label nameLabel = createInfoLabel("Nom: " + dossier.getNom());
            Label firstNameLabel = createInfoLabel("Prénom: " + dossier.getPrenom());
            Label dobLabel = createInfoLabel("Date de naissance: " + dossier.getDateNaissance());
            Label addressLabel = createInfoLabel("Adresse: " + dossier.getLieuNaissance());

            patientGrid.add(nameLabel, 0, 0);
            patientGrid.add(firstNameLabel, 0, 1);
            patientGrid.add(dobLabel, 0, 2);
            patientGrid.add(addressLabel, 0, 3);

            patientInfoBox.getChildren().add(patientGrid);
        }

        // Wrap the patientInfoBox in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(patientInfoBox);
        scrollPane.setFitToWidth(true);

        root.setCenter(scrollPane);

        // Add a back button to return to the account settings page
        Button backButton = new Button("Retour");
        backButton.getStyleClass().add("button-style");
        backButton.setOnAction(e -> {
            AccountSettingsPage accountSettingsPage = new AccountSettingsPage(primaryStage, orthophoniste);
            accountSettingsPage.load(scene);
        });

        VBox bottomBox = new VBox(backButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20, 0, 0, 0));
        root.setBottom(bottomBox);

        Scene patientInfoScene = new Scene(root, 700, 600);
        patientInfoScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(patientInfoScene);
    }

    private Label createInfoLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("info-label");
        return label;
    }
}
