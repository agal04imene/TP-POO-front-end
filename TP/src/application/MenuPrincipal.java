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

public class MenuPrincipal {
    private Stage primaryStage;
    private Orthophoniste orthophoniste;

    public MenuPrincipal(Stage primaryStage, Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.orthophoniste = orthophoniste;
    }

    public void load(Scene scene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Title
        Text title = new Text("Menu");
        title.setFont(Font.font("Ubuntu", 38));
        title.setFill(Color.web("#00215E"));
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);

        // Menu Options
        VBox menuOptions = new VBox(20);
        menuOptions.setAlignment(Pos.CENTER);
        menuOptions.setPadding(new Insets(20));

        // Option: Paramètres du Compte
        Button accountSettingsButton = createMenuButton("Paramètres du Compte");
        accountSettingsButton.setOnAction(e -> {
            // Navigate to Account Settings Page
            AccountSettingsPage accountSettingsPage = new AccountSettingsPage(primaryStage, orthophoniste);
            accountSettingsPage.load(scene);
        });

        // Option: Gestions des dossiers des patients
        Button managePatientRecordsButton = createMenuButton("Gestions des dossiers des patients");
        managePatientRecordsButton.setOnAction(e -> {
            // Navigate to Manage Patient Records Page
            GestionDossiersPatients gestionDossiersPatients = new GestionDossiersPatients(primaryStage, orthophoniste);
            gestionDossiersPatients.load(scene);
        });

        // Option: Gestion des Tests et Anamnèses
        Button manageTestsButton = createMenuButton("Gestion des Tests et Anamnèses");
        manageTestsButton.setOnAction(e -> {
            // Navigate to Manage Tests Page
            PatientsListManageTests manageTestsPage = new PatientsListManageTests(primaryStage, orthophoniste);
            manageTestsPage.load(scene);
        });

        // Option : Statistiques sur les Patients
        Button statsPatientsButton = createMenuButton("Statistiques sur les Patients");
        statsPatientsButton.setOnAction(e -> {
            // Navigate to Stats Patients Page
            StatsPatientsPage statsPage = new StatsPatientsPage(orthophoniste);
            statsPage.start(new Stage());
        });

        // Option: Gestion des rendez-vous
        Button gestionRendezVousButton = createMenuButton("Gestion des rendez-vous");
        gestionRendezVousButton.setOnAction(e -> {
            // Navigate to GestionRendezVous Page
            GestionRendezVous gestionRendezVous = new GestionRendezVous(primaryStage, orthophoniste);
            gestionRendezVous.load(scene);
        });

        // Option: Se déconnecter de l'application
        Button logOutButton = createMenuButton("Se déconnecter de l'application");
        logOutButton.setOnAction(e -> {
            // Navigate to Log Out Page
            // LogOutPage logOutPage = new LogOutPage(primaryStage);
            // logOutPage.load(scene);
        });

        menuOptions.getChildren().addAll(
                accountSettingsButton,
                managePatientRecordsButton,
                manageTestsButton,
                statsPatientsButton,
                gestionRendezVousButton,
                logOutButton
        );

        root.setCenter(menuOptions);

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
