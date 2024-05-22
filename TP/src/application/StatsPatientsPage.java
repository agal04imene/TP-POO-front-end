package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StatsPatientsPage extends Application {

	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Statistiques");

        Button AfficherPourcentages = createMenuButton("Pourcentage de patients par catégorie de troubles");
        AfficherPourcentages.setOnAction(e -> {
        	PourcentageParCategorie statsPage = new PourcentageParCategorie();
            statsPage.start(new Stage());
        });
        
        Button AfficherPatients = createMenuButton("Liste des patients souffrant d'un trouble spécifique");
        AfficherPatients.setOnAction(e -> {
        	PatientsParTrouble statsPage = new PatientsParTrouble();
            statsPage.start(new Stage());
        });
        
        VBox buttonsBox = new VBox(20);
        buttonsBox.getChildren().addAll(AfficherPourcentages, AfficherPatients);
        buttonsBox.setAlignment(Pos.CENTER);
        
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setCenter(buttonsBox);
        
        Scene StatsScene = new Scene(root, 600, 400);
        primaryStage.setScene(StatsScene);
        StatsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.show();
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
