package application;

import javafx.application.Platform;
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

    public MenuPrincipal(Stage primaryStage) {
        this.primaryStage = primaryStage;
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

        // Option : Paramètres du Compte
        Button accountSettingsButton = createMenuButton("Paramètres du Compte");
        accountSettingsButton.setOnAction(e -> {
            // Naviguer vers la Page des Paramètres du Compte
            AccountSettingsPage accountSettingsPage = new AccountSettingsPage(primaryStage);
            accountSettingsPage.load(scene);
        });

        // Option : Consulter le Dossier d'un Patient
        Button viewPatientRecordsButton = createMenuButton("Consulter le Dossier d'un Patient");
        viewPatientRecordsButton.setOnAction(e -> {
            // Naviguer vers la Page de Consultation du Dossier d'un Patient
            ViewPatientRecordsPage viewPatientRecordsPage = new ViewPatientRecordsPage(primaryStage);
            viewPatientRecordsPage.load(scene);
        });

        // Option : Ajouter un Patient
        Button addPatientButton = createMenuButton("Créer un dossier pour un nouveau Patient");
        addPatientButton.setOnAction(e -> {
            // Naviguer vers la Page d'Ajout d'un Patient
            AddPatientPage addPatientPage = new AddPatientPage(primaryStage);
            addPatientPage.load(scene);
        });

        // Option : Supprimer un Patient
        Button removePatientButton = createMenuButton("Supprimer le dossier d'un Patient");
        removePatientButton.setOnAction(e -> {
            // Naviguer vers la Page de Suppression d'un Patient
            RemovePatientPage removePatientPage = new RemovePatientPage(primaryStage);
            removePatientPage.load(scene);
        });

        // Option : Gestion des Tests et Anamnèses
        Button manageTestsButton = createMenuButton("Gestion des Tests et Anamnèses");
        manageTestsButton.setOnAction(e -> {
            // Naviguer vers la Page de Gestion des Tests et Anamnèses
        	ManageTestsPage manageTestsPage = new ManageTestsPage();
            Stage stage = new Stage();
            manageTestsPage.start(stage);
        });
        
        // Option : Statistiques sur les Patients
        Button StatsPatientsButton = createMenuButton("Statistiques sur les Patients");
        StatsPatientsButton.setOnAction(e -> {
            // Naviguer vers la Page de Gestion des Tests et Anamnèses
            //ManageTestsPage manageTestsPage = new ManageTestsPage(primaryStage);
            //manageTestsPage.load(scene);
            
            StatsPatientsPage statsPage = new StatsPatientsPage();
            statsPage.start(new Stage());
        });
        
        // Option : Se déconnecter de l'application
        Button logOutButton = createMenuButton("Se déconnecter de l'application");
        logOutButton.setOnAction(e -> {
        	Platform.exit(); // Close the JavaFX application
        });

        menuOptions.getChildren().addAll(
                accountSettingsButton,
                viewPatientRecordsButton,
                addPatientButton,
                removePatientButton,
                manageTestsButton,
                StatsPatientsButton,
                logOutButton
        );

        root.setCenter(menuOptions);

        // Bouton Retour
        Button backButton = new Button("Retour");
        backButton.getStyleClass().add("button-style");
        backButton.setOnAction(e -> {
        	HomePage homePage = new HomePage(primaryStage);
            homePage.load(scene);
        });
        root.setBottom(backButton);

        Scene menuScene = new Scene(root, 800, 650);
        menuScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(menuScene);
        
        primaryStage.centerOnScreen();
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("menu-button");
        button.setPrefWidth(500);
        button.setPrefHeight(60);
        return button;
    }
}
