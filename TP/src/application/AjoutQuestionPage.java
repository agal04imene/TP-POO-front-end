package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AjoutQuestionPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ajout de Question");

        // Title
        Text title = new Text("Quel type de question souhaitez-vous ajouter ?\n\n");
        title.setFont(Font.font("Ubuntu", 16));

        // Buttons
        Button qcmButton = createMenuButton("QCM");
        Button qcuButton = createMenuButton("QCU");
        Button libreButton = createMenuButton("Question libre");

        // Button container
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(qcmButton, qcuButton, libreButton);

        // Root layout
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(title, buttonBox);
        
        // Handlers for buttons
        qcmButton.setOnAction(e -> {
            primaryStage.setScene(createQCMScene(primaryStage));
            primaryStage.setTitle("Ajouter une question QCM");
        });
        qcuButton.setOnAction(e -> {
            primaryStage.setScene(createQCUScene(primaryStage));
            primaryStage.setTitle("Ajouter une question QCU");
        });
        libreButton.setOnAction(e -> {
            primaryStage.setScene(createLibreScene(primaryStage));
            primaryStage.setTitle("Ajouter une question libre");
        });
        

        // Scene setup
        Scene scene = new Scene(root, 500, 200);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.show();
        primaryStage.centerOnScreen();
    }
    
    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(150);
        button.setPrefHeight(60);
        return button;
    }
    
    private Scene createQCMScene(Stage primaryStage) {
        // Create scene for adding QCM question
        Label questionLabel = new Label("Énoncé de la question :");
        questionLabel.getStyleClass().add("label-style");
        TextField questionField = new TextField();
        Label nbPropositionsLabel = new Label("Nombre de propositions :");
        nbPropositionsLabel.getStyleClass().add("label-style");
        TextField nbPropositionsField = new TextField();

        VBox propositionsBox = new VBox(10);
        propositionsBox.setAlignment(Pos.CENTER_LEFT);

        nbPropositionsField.setOnAction(event -> {
            propositionsBox.getChildren().clear();
            int nbPropositions = Integer.parseInt(nbPropositionsField.getText());
            for (int i = 1; i <= nbPropositions; i++) {
                TextField propositionField = new TextField();
                CheckBox justeBox = new CheckBox("Réponse juste");
                CheckBox choisieBox = new CheckBox("Réponse choisie");
                Label label = new Label("Proposition " + i + ":");
                label.getStyleClass().add("label-style");
                propositionsBox.getChildren().add(label);
                propositionsBox.getChildren().add(propositionField);
                propositionsBox.getChildren().addAll(justeBox, choisieBox);
            }
        });

        Button addButton = new Button("Ajouter");
        Button cancelButton = new Button("Annuler");
        HBox buttonsBox = new HBox(20);
        buttonsBox.getChildren().addAll(addButton, cancelButton);
        buttonsBox.setAlignment(Pos.CENTER);
        
        addButton.setOnAction(event -> primaryStage.setScene(new Scene(new VBox(new Label("Question QCM ajoutée")), 400, 300)));
        cancelButton.setOnAction(event -> primaryStage.close());

        VBox qcmLayout = new VBox(10);
        qcmLayout.setAlignment(Pos.CENTER);
        qcmLayout.setPadding(new Insets(20));
        qcmLayout.getChildren().addAll(questionLabel, questionField, nbPropositionsLabel, nbPropositionsField, propositionsBox, buttonsBox);

        ScrollPane scrollPane = new ScrollPane(qcmLayout);
        scrollPane.setFitToWidth(true);
        
        Scene scene = new Scene(scrollPane, 500, 400);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.centerOnScreen();
        
        return scene ;
    }

    private Scene createQCUScene(Stage primaryStage) {
        // Create scene for adding QCU question
        Label questionLabel = new Label("Énoncé de la question :");
        questionLabel.getStyleClass().add("label-style");
        TextField questionField = new TextField();
        Label nbPropositionsLabel = new Label("Nombre de propositions :");
        nbPropositionsLabel.getStyleClass().add("label-style");
        TextField nbPropositionsField = new TextField();

        VBox propositionsBox = new VBox(10);
        propositionsBox.setAlignment(Pos.CENTER_LEFT);
        
        ToggleGroup toggleGroup = new ToggleGroup();

        nbPropositionsField.setOnAction(event -> {
            propositionsBox.getChildren().clear();
            int nbPropositions = Integer.parseInt(nbPropositionsField.getText());
            for (int i = 1; i <= nbPropositions; i++) {
                TextField propositionField = new TextField();
                CheckBox justeBox = new CheckBox("Réponse juste");
                RadioButton choisieButton = new RadioButton("Réponse choisie");
                choisieButton.setToggleGroup(toggleGroup);
                Label label = new Label("Proposition " + i + ":");
                label.getStyleClass().add("label-style");
                propositionsBox.getChildren().add(label);
                propositionsBox.getChildren().add(propositionField);
                propositionsBox.getChildren().addAll(justeBox, choisieButton);
            }
        });

        Button addButton = new Button("Confirmer");
        Button cancelButton = new Button("Annuler");
        
        HBox buttonsBox = new HBox(20);
        buttonsBox.getChildren().addAll(addButton, cancelButton);
        buttonsBox.setAlignment(Pos.CENTER);
        
        addButton.setOnAction(event -> primaryStage.setScene(new Scene(new VBox(new Label("Question QCU ajoutée")), 400, 300)));
        cancelButton.setOnAction(event -> primaryStage.close());

        VBox qcuLayout = new VBox(10);
        qcuLayout.setAlignment(Pos.CENTER);
        qcuLayout.setPadding(new Insets(20));
        qcuLayout.getChildren().addAll(questionLabel, questionField, nbPropositionsLabel, nbPropositionsField, propositionsBox,buttonsBox);

        ScrollPane scrollPane = new ScrollPane(qcuLayout);
        scrollPane.setFitToWidth(true);
        
        Scene scene = new Scene(scrollPane, 500, 400);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.centerOnScreen();
        
        return scene;
    }

    private Scene createLibreScene(Stage primaryStage) {
        // Create scene for adding libre question
        Label questionLabel = new Label("Énoncé de la question :");
        questionLabel.getStyleClass().add("label-style");
        TextField questionField = new TextField();
        Label reponseLabel = new Label("Réponse du patient :");
        reponseLabel.getStyleClass().add("label-style");
        TextField reponseField = new TextField();

        Button addButton = new Button("Confirmer");
        Button cancelButton = new Button("Annuler");
        
        HBox buttonsBox = new HBox(20);
        buttonsBox.getChildren().addAll(addButton, cancelButton);
        buttonsBox.setAlignment(Pos.CENTER);
        
        addButton.setOnAction(event -> primaryStage.setScene(new Scene(new VBox(new Label("Question libre ajoutée")), 400, 300)));
        cancelButton.setOnAction(event -> primaryStage.close());
        
        VBox libreLayout = new VBox(10);
        libreLayout.setAlignment(Pos.CENTER);
        libreLayout.setPadding(new Insets(20));
        libreLayout.getChildren().addAll(questionLabel, questionField, reponseLabel, reponseField, buttonsBox);
        
        ScrollPane scrollPane = new ScrollPane(libreLayout);
        scrollPane.setFitToWidth(true);
        
        Scene scene = new Scene(scrollPane, 500, 300);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.centerOnScreen();
        
        return scene;
    }

}
