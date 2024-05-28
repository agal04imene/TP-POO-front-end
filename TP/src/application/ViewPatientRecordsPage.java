package application;

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

public class ViewPatientRecordsPage {
    private Stage primaryStage;
    Orthophoniste orthophoniste;
    public ViewPatientRecordsPage(Stage primaryStage,Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.orthophoniste=orthophoniste;
    }

    public void load(Scene scene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        
        // Title
        Text title = new Text("Consultations des dossiers des patients");
        title.setFont(Font.font("Ubuntu", 30));
        title.setFill(Color.web("#00215E"));
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);

        // Menu Options
        VBox menuOptions = new VBox(20);
        menuOptions.setAlignment(Pos.CENTER);
        menuOptions.setPadding(new Insets(20));

        // Option : Afficher la liste des rendez-vous
        Button viewAppointmentsButton = createMenuButton("Afficher la liste des rendez-vous");
        viewAppointmentsButton.setOnAction(e -> {
          
            AgendaView agendaView = new AgendaView(primaryStage);
            agendaView.load(new Scene(new BorderPane()));
        });

        // Option : Afficher l'observation d'un rendez-vous spécifique
        Button viewAppointmentObservationButton = createMenuButton("Afficher l'observation d'un rendez-vous spécifique");
        viewAppointmentObservationButton.setOnAction(e -> {
            
           // SpecificAppointmentObservationPage specificAppointmentObservationPage = new SpecificAppointmentObservationPage(primaryStage);
           // specificAppointmentObservationPage.load(scene);
        });

        // Option : Compte rendu des tests du patient
        Button patientTestReportsButton = createMenuButton("Compte rendu des tests du patient");
        patientTestReportsButton.setOnAction(e -> {
            
        	PatientsListTests patientsListTests = new PatientsListTests(primaryStage,orthophoniste);
        	patientsListTests.load(scene);
        });

        // Option : Bilans orthophoniques du patient
        Button patientAssessmentReportsButton = createMenuButton("Bilans orthophoniques du patient");
        patientAssessmentReportsButton.setOnAction(e -> {
          
        	PatientsListBOs patientsListBOs = new PatientsListBOs(primaryStage,orthophoniste);
        	patientsListBOs.load(scene);
        });

        // Option : Listes de suivis
        Button followUpListsButton = createMenuButton("Listes de suivis");
        followUpListsButton.setOnAction(e -> {
            
        	 PatientList_FicheDeSuivi patientList_FicheDeSuivi = new  PatientList_FicheDeSuivi(primaryStage,orthophoniste);
        	 patientList_FicheDeSuivi.load(scene);
        });

        menuOptions.getChildren().addAll(
                viewAppointmentsButton,
                viewAppointmentObservationButton,
                patientTestReportsButton,
                patientAssessmentReportsButton,
                followUpListsButton
        );

        root.setCenter(menuOptions);

        // Back button
        Button backButton = new Button("Retour");
        backButton.getStyleClass().add("button-style");
        backButton.setOnAction(e -> {
            MenuPrincipal menuPage = new MenuPrincipal(primaryStage, orthophoniste);
            menuPage.load(scene);
        });
        root.setBottom(backButton);

        Scene menuScene = new Scene(root, 800, 700);
        menuScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(menuScene);
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("menu-button");
        button.setPrefWidth(500);
        button.setPrefHeight(60);
        return button;
    }
}
