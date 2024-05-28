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

import java.util.ArrayList;

public class ListeDesBOsDuPatient {
    private Stage primaryStage;
    private Patient patient;

    public ListeDesBOsDuPatient(Stage primaryStage, Patient patient) {
        this.primaryStage = primaryStage;
        this.patient = patient;
    }

    public void load(Scene previousScene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Title
        String titleText = "Liste des BOs pour " + patient.getDossierPatient().getNom() + " " + patient.getDossierPatient().getPrenom();
        Text title = new Text(titleText);
        title.setFont(Font.font("Ubuntu", 30));
        title.setFill(Color.web("#00215E"));
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);

        // BO buttons
        VBox boBox = new VBox(20);
        boBox.setAlignment(Pos.CENTER);
        boBox.setPadding(new Insets(20));

        ArrayList<BO> listeBOs = patient.getDossierPatient().getListeBOs();
        for (BO bo : listeBOs) {
            Button button = createLightGrayButton("BO");
            button.setOnAction(e -> showBODetailsPage(bo, previousScene));
            boBox.getChildren().add(button);
        }

        root.setCenter(boBox);

        // Back button
        Button backButton = new Button("Retour");
        backButton.getStyleClass().add("button-style");
        backButton.setOnAction(e -> primaryStage.setScene(previousScene));

        BorderPane.setAlignment(backButton, Pos.CENTER);
        root.setBottom(backButton);

        Scene listeBOsScene = new Scene(root, 800, 700);
        listeBOsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(listeBOsScene);
    }

    private Button createLightGrayButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("light-gray-button");
        button.setPrefWidth(200);
        button.setPrefHeight(40);
        return button;
    }

    private void showBODetailsPage(BO bo, Scene previousScene) {
        BODetailsPage boDetailsPage = new BODetailsPage(primaryStage, bo);
        boDetailsPage.load(previousScene);
    }
}
