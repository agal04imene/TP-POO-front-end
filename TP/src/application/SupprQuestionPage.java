package application;

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

public class SupprQuestionPage {
    private Patient patient;
    private Stage primaryStage;
    private ArrayList<Question> questions; // This array is filled with the questions from all "les questionnaires" of a patient

    public SupprQuestionPage(Stage primaryStage, Patient patient) {
        this.primaryStage = primaryStage;
        this.patient = patient;
        this.questions = new ArrayList<>();

     
    }

 

    public void load() {
        primaryStage.setTitle("Suppression de Question");

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
        qcmButton.setOnAction(e -> primaryStage.setScene(createQuestionTypeScene("QCM")));
        qcuButton.setOnAction(e -> primaryStage.setScene(createQuestionTypeScene("QCU")));
        libreButton.setOnAction(e -> primaryStage.setScene(createQuestionTypeScene("Question Libre")));

        // Scene setup
        Scene scene = new Scene(root, 500, 200);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(150);
        button.setPrefHeight(60);
        return button;
    }

    private Scene createQuestionTypeScene(String questionType) {
        Label title = new Label("Choisissez une méthode de suppression pour " + questionType + " :");
        title.setStyle("-fx-font-size: 16px; -fx-font-family: 'Ubuntu';");

        Button showAllButton = new Button("Afficher toutes les questions");
        Button enterEnonceButton = new Button("Entrer l'énoncé de la question à supprimer");

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(title, showAllButton, enterEnonceButton);

        showAllButton.setOnAction(e -> primaryStage.setScene(createShowAllQuestionsScene(questionType)));
        enterEnonceButton.setOnAction(e -> primaryStage.setScene(createEnterEnonceScene(questionType)));

        Scene scene = new Scene(root, 500, 200);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        return scene;
    }

    private Scene createShowAllQuestionsScene(String questionType) {
        Label title = new Label("Sélectionnez la question à supprimer :");
        
    	
        title.setStyle("-fx-font-size: 16px; -fx-font-family: 'Ubuntu';");

        VBox questionsBox = new VBox(10);
        questionsBox.setAlignment(Pos.CENTER_LEFT);
        questionsBox.setPadding(new Insets(10));
        EpreuveClinique[] listeEpreuves = patient.getDossierPatient().getListeBOs().get( 0).getListeEpreuves();
    	EpreuveClinique epreuveClinique = listeEpreuves[0];  // la derniere epreue clinique
    	
    	if (questionType.equalsIgnoreCase("qcm")){
    		Questionnaire  questionnaire ;
    		ArrayList<QCM> questions;
    		questionnaire = (Questionnaire)epreuveClinique.getListeTests().get(0);
    		questions = questionnaire.getListeQcm();
    	} else if  (questionType.equalsIgnoreCase("qcm")) {
    		Questionnaire  questionnaire ;
    		ArrayList<QCM> questions;
    		questionnaire = (Questionnaire)epreuveClinique.getListeTests().get(0);
    		questions = questionnaire.getListeQcm();
    	}else {
    		Questionnaire  questionnaire ;
    		ArrayList<QuestionLibre> questions;
    		questionnaire = (Questionnaire)epreuveClinique.getListeTests().get(0);
    		questions = questionnaire.getListeQstLibre();
    	}
    	
        for (Question question : questions) {
            if (isQuestionType(question, questionType)) {
                Button questionButton = new Button(question.getEnonce());
                questionButton.getStyleClass().add("choix-button");
                questionButton.setOnAction(e -> {
                    boolean supp =questions.remove(question);
                    
                    if (supp) primaryStage.setScene(createConfirmationScene("Question supprimée."));
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

    private Scene createEnterEnonceScene(String questionType) {
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
            boolean questionFound = false;
            for (Question question : questions) {
                if (isQuestionType(question, questionType) && question.getEnonce().equals(enonce)) {
                    questions.remove(question);
                    primaryStage.setScene(createConfirmationScene("Question supprimée."));
                    questionFound = true;
                    break;
                }
            }
            if (!questionFound) {
                primaryStage.setScene(createConfirmationScene("Question non trouvée."));
            }
        });

        annulerButton.setOnAction(e -> primaryStage.setScene(new Scene(new VBox(new Label("Action annulée")), 400, 300)));

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(title, enonceField, buttonsBox);

        Scene scene = new Scene(root, 500, 200);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        return scene;
    }

    private Scene createConfirmationScene(String message) {
        Label confirmationLabel = new Label(message);
        confirmationLabel.setStyle("-fx-font-size: 16px; -fx-font-family: 'Ubuntu';");

        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> load());

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
