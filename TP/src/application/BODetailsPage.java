package application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

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
import javafx.stage.Modality;
import javafx.stage.Stage;


public class BODetailsPage {
    private Stage primaryStage;
    private BO bo;

    public BODetailsPage(Stage primaryStage, BO bo) {
        this.primaryStage = primaryStage;
        this.bo = bo;
    }

    public void load(Scene previousScene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Title
        String titleText = "BO Details";
        Text title = new Text(titleText);
        title.setFont(Font.font("Ubuntu", 30));
        title.setFill(Color.web("#00215E"));
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);

        // Menu
        VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER_LEFT);
        menuBox.setPadding(new Insets(20));
    
        Button anamneseButton = createMenuButton("Afficher Anamnèse");
        anamneseButton.setOnAction(e -> displayAnamnese(bo));
        menuBox.getChildren().add(anamneseButton);

        Button testsButton = createMenuButton("Afficher les Tests du Patient");
      //  testsButton.setOnAction(e -> displayTests(bo));
        menuBox.getChildren().add(testsButton);

        Button diagnosticButton = createMenuButton("Afficher le Diagnostic");
        diagnosticButton.setOnAction(e -> displayDiagnostic(bo));
        menuBox.getChildren().add(diagnosticButton);

        Button projetTherapeutiqueButton = createMenuButton("Afficher le Projet Thérapeutique");
        projetTherapeutiqueButton.setOnAction(e -> displayProjetTherapeutique(bo));
        menuBox.getChildren().add(projetTherapeutiqueButton);

        root.setLeft(menuBox);

        // Back button
        Button backButton = new Button("Retour");
        backButton.getStyleClass().add("button-style");
        backButton.setOnAction(e -> primaryStage.setScene(previousScene));

        BorderPane.setAlignment(backButton, Pos.CENTER);
        root.setBottom(backButton);

        Scene boDetailsScene = new Scene(root, 600, 400);
        boDetailsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(boDetailsScene);
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("light-gray-button");
        button.setPrefWidth(200);
        button.setPrefHeight(40);
        return button;
    }

    
    
    private void displayAnamnese(BO bo) {
        Anamnese anamnese = bo.getAnamnese();
        if (!anamnese.listeQuestions.isEmpty()) {
            Set<QstLibreAnamnese> listeQuestions = anamnese.listeQuestions;

            // Create a new Stage for the popup window
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Anamnèse Details");

            // Create a VBox to hold the content
            VBox contentBox = new VBox(10);
            contentBox.setAlignment(Pos.CENTER);
            contentBox.setPadding(new Insets(20));
            
            int i=1;
            // Loop through the set of questions and create labels for each one
            for (QstLibreAnamnese qst : listeQuestions) {
                Label questionLabel = new Label("Question : "+ (i+1) + qst.enonce);
                contentBox.getChildren().add(questionLabel);
            }

            // Create a Scene with the VBox as its root
            Scene scene = new Scene(contentBox, 400, 300);

            // Set the scene to the popup stage
            popupStage.setScene(scene);

            // Show the popup stage
            popupStage.showAndWait();
        }
    }


    
    
    // Méthode pour afficher les informations des tests du patient
   /* private void displayTests(BO bo) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(primaryStage);

        VBox testsBox = new VBox(20);
        testsBox.setAlignment(Pos.CENTER);
        testsBox.setPadding(new Insets(20));

        EpreuveClinique[] listeEpreuveClinique = bo.getListeEpreuves();

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
                    String labelText = "Nom du test " + j + ": " + test.getNom() + " \n Capacité du test : " + test.getCapacite() + " \n Score total du Test : " + test.scoreTotal;
                    Label testLabel = createTestLabel(labelText);
                    testsBox.getChildren().add(testLabel);
                    j++;
                }
            }
        }

        Scene popupScene = new Scene(testsBox, 600, 400);
        popupScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }*/


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
    
 
   // Méthode pour afficher le diagnostic
    private void displayDiagnostic(BO bo) {
        Diagnostic diagnostic = bo.getDiagnostic();
        ArrayList<Trouble> listeTroubles = diagnostic.listeTroubles;
        
        // Create a new Stage for the popup window
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Diagnostic Details");
        
        // Create a VBox to hold the content
        VBox contentBox = new VBox(10);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(20));
        
        // Loop through the list of troubles and create labels for each one
        for (Trouble trouble : listeTroubles) {
            // Create labels for the name and category of the trouble
            Label nameLabel = new Label("Nom du trouble: " + trouble.nom);
            Label categoryLabel = new Label("Catégorie du trouble: " + trouble.categorieStr);
            
            // Add the labels to the VBox
            contentBox.getChildren().addAll(nameLabel, categoryLabel);
        }
        
        // Create a Scene with the VBox as its root
        Scene scene = new Scene(contentBox, 400, 300);
        
        // Set the scene to the popup stage
        popupStage.setScene(scene);
        
        // Show the popup stage
        popupStage.showAndWait();
    }

	
	// Méthode pour afficher le projet thérapeutique 
	private void displayProjetTherapeutique(BO bo) {
	    String projetTherapeutique = bo.getProjetTherapeutique();
	    
	    // Create a new Stage for the popup window
	    Stage popupStage = new Stage();
	    
	    // Set modality to APPLICATION_MODAL to block user interaction with other windows
	    popupStage.initModality(Modality.APPLICATION_MODAL);
	    
	    // Create a VBox to hold the content
	    VBox contentBox = new VBox();
	    contentBox.setAlignment(Pos.CENTER);
	    contentBox.setPadding(new Insets(20));
	    contentBox.setSpacing(10);
	    
	    // Create a Label to display the projetTherapeutique string
	    Label projetLabel = new Label(projetTherapeutique);
	    
	    // Add the Label to the VBox
	    contentBox.getChildren().add(projetLabel);
	    
	    // Create a Scene with the VBox as its root
	    Scene scene = new Scene(contentBox, 400, 200);
	    
	    // Set the scene to the popup stage
	    popupStage.setScene(scene);
	    
	    // Set the title of the popup stage
	    popupStage.setTitle("Projet Thérapeutique");
	    
	    // Show the popup stage
	    popupStage.showAndWait();
	}

}
