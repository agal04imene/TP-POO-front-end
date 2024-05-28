package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PatientTestPage {
    private Stage primaryStage;
    private Patient patient;
    private HashSet<Patient> listePatients = new HashSet<Patient>();
    Orthophoniste orthophoniste;

    public PatientTestPage(Stage primaryStage, Patient patient,Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.patient = patient;
        this.orthophoniste=orthophoniste;
    }

    public void load(Scene previousScene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Title
        String titleText = patient.getDossierPatient().getNom() + " " +
                           patient.getDossierPatient().getPrenom();
        Text title = new Text(titleText);
        title.setFont(Font.font("Ubuntu", 30));
        title.setFill(Color.web("#00215E"));
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);

        // Test details
        VBox testsBox = new VBox(20);
        testsBox.setAlignment(Pos.CENTER);
        testsBox.setPadding(new Insets(20));

        listePatients = orthophoniste.getListePatients();
        for (Patient patient0 : listePatients) {
            if (patient0.getDossierPatient().getNom().equals(patient.getDossierPatient().getNom()) &&
                patient0.getDossierPatient().getPrenom().equals(patient.getDossierPatient().getPrenom())) {

                ArrayList<BO> listeBOs = patient0.getDossierPatient().getListeBOs();
                Iterator<BO> it_BOs = listeBOs.iterator();

                while (it_BOs.hasNext()) {
                    BO bo = it_BOs.next();
                    EpreuveClinique[] listeEpreuveClinique = bo.getListeEpreuves();

                    // Parcours le tableau des epreuves cliniques 
                    for (int i = 0; i < listeEpreuveClinique.length; i++) {
                        ArrayList<Test> listeTests = listeEpreuveClinique[i].getListeTests();
                        Iterator<Test> it_Tests = listeTests.iterator();

                        int j = 1;
                        if (listeTests.isEmpty()) {
                            Label noTestsLabel = createTestLabel("Ce patient n'a pas des tests");
                            testsBox.getChildren().add(noTestsLabel);
                        } else {
                            while (it_Tests.hasNext()) {
                                Test test = it_Tests.next();
                                String labelText = "Test " + j + ": " + test.getConclusion();
                                Label testLabel = createTestLabel(labelText);
                                testsBox.getChildren().add(testLabel);
                                j++;
                            }
                        }
                    }
                }
                break;
            }
        }

        root.setCenter(testsBox);

        // Back button
        Button backButton = new Button("Retour");
        backButton.getStyleClass().add("button-style");
        backButton.setOnAction(e -> primaryStage.setScene(previousScene));

        BorderPane.setAlignment(backButton, Pos.CENTER);
        root.setBottom(backButton);

        Scene patientTestsScene = new Scene(root, 800, 700);
        patientTestsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(patientTestsScene);
    }

 

    private Label createTestLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Ubuntu", 18));
        label.setPadding(new Insets(20));
        label.setStyle("-fx-background-color: #E0E0E0; -fx-background-radius: 15; -fx-border-radius: 15;");
        label.setAlignment(Pos.CENTER);
        label.setPrefWidth(400);
        label.setPrefHeight(100); // Set the height to make it taller
        label.setWrapText(true); // Enable text wrapping
        return label;
    }
}
