package application;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddPatientPage {
    private Stage primaryStage;
    private Label messageLabel;
    Orthophoniste orthophoniste;
    private Patient patient;
    private HashSet<Orthophoniste> comptesUtilisateurs = new HashSet<Orthophoniste>();

    public AddPatientPage(Stage primaryStage, Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.orthophoniste = orthophoniste;
    }

    public void load(Scene scene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Title
        Text title = new Text("Ajouter un Nouveau Patient");
        title.setFont(Font.font("Ubuntu", 30));
        title.setFill(Color.web("#00215E"));
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);

        // Form
        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER);

        TextField nameField = new TextField();
        nameField.setPromptText("Nom du patient");
        nameField.setMaxWidth(200);

        TextField surenameField = new TextField();
        surenameField.setPromptText("Prénom");
        surenameField.setMaxWidth(200);

        TextField birthdayField = new TextField();
        birthdayField.setPromptText("Date de naissance du patient (jj/mm/yyyy)");
        birthdayField.setMaxWidth(200);

        TextField birthPlaceField = new TextField();
        birthPlaceField.setPromptText("Lieu de naissance du patient");
        birthPlaceField.setMaxWidth(200);

        TextField addressField = new TextField();
        addressField.setPromptText("Adresse du patient");
        addressField.setMaxWidth(200);

        form.getChildren().addAll(nameField, surenameField, birthdayField, birthPlaceField, addressField);

        // Message Label
        messageLabel = new Label();
        form.getChildren().add(messageLabel);

        // Continue Button
        Button continueButton = new Button("Continuer");
        continueButton.getStyleClass().add("button-style");
        continueButton.setOnAction(e -> {
            String name = nameField.getText();
            String surename = surenameField.getText();
            String birthday = birthdayField.getText();
            String birthPlace = birthPlaceField.getText();
            String address = addressField.getText();
            

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dateDeNaissance = LocalDate.parse(birthday, formatter);
            int agePatient = Period.between(dateDeNaissance, LocalDate.now()).getYears();
              InfoDossier infos = new InfoDossier (name,surename,agePatient,birthday,birthPlace,address);

            if (agePatient > 18) {
            	
                AddPatientAdult addPatientAdult = new AddPatientAdult(primaryStage, orthophoniste, infos);
                addPatientAdult.load(scene);
            } else {
                AddPatientKid addPatientKid = new AddPatientKid(primaryStage, orthophoniste, infos);
                addPatientKid.load(scene);
            }
        });

        // Back Button
        Button backButton = new Button("Retour");
        backButton.getStyleClass().add("button-style");
        backButton.setOnAction(e -> {
            MenuPrincipal menuPrincipal = new MenuPrincipal(primaryStage, orthophoniste);
            menuPrincipal.load(scene);
        });

        // Button Container
        VBox buttonBox = new VBox(10, continueButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));

        root.setCenter(form);
        root.setBottom(buttonBox);
        BorderPane.setAlignment(buttonBox, Pos.CENTER);

        Scene addPatientScene = new Scene(root, 800, 700);
        addPatientScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(addPatientScene);
    }

    private void setMessage(String message, String color) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: " + color + ";");
    }
}
