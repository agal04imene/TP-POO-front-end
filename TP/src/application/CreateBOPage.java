package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CreateBOPage {
    private Stage primaryStage;
    private Orthophoniste orthophoniste;
    private Patient patient;

    public CreateBOPage(Stage primaryStage, Patient patient, Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.orthophoniste = orthophoniste;
        this.patient = patient;
    }

    public void load(Scene scene) {
        if (patient.VerifierNbBilans()) {
            afficherChoixAnamnese(scene);
        } else {
            afficherAjoutEpreuveClinique(scene);
        }
    }
    
    

    private void afficherChoixAnamnese(Scene scene) {
        VBox layout = new VBox(10);
        Label instruction = new Label("Saisir le numéro du modèle d'anamnèse à remplir :");
        TextField choixModele = new TextField();
        HBox buttonsBox = new HBox(20);
        Button btnSuivant = new Button("Suivant");
        Button btnAnnuler = new Button("Annuler");
        buttonsBox.getChildren().addAll(btnAnnuler, btnSuivant);
        buttonsBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(instruction, choixModele, buttonsBox);
        scene.setRoot(layout);

        btnSuivant.setOnAction(e -> {
            try {
                int index = Integer.parseInt(choixModele.getText());
                if (index >= 0 && index < patient.getListeAnamneses().size()) {
                    Anamnese modeleChoisi = patient.getListeAnamneses().get(index);
                    afficherRemplissageAnamnese(scene, modeleChoisi);
                } else {
                    showAlert("Index invalide", "Veuillez entrer un numéro de modèle valide.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Format invalide", "Veuillez entrer un numéro valide.");
            }
        });
        
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        btnAnnuler.setOnAction(e -> {
            load(scene);
        });
    }

    private void afficherRemplissageAnamnese(Scene scene, Anamnese anamnese) {
        VBox layout = new VBox(10);
        Label instruction = new Label("Remplissez les réponses pour chaque question :");
        ScrollPane scrollPane = new ScrollPane();
        VBox questionLayout = new VBox(10);

        for (QstLibreAnamnese question : anamnese.getQuestions()) {
            Label enonceLabel = new Label(question.getEnonce());
            TextField reponseField = new TextField();
            questionLayout.getChildren().addAll(enonceLabel, reponseField);

            reponseField.textProperty().addListener((obs, oldText, newText) -> {
                question.setReponse(newText);
            });
        }

        scrollPane.setContent(questionLayout);
        HBox buttonsBox = new HBox(20);
        Button btnSuivant = new Button("Suivant");
        Button btnAnnuler = new Button("Annuler");
        buttonsBox.getChildren().addAll(btnAnnuler, btnSuivant);
        buttonsBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(instruction, scrollPane, buttonsBox);
        scene.setRoot(layout);

        btnSuivant.setOnAction(e -> {
            BO bo = patient.getDossierPatient().getListeBOs().get(0);
            bo.setAnamnese(anamnese);
            afficherAjoutEpreuveClinique(scene);
        });

        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
        btnAnnuler.setOnAction(e -> {
            for (QstLibreAnamnese question : anamnese.getQuestions()) {
                question.setReponse("");
            }
            afficherChoixAnamnese(scene);
        });
    }

    private void afficherAjoutEpreuveClinique(Scene scene) {
        VBox layout = new VBox(10);
        Label instruction = new Label("Ajouter une épreuve clinique:");
        TextField nombreObservations = new TextField();
        nombreObservations.setPromptText("Nombre d'observations");
        Button btnSuivant = new Button("Suivant");

        layout.getChildren().addAll(instruction, nombreObservations, btnSuivant);
        scene.setRoot(layout);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        btnSuivant.setOnAction(e -> {
            try {
                int nbObs = Integer.parseInt(nombreObservations.getText());
                afficherAjoutObservations(scene, nbObs);
            } catch (NumberFormatException ex) {
                showAlert("Format invalide", "Veuillez entrer un nombre valide.");
            }
        });
    }

    private void afficherAjoutObservations(Scene scene, int nbObs) {
        VBox layout = new VBox(10);
        Label instruction = new Label("Entrez les observations cliniques :");
        ScrollPane scrollPane = new ScrollPane();
        VBox observationLayout = new VBox(10);

        TextField[] observationFields = new TextField[nbObs];
        for (int i = 0; i < nbObs; i++) {
            TextField observationField = new TextField();
            observationField.setPromptText("Observation " + (i + 1));
            observationLayout.getChildren().add(observationField);
            observationFields[i] = observationField;
        }

        scrollPane.setContent(observationLayout);
        HBox buttonsBox = new HBox(20);
        Button btnSuivant = new Button("Suivant");
        Button btnAnnuler = new Button("Annuler");
        buttonsBox.getChildren().addAll(btnAnnuler, btnSuivant);
        buttonsBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(instruction, scrollPane, buttonsBox);
        scene.setRoot(layout);

        btnSuivant.setOnAction(e -> {
            EpreuveClinique epreuve = new EpreuveClinique();
            for (TextField field : observationFields) {
                epreuve.ajouterObservation(field.getText());
            }
            afficherAjoutTests(scene, epreuve);
        });

        btnAnnuler.setOnAction(e -> {
            afficherAjoutEpreuveClinique(scene);
        });
        
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    }

    private void afficherAjoutTests(Scene scene, EpreuveClinique epreuve) {
        VBox layout = new VBox(10);
        Label instruction = new Label("Voulez-vous ajouter un questionnaire ou une série d'exercices ?");
        HBox buttonsBox = new HBox(20);
        Button btnQuestionnaire = new Button("Questionnaire");
        Button btnSerieExercices = new Button("Série d'exercices");
        buttonsBox.getChildren().addAll(btnQuestionnaire, btnSerieExercices);
        buttonsBox.setAlignment(Pos.CENTER);
        

        layout.getChildren().addAll(instruction, buttonsBox);
        scene.setRoot(layout);

        btnQuestionnaire.setOnAction(e -> {
            afficherChoixQuestionnaire(scene, epreuve);
        });

        btnSerieExercices.setOnAction(e -> {
            afficherAjoutExercices(scene, epreuve);
        });
        
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    }

    private void afficherChoixQuestionnaire(Scene scene, EpreuveClinique epreuve) {
        VBox layout = new VBox(10);
        Label instruction = new Label("Choisissez le questionnaire à remplir :");
        TextField choixQuestionnaire = new TextField();
        Button btnSuivant = new Button("Suivant");

        layout.getChildren().addAll(instruction, choixQuestionnaire, btnSuivant);
        scene.setRoot(layout);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        btnSuivant.setOnAction(e -> {
            try {
                int index = Integer.parseInt(choixQuestionnaire.getText());
                if (index >= 0 && index < patient.getListeQuestionnaires().size()) {
                    Questionnaire questionnaireChoisi = patient.getListeQuestionnaires().get(index);
                    afficherRemplissageQuestionnaire(scene, epreuve, questionnaireChoisi);
                } else {
                    showAlert("Index invalide", "Veuillez entrer un numéro de questionnaire valide.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Format invalide", "Veuillez entrer un numéro valide.");
            }
        });
    }

    private void afficherRemplissageQuestionnaire(Scene scene, EpreuveClinique epreuve, Questionnaire questionnaire) {
        // This one is for filling the answers to a questionnaire
    }

    private void afficherAjoutExercices(Scene scene, EpreuveClinique epreuve) {
    	// This one is for filling une série d'exercices
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
