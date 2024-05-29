package application;

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

public class PatientsInfos {
    private Stage primaryStage;
    private Patient patient;
    private Orthophoniste orthophoniste;

    public PatientsInfos(Stage primaryStage,Patient patient,Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.patient = patient;
        this.orthophoniste=orthophoniste;
    }

    public void load(Scene scene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        
        // Title
        Label titleLabel = new Label("Informations du Compte");
        titleLabel.getStyleClass().add("title-label");
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setTop(titleLabel);

        // Create a GridPane to display account information
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.getStyleClass().add("info-grid");

        // Retrieve and display account information
        Label nameLabel = createInfoLabel("Nom du patient : " + patient.getDossierPatient().getNom());
        Label firstNameLabel = createInfoLabel("Prénom : " + patient.getDossierPatient().getPrenom());
        Label birthdayLabel = createInfoLabel("Date de naissance du patient : " + patient.getDossierPatient().getDateNaissance());
        Label ageLabel = createInfoLabel("Age du patient : " + patient.getDossierPatient().getAge());
        Label birthPlaceLabel = createInfoLabel("Lieu de naissance du patient : " + patient.getDossierPatient().getLieuNaissance());
       


        // Add labels to the gridPane
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(firstNameLabel, 0, 1);
        gridPane.add(birthdayLabel, 0, 2);
        gridPane.add(ageLabel, 0, 3);
        gridPane.add(birthPlaceLabel, 0, 4);

        // Wrap the gridPane in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);

        root.setCenter(scrollPane);

        // Add a back button to return to the account settings page
        Button backButton = new Button("Retour");
        backButton.getStyleClass().add("button-style");
        backButton.setOnAction(e -> {
        	ListPatientInfos listPage = new ListPatientInfos(primaryStage, orthophoniste);
        	listPage.load(scene);
        });
        
        VBox bottomBox = new VBox(backButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20, 0, 0, 0));
        root.setBottom(bottomBox);

        Scene infoScene = new Scene(root, 700, 600);
        infoScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(infoScene);
    }

    private Label createInfoLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("info-label");
        return label;
    }
}
