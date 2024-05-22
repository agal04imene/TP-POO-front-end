package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PatientsParTrouble extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Statistiques");

        // Create a form for user input
        Label label = new Label("Si vous souhaitez afficher les noms de patients souffrant d'un trouble spécifique, veuillez entrer le nom de ce trouble ci-dessous :");
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);
        label.getStyleClass().add("label-style");

        TextField textField = new TextField();
        textField.setMaxWidth(400);
        textField.getStyleClass().add("textfield-style");

        Button button = new Button("Afficher");
        button.getStyleClass().add("button-style");

        VBox form = new VBox(10);
        form.setPadding(new Insets(20));
        form.setAlignment(Pos.CENTER); // Center the VBox content
        form.getChildren().addAll(label, textField, button);

        StackPane root = new StackPane(form);
        Scene StatsScene = new Scene(root, 500, 350);
        StatsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(StatsScene);
        primaryStage.show();
    }
}
