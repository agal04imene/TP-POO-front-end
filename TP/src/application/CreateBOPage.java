package application;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateBOPage {
    private Stage primaryStage;
    private Orthophoniste orthophoniste;
    private Patient patient;
    private BO bo;

    public CreateBOPage(Stage primaryStage, Patient patient, Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.orthophoniste = orthophoniste;
        this.patient = patient;
    }

    public void load(Scene scene) {
        if (patient.VerifierNbBilans()) {
            afficherChoixAnamnese(scene);
        } else {
            startAjouterEpreuveClinique(scene);
        }
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    }

    private void afficherChoixAnamnese(Scene scene) {
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        Label label = new Label("Choisissez une anamnèse pour créer le bilan orthophonique :");
        label.setStyle("-fx-font-size: 18px;");

        ComboBox<Anamnese> anamneseComboBox = new ComboBox<>();
        anamneseComboBox.getItems().addAll(patient.getListeAnamneses());
        anamneseComboBox.setPromptText("Sélectionnez une anamnèse");

        Button nextButton = new Button("Suivant");
        nextButton.setOnAction(e -> {
            if (anamneseComboBox.getValue() != null) {
                bo.setAnamnese(anamneseComboBox.getValue());
                startAjouterEpreuveClinique(scene);
            } else {
                showAlert("Sélectionnez une anamnèse avant de continuer.");
            }
        });

        vbox.getChildren().addAll(label, anamneseComboBox, nextButton);

        scene.setRoot(vbox);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void startAjouterEpreuveClinique(Scene scene) {
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
                ajouterObservations(nbObs, scene);
            } catch (NumberFormatException ex) {
                showAlert("Veuillez entrer un nombre valide.");
            }
        });
    }

    // Method to add observations
    private void ajouterObservations(int nbObs, Scene scene) {
        VBox layout = new VBox(10);
        List<TextField> observationFields = new ArrayList<>();

        for (int i = 0; i < nbObs; i++) {
            TextField observationField = new TextField();
            observationField.setPromptText("Observation " + (i + 1));
            observationFields.add(observationField);
            layout.getChildren().add(observationField);
        }

        Button btnSuivant = new Button("Suivant");
        layout.getChildren().add(btnSuivant);
        scene.setRoot(layout);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        btnSuivant.setOnAction(e -> {
            EpreuveClinique epreuve = new EpreuveClinique();

            for (TextField field : observationFields) {
                epreuve.ajouterObservation(field.getText());
            }

            ajouterTest(epreuve, scene);
        });
    }

    // Method to choose and add a test (Questionnaire or TextExercices)
    private void ajouterTest(EpreuveClinique epreuve, Scene scene) {
        VBox layout = new VBox(10);
        Label instruction = new Label("Choisissez le type de test à ajouter :");

        Button btnQuestionnaire = new Button("Questionnaire");
        Button btnTextExercices = new Button("TextExercices");

        layout.getChildren().addAll(instruction, btnQuestionnaire, btnTextExercices);
        scene.setRoot(layout);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        btnQuestionnaire.setOnAction(e -> {
            choisirQuestionnaire(epreuve, scene);
        });

        btnTextExercices.setOnAction(e -> {
            choisirTextExercices(epreuve, scene);
        });
    }

    // Method to choose and add a Questionnaire
    private void choisirQuestionnaire(EpreuveClinique epreuve, Scene scene) {
        VBox layout = new VBox(10);
        Label instruction = new Label("Choisissez le questionnaire à remplir :");
        ComboBox<Questionnaire> questionnaireComboBox = new ComboBox<>();
        questionnaireComboBox.getItems().addAll(patient.getListeQuestionnaires());

        Button btnSuivant = new Button("Suivant");

        layout.getChildren().addAll(instruction, questionnaireComboBox, btnSuivant);
        scene.setRoot(layout);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        btnSuivant.setOnAction(e -> {
            Questionnaire selectedQuestionnaire = questionnaireComboBox.getValue();
            if (selectedQuestionnaire != null) {
                afficherRemplissageQuestionnaire(epreuve, selectedQuestionnaire, scene);
            } else {
                showAlert("Veuillez choisir un questionnaire.");
            }
        });
    }

    // Method to fill in a Questionnaire
    private void afficherRemplissageQuestionnaire(EpreuveClinique epreuve, Questionnaire questionnaire, Scene scene) {
        VBox layout = new VBox(10);
        Label instruction = new Label("Remplissez les réponses pour chaque question :");

        for (Question question : questionnaire.getListeQuestion()) {
            Label enonceLabel = new Label(question.getEnonce());
            TextField reponseField = new TextField();

            layout.getChildren().addAll(enonceLabel, reponseField);

            if (question instanceof QCM) {
                QCM qcm = (QCM) question;
                VBox reponsesLayout = new VBox(5);

                for (int i = 0; i < qcm.getNbPropositions(); i++) {
                    TextField propositionField = new TextField();
                    reponsesLayout.getChildren().add(propositionField);

                    propositionField.textProperty().addListener((obs, oldText, newText) -> {
                        qcm.getReponses().add(newText);
                    });
                }

                layout.getChildren().add(reponsesLayout);

            } else if (question instanceof QCU) {
                QCU qcu = (QCU) question;
                TextField reponseUniqueField = new TextField();

                reponseUniqueField.textProperty().addListener((obs, oldText, newText) -> {
                    qcu.setReponse(newText);
                });

                layout.getChildren().add(reponseUniqueField);

            } else if (question instanceof QuestionLibre) {
                QuestionLibre questionLibre = (QuestionLibre) question;
                TextField reponseLibreField = new TextField();

                reponseLibreField.textProperty().addListener((obs, oldText, newText) -> {
                    questionLibre.setReponse(newText);
                });

                layout.getChildren().add(reponseLibreField);
            }
        }

        Button btnSuivant = new Button("Suivant");
        layout.getChildren().add(btnSuivant);
        scene.setRoot(layout);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        btnSuivant.setOnAction(e -> {
            questionnaire.setScoreTotal(questionnaire.calculeScoreTotal());
            epreuve.getListeTests().add(questionnaire);
            ajouterBO(epreuve);
        });
    }

    // Method to choose and add TextExercices
    private void choisirTextExercices(EpreuveClinique epreuve, Scene scene) {
        VBox layout = new VBox(10);
        Label instruction = new Label("Choisissez la série d'exercices à remplir :");
        ComboBox<TestExercices> exercicesComboBox = new ComboBox<>();
        exercicesComboBox.getItems().addAll(patient.getListeSeriesExercices());

        Button btnSuivant = new Button("Suivant");

        layout.getChildren().addAll(instruction, exercicesComboBox, btnSuivant);
        scene.setRoot(layout);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        btnSuivant.setOnAction(e -> {
            TestExercices selectedExercices = exercicesComboBox.getValue();
            if (selectedExercices != null) {
                afficherRemplissageExercice(epreuve, selectedExercices, scene);
            } else {
                showAlert("Veuillez choisir une série d'exercices.");
            }
        });
    }

    // Method to fill in TextExercices
    private void afficherRemplissageExercice(EpreuveClinique epreuve, TestExercices exercices, Scene scene) {
        VBox layout = new VBox(10);
        Label instruction = new Label("Remplissez le score pour chaque exercice :");

        for (Exercice exercice : exercices.getExercices()) {
            Label consigneLabel = new Label("Consigne : " + exercice.getConsigne());
            Label materielLabel = new Label("Matériel : " + exercice.getNomMaterial());
            TextField scoreField = new TextField();

            scoreField.textProperty().addListener((obs, oldText, newText) -> {
                try {
                    int score = Integer.parseInt(newText);
                    exercice.setScore(0, score); // Update only the first score
                } catch (NumberFormatException ignored) {
                }
            });

            layout.getChildren().addAll(consigneLabel, materielLabel, scoreField);
        }

        Button btnSuivant = new Button("Suivant");
        layout.getChildren().add(btnSuivant);
        scene.setRoot(layout);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        btnSuivant.setOnAction(e -> {
            exercices.calculeScoreTotal();
            epreuve.getListeTests().add(exercices);
            ajouterBO(epreuve);
            afficherAjoutDiagnostic(scene);
        });
    }

    // Method to add EpreuveClinique to the BO
    private void ajouterBO(EpreuveClinique epreuve) {
    	EpreuveClinique[] nouvellesEpreuves = Arrays.copyOf(bo.getListeEpreuves(), bo.getListeEpreuves().length + 1);
    	nouvellesEpreuves[bo.getListeEpreuves().length] = epreuve;
    	bo.setListeEpreuves(nouvellesEpreuves);
    }

    
    private void afficherAjoutDiagnostic(Scene scene) {
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        Label label = new Label("Ajoutez un diagnostic :");
        label.setStyle("-fx-font-size: 18px;");

        Label nbTroublesLabel = new Label("Nombre de troubles à ajouter :");
        Spinner<Integer> nbTroublesSpinner = new Spinner<>(0, 10, 0);
        nbTroublesSpinner.setEditable(true);

        Button nextButton = new Button("Suivant");
        nextButton.setOnAction(e -> {
            int nbTroubles = nbTroublesSpinner.getValue();
            if (nbTroubles > 0) {
                afficherFormulaireTroubles(scene, nbTroubles);
            } else {
                showAlert("Veuillez entrer un nombre valide de troubles à ajouter.");
            }
        });

        vbox.getChildren().addAll(label, nbTroublesLabel, nbTroublesSpinner, nextButton);

        scene.setRoot(vbox);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void afficherFormulaireTroubles(Scene scene, int nbTroubles) {
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        Label label = new Label("Remplissez les détails des troubles :");
        label.setStyle("-fx-font-size: 18px;");

        List<HBox> troubleForms = new ArrayList<>();
        for (int i = 0; i < nbTroubles; i++) {
            HBox hbox = new HBox(10);
            hbox.setAlignment(Pos.CENTER);

            Label nomLabel = new Label("Nom du trouble " + (i + 1) + " :");
            TextField nomField = new TextField();

            Label categorieLabel = new Label("Catégorie du trouble " + (i + 1) + " :");
            ComboBox<CatTrouble> categorieComboBox = new ComboBox<>();
            categorieComboBox.getItems().addAll(CatTrouble.values());

            hbox.getChildren().addAll(nomLabel, nomField, categorieLabel, categorieComboBox);
            troubleForms.add(hbox);
        }

        Button saveButton = new Button("Enregistrer");
        saveButton.setOnAction(e -> {
            List<Trouble> troubles = new ArrayList<>();
            for (int i = 0; i < nbTroubles; i++) {
                HBox hbox = troubleForms.get(i);
                String nom = ((TextField) hbox.getChildren().get(1)).getText();
                CatTrouble categorie = ((ComboBox<CatTrouble>) hbox.getChildren().get(3)).getValue();
                if (nom != null && !nom.isEmpty() && categorie != null) {
                    troubles.add(new Trouble(nom, categorie.name()));
                } else {
                    showAlert("Veuillez remplir tous les champs pour chaque trouble.");
                    return;
                }
            }

            try {
                for (Trouble trouble : troubles) {
                    bo.getDiagnostic().ajouterTrouble(trouble);
                }
                bo.setDiagnostic(bo.getDiagnostic());
                afficherProjetTherapeutique(scene);
            } catch (TroubleDejaExistantException ex) {
                showAlert("Un trouble avec le même nom existe déjà dans le diagnostic.");
            }
        });

        vbox.getChildren().addAll(label);
        vbox.getChildren().addAll(troubleForms);
        vbox.getChildren().addAll(saveButton);

        scene.setRoot(vbox);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void afficherProjetTherapeutique(Scene scene) {
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        Label label = new Label("Ajoutez un projet thérapeutique :");
        label.setStyle("-fx-font-size: 18px;");

        TextArea projetTherapeutiqueArea = new TextArea();
        projetTherapeutiqueArea.setPromptText("Entrez le projet thérapeutique...");

        Button saveButton = new Button("Enregistrer");
        saveButton.setOnAction(e -> {
            String projetTherapeutiqueText = projetTherapeutiqueArea.getText();
            bo.setProjetTherapeutique(projetTherapeutiqueText);
            enregistrerBO();
        });

        vbox.getChildren().addAll(label, projetTherapeutiqueArea, saveButton);

        scene.setRoot(vbox);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void enregistrerBO() {
    	// n7etou le BO li khdmnah f la liste ta3 les BOs ta3 le patient
        patient.getDossierPatient().getListeBOs().add(bo);

        System.out.println("Bilan Orthophonique enregistré avec succès !");

        patient.setNbRDV(patient.getNbRDV()+1); // on incrémente nbRDV bach lmera ljaya man3awdouch ndirou anamnèse :)
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

    