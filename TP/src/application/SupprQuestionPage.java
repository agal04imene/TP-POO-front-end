package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SupprQuestionPage extends Application {

    private ArrayList<Question> questions; // this array is filled with the questions from all "les questionnaires" of a patient

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Suppression de Question");
        
        // ---------------------------------------------------------------------------------------------------------------------------- //
        // !!!! THIS IS JUST A STUPID LITTLE EXAMPLE FOR TESTING PURPOSES !!!!
        questions = new ArrayList<>();

        // Adding a QCM question
        QCM qcm = new QCM("Quelle est la capitale de la France ?");
        qcm.ajoutRepJuste("Paris");
        qcm.ajoutRepFausse("Lyon");
        qcm.ajoutRepFausse("Marseille");
        qcm.ajoutRepFausse("Nice");
        questions.add(qcm);

        // Adding a QCU question
        QCU qcu = new QCU("Quelle est la couleur du ciel par temps clair ?");
        qcu.ajoutRepJuste("Bleu");
        qcu.ajoutRepFausse("Rouge");
        qcu.ajoutRepFausse("Vert");
        qcu.ajoutRepFausse("Jaune");
        questions.add(qcu);

        // Adding a QuestionLibre question
        QuestionLibre libre = new QuestionLibre("Décrivez la Révolution française en quelques phrases.");
        libre.setReponse("La Révolution française est une période de grands changements sociaux et politiques en France de 1789 à 1799.");
        questions.add(libre);
        
        // ---------------------------------------------------------------------------------------------------------------------------- //

        // Title
        Label title = new Label("Quel type de question souhaitez-vous supprimer ?");
        title.setStyle("-fx-font-size: 16px; -fx-font-family: 'Ubuntu';");

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
        qcmButton.setOnAction(e -> primaryStage.setScene(createQuestionTypeScene(primaryStage, "QCM")));
        qcuButton.setOnAction(e -> primaryStage.setScene(createQuestionTypeScene(primaryStage, "QCU")));
        libreButton.setOnAction(e -> primaryStage.setScene(createQuestionTypeScene(primaryStage, "Question Libre")));

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

    private Scene createQuestionTypeScene(Stage primaryStage, String questionType) {
        Label title = new Label("Choisissez une méthode de suppression pour " + questionType + " :");
        title.setStyle("-fx-font-size: 16px; -fx-font-family: 'Ubuntu';");

        Button showAllButton = new Button("Afficher toutes les questions");
        Button enterEnonceButton = new Button("Entrer l'énoncé de la question à supprimer");

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(title, showAllButton, enterEnonceButton);

        showAllButton.setOnAction(e -> primaryStage.setScene(createShowAllQuestionsScene(primaryStage, questionType)));
        enterEnonceButton.setOnAction(e -> primaryStage.setScene(createEnterEnonceScene(primaryStage, questionType)));

        Scene scene = new Scene(root, 500, 200);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        return scene;
    }

    private Scene createShowAllQuestionsScene(Stage primaryStage, String questionType) {
        Label title = new Label("Sélectionnez la question à supprimer :");
        title.setStyle("-fx-font-size: 16px; -fx-font-family: 'Ubuntu';");

        VBox questionsBox = new VBox(10);
        questionsBox.setAlignment(Pos.CENTER_LEFT);
        questionsBox.setPadding(new Insets(10));

        for (Question question : questions) {
            if (isQuestionType(question, questionType)) {
                Button questionButton = new Button(question.getEnonce());
                questionButton.getStyleClass().add("choix-button");
                questionButton.setOnAction(e -> {
                    questions.remove(question);
                    primaryStage.setScene(createConfirmationScene(primaryStage, "Question supprimée."));
                });
                questionsBox.getChildren().add(questionButton);
            }
        }

        ScrollPane scrollPane = new ScrollPane(questionsBox);
        scrollPane.setFitToWidth(true);

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(title, scrollPane);

        Scene scene = new Scene(root, 500, 400);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        return scene;
    }

    private Scene createEnterEnonceScene(Stage primaryStage, String questionType) {
        Label title = new Label("Entrez l'énoncé de la question à supprimer :");
        title.setStyle("-fx-font-size: 16px; -fx-font-family: 'Ubuntu';");

        TextField enonceField = new TextField();
        Button deleteButton = new Button("Supprimer");
        Button annulerButton = new Button("Annuler");
        HBox buttonsBox = new HBox(20);
        buttonsBox.getChildren().addAll(deleteButton, annulerButton);
        buttonsBox.setAlignment(Pos.CENTER);

        deleteButton.setOnAction(e -> {
            String enonce = enonceField.getText();
            for (Question question : questions) {
                if (isQuestionType(question, questionType) && question.getEnonce().equals(enonce)) {
                    questions.remove(question);
                    primaryStage.setScene(createConfirmationScene(primaryStage, "Question supprimée."));
                    break;
                }
            }
        });
        
        annulerButton.setOnAction(event -> primaryStage.close());

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(title, enonceField, buttonsBox);

        Scene scene = new Scene(root, 500, 200);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        return scene;
    }

    private Scene createConfirmationScene(Stage primaryStage, String message) {
        Label confirmationLabel = new Label(message);
        confirmationLabel.setStyle("-fx-font-size: 16px; -fx-font-family: 'Ubuntu';");

        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> start(primaryStage));

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(confirmationLabel, backButton);

        Scene scene = new Scene(root, 500, 200);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        return scene;
    }

    private boolean isQuestionType(Question question, String questionType) {
        switch (questionType) {
            case "QCM":
                return question instanceof QCM;
            case "QCU":
                return question instanceof QCU;
            case "Question Libre":
                return question instanceof QuestionLibre;
            default:
                return false;
        }
    }

}
