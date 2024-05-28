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

public class GestionDossiersPatients {
    private Stage primaryStage;
    private Orthophoniste orthophoniste;

    public GestionDossiersPatients(Stage primaryStage, Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.orthophoniste = orthophoniste;
    }

    public void load(Scene scene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Title
        Text title = new Text("Gestions des dossiers des patients");
        title.setFont(Font.font("Ubuntu", 32));
        title.setFill(Color.web("#00215E"));
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);

        // Menu Options
        VBox menuOptions = new VBox(20);
        menuOptions.setAlignment(Pos.CENTER);
        menuOptions.setPadding(new Insets(20));

        // Option: Consulter le Dossier d'un Patient
        Button viewPatientRecordsButton = createMenuButton("Consulter le Dossier d'un Patient");
        viewPatientRecordsButton.setOnAction(e -> {
            // Navigate to View Patient Records Page
            ViewPatientRecordsPage viewPatientRecordsPage = new ViewPatientRecordsPage(primaryStage,orthophoniste);
            viewPatientRecordsPage.load(scene);
        });

        // Option: Ajouter un Patient
        Button addPatientButton = createMenuButton("Créer un dossier pour un nouveau Patient");
        addPatientButton.setOnAction(e -> {
            // Navigate to Add Patient Page
            AddPatientPage addPatientPage = new AddPatientPage(primaryStage, orthophoniste);
            addPatientPage.load(scene);
        });

        // Option: Supprimer un Patient
        Button removePatientButton = createMenuButton("Supprimer le dossier d'un Patient");
        removePatientButton.setOnAction(e -> {
            // Navigate to Remove Patient Page
            RemovePatientPage removePatientPage = new RemovePatientPage(primaryStage, orthophoniste);
            removePatientPage.load(scene);
        });
        
        // Back button
        Button backButton = new Button("Retour");
        backButton.getStyleClass().add("button-style");
        backButton.setOnAction(e -> {
            MenuPrincipal menuPage = new MenuPrincipal(primaryStage, orthophoniste);
            menuPage.load(scene);
        });
        root.setBottom(backButton);

        menuOptions.getChildren().addAll(
                viewPatientRecordsButton,
                addPatientButton,
                removePatientButton
        );
        

        root.setCenter(menuOptions);

        Scene gestionDossiersPatientsScene = new Scene(root, 800, 700);
        gestionDossiersPatientsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(gestionDossiersPatientsScene);
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("menu-button");
        button.setPrefWidth(500);
        button.setPrefHeight(60);
        return button;
    }
}
