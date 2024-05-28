package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

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

public class PatientsListManageTests {
    private Stage primaryStage;
    private HashSet<Patient> listePatients = new HashSet<Patient>();
    Orthophoniste orthophoniste;
    
    public PatientsListManageTests(Stage primaryStage,Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.orthophoniste =orthophoniste;
    }

    public void load(Scene scene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Title
        Text title = new Text("Les patients");
        title.setFont(Font.font("Ubuntu", 30));
        title.setFill(Color.web("#00215E"));
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);

        // Labels
        VBox labelsBox = new VBox(20);
        labelsBox.setAlignment(Pos.CENTER);
        labelsBox.setPadding(new Insets(20));

       

        
        if (orthophoniste.getListePatients() == null) {
       	 orthophoniste.setListePatients(new HashSet<>())  ;
       	}
        
        listePatients = orthophoniste.getListePatients();
        		
        for (Patient patient : listePatients) {
            Button button = createLightGrayButton("\"" + patient.getDossierPatient().getNom() + " " + patient.getDossierPatient().getPrenom() + "\"\n\t" + patient.getDossierPatient().getAge() + " ans");
            button.setOnAction(e -> {
            	ManageTestsPage manageTests = new ManageTestsPage(primaryStage, patient);
            	manageTests.load(scene);
            });
            labelsBox.getChildren().add(button);
        }

        root.setCenter(labelsBox);

        // Back button
        Button backButton = new Button("Retour");
        backButton.getStyleClass().add("button-style");
        backButton.setOnAction(e -> {
            ViewPatientRecordsPage viewPatientRecordsPage = new ViewPatientRecordsPage(primaryStage,orthophoniste);
            viewPatientRecordsPage.load(scene);
        });

        BorderPane.setAlignment(backButton, Pos.CENTER);
        root.setBottom(backButton);

        Scene patientAssessmentReportsScene = new Scene(root, 800, 700);
        patientAssessmentReportsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(patientAssessmentReportsScene);
    }


    

    private Button createLightGrayButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("light-gray-button"); // Assuming you have a CSS class for light gray buttons
        button.setPrefWidth(200); // Adjust the width as needed
        button.setPrefHeight(40); // Adjust the height as needed
        return button;
    }
}
