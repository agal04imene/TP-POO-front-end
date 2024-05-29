package application;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ModifQuestionPage {

    private Stage primaryStage;
    private Patient patient;
    private Orthophoniste orthophoniste;

    public ModifQuestionPage(Stage primaryStage, Patient patient, Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.orthophoniste = orthophoniste;
        this.patient = patient;
    }

    public void load(Scene scene) {
        primaryStage.setTitle("Modification de Question");

        // Title
        Text title = new Text("Quel type de question souhaitez-vous modifier ?");
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
            primaryStage.setTitle("Liste des QCMs");
            primaryStage.setScene(createQCMListScene(primaryStage));
        });
        qcuButton.setOnAction(e -> {
            primaryStage.setTitle("Modifier une question QCU");
            // Uncomment and implement chooseQuestion(primaryStage, QCU.class);
        });
        libreButton.setOnAction(e -> {
            primaryStage.setTitle("Modifier une question libre");
            // Uncomment and implement chooseQuestion(primaryStage, QuestionLibre.class);
        });

        // Set the scene to the primary stage
        Scene mainScene = new Scene(root, 500, 400);
        mainScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(150);
        button.setPrefHeight(60);
        return button;
    }

    private Scene chooseQuestion(Stage primaryStage, Questionnaire questionnaire) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Text title = new Text("Sélectionnez la question à modifier :");
        title.setFont(Font.font("Ubuntu", 16));

        VBox questionList = new VBox(10);
        ArrayList<QCM> questions = questionnaire.getListeQcm();
        for (QCM q : questions) {
            Button questionButton = new Button(q.getEnonce());
            questionButton.getStyleClass().add("choix-button");
            questionButton.setOnAction(e -> showModificationOptions(primaryStage, q));
            questionList.getChildren().add(questionButton);
        }

        root.getChildren().addAll(title, questionList);
        questionList.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 500, 400);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        return scene;
    }

    private void showModificationOptions(Stage primaryStage, Question question) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Text title = new Text("Modifier la question : " + question.getEnonce());
        title.setFont(Font.font("Ubuntu", 16));

        Button modifyEnonceButton = new Button("Modifier l'énoncé");
        Button modifyReponseButton = new Button("Modifier la réponse");

        modifyEnonceButton.setOnAction(e -> modifyEnonce(primaryStage, question));
        modifyReponseButton.setOnAction(e -> modifyReponse(primaryStage, question));

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        if (question instanceof QCM || question instanceof QCU) {
            Button modifyChoicesButton = new Button("Modifier les choix possibles");
            modifyChoicesButton.setOnAction(e -> modifyChoices(primaryStage, question));
            buttonBox.getChildren().addAll(modifyEnonceButton, modifyReponseButton, modifyChoicesButton);
        } else {
            buttonBox.getChildren().addAll(modifyEnonceButton, modifyReponseButton);
        }

        root.getChildren().addAll(title, buttonBox);
        Scene scene = new Scene(root, 700, 400);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    private void modifyEnonce(Stage primaryStage, Question question) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Text title = new Text("Modifier l'énoncé de la question : \n" + question.getEnonce());
        title.setFont(Font.font("Ubuntu", 16));

        TextField enonceField = new TextField(question.getEnonce());

        Button confirmButton = new Button("Confirmer");
        confirmButton.setOnAction(e -> {
            String newEnonce = enonceField.getText().trim();
            if (!newEnonce.isEmpty()) {
                question.setEnonce(newEnonce);
            }
        });

        Button cancelButton = new Button("Annuler");
        cancelButton.setOnAction(e -> primaryStage.close());

        HBox buttonBox = new HBox(20, confirmButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(title, enonceField, buttonBox);
        Scene scene = new Scene(root, 700, 400);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    private void modifyReponse(Stage primaryStage, Question question) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Text title = new Text("Modifier la réponse de la question : \n" + question.getEnonce());
        title.setFont(Font.font("Ubuntu", 16));

        TextField reponseField = new TextField();
        if (question instanceof QCM) {
            reponseField.setPromptText("Entrer les nouvelles réponses séparées par des virgules (pas d'espaces)");
        } else {
            reponseField.setPromptText("Entrer la nouvelle réponse");
            reponseField.setText((question instanceof QuestionLibre) ? ((QuestionLibre) question).getReponse() : ((QCU) question).getReponse());
        }

        Button confirmButton = new Button("Confirmer");
        confirmButton.setOnAction(e -> {
            String newReponse = reponseField.getText().trim();
            if (!newReponse.isEmpty()) {
                if (question instanceof QCM) {
                    ArrayList<String> newReponses = new ArrayList<>();
                    for (String rep : newReponse.split(",")) {
                        newReponses.add(rep.trim());
                    }
                    ((QCM) question).setReponses(newReponses);
                } else if (question instanceof QuestionLibre) {
                    ((QuestionLibre) question).setReponse(newReponse);
                } else if (question instanceof QCU) {
                    ((QCU) question).setReponse(newReponse);
                }
                primaryStage.close();
            }
        });

        Button cancelButton = new Button("Annuler");
        cancelButton.setOnAction(e -> primaryStage.close());

        HBox buttonBox = new HBox(20, confirmButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(title, reponseField, buttonBox);
        Scene scene = new Scene(root, 700, 400);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    private void modifyChoices(Stage primaryStage, Question question) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Text title = new Text("Modifier les choix possibles de la question : \n" + question.getEnonce());
        title.setFont(Font.font("Ubuntu", 16));

        VBox choicesBox = new VBox(10);
        ArrayList<TextField> choiceFields = new ArrayList<>();

        if (question instanceof QCM) {
            QCM qcm = (QCM) question;

            for (String rep : qcm.getReponsesJustes()) {
                TextField choiceField = new TextField(rep);
                choiceField.setPromptText(rep);
                choiceFields.add(choiceField);
                choicesBox.getChildren().add(choiceField);
            }

            for (String rep : qcm.getReponsesFausses()) {
                TextField choiceField = new TextField(rep);
                choiceField.setPromptText(rep);
                choiceFields.add(choiceField);
                choicesBox.getChildren().add(choiceField);
            }

            Button confirmButton = new Button("Confirmer");
            confirmButton.setOnAction(e -> {
                ArrayList<String> newReponsesJustes = new ArrayList<>();
                ArrayList<String> newReponsesFausses = new ArrayList<>();
                for (int i = 0; i < qcm.getReponsesJustes().size(); i++) {
                    TextField field = choiceFields.get(i);
                    String newChoice = field.getText().trim();
                    if (!newChoice.isEmpty()) {
                        newReponsesJustes.add(newChoice);
                    } else {
                        newReponsesJustes.add(field.getPromptText());
                    }
                }
                for (int i = qcm.getReponsesJustes().size(); i < choiceFields.size(); i++) {
                    TextField field = choiceFields.get(i);
                    String newChoice = field.getText().trim();
                    if (!newChoice.isEmpty()) {
                        newReponsesFausses.add(newChoice);
                    } else {
                        newReponsesFausses.add(field.getPromptText());
                    }
                }
                qcm.setReponsesJustes(newReponsesJustes);
                qcm.setReponsesFausses(newReponsesFausses);
                primaryStage.close();
            });

            Button cancelButton = new Button("Annuler");
            cancelButton.setOnAction(e -> primaryStage.close());

            HBox buttonBox = new HBox(20, confirmButton, cancelButton);
            buttonBox.setAlignment(Pos.CENTER);
            root.getChildren().addAll(title, choicesBox, buttonBox);
        } else if (question instanceof QCU) {
            QCU qcu = (QCU) question;

            // Correct answer field
            TextField correctAnswerField = new TextField(qcu.getReponseJuste());
            correctAnswerField.setPromptText(qcu.getReponseJuste());
            choiceFields.add(correctAnswerField);
            choicesBox.getChildren().add(correctAnswerField);

            for (String rep : qcu.getReponsesFausses()) {
                TextField choiceField = new TextField(rep);
                choiceField.setPromptText(rep);
                choiceFields.add(choiceField);
                choicesBox.getChildren().add(choiceField);
            }

            Button confirmButton = new Button("Confirmer");
            confirmButton.setOnAction(e -> {
                ArrayList<String> newChoices = new ArrayList<>();
                for (TextField field : choiceFields) {
                    String newChoice = field.getText().trim();
                    if (!newChoice.isEmpty()) {
                        newChoices.add(newChoice);
                    } else {
                        newChoices.add(field.getPromptText());
                    }
                }
                qcu.setReponseJuste(correctAnswerField.getText().trim());
                qcu.setReponsesFausses(new ArrayList<>(newChoices.subList(1, newChoices.size())));
                primaryStage.close();
            });

            Button cancelButton = new Button("Annuler");
            cancelButton.setOnAction(e -> primaryStage.close());

            HBox buttonBox = new HBox(20, confirmButton, cancelButton);
            buttonBox.setAlignment(Pos.CENTER);
            root.getChildren().addAll(title, choicesBox, buttonBox);
        }

        Scene scene = new Scene(root, 700, 400);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    private Scene createQCMListScene(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        ArrayList<Questionnaire> listeQuestionnaires = patient.getDossierPatient().getListeQuestionnaire();
        for (int i = 0; i < listeQuestionnaires.size(); i++) {
            int index = i;
            Button qcmButton = new Button("QCM " + (i + 1));
            qcmButton.setOnAction(e -> {
                primaryStage.setScene(chooseQuestion(primaryStage, listeQuestionnaires.get(index)));
                primaryStage.setTitle("Ajouter une question QCM " + (index + 1));
            });
            root.getChildren().add(qcmButton);
        }

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 500, 400);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.centerOnScreen();

        return scene;
    }
}
