package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GestionRendezVous {
    private Stage primaryStage;
    private Orthophoniste orthophoniste;

    public GestionRendezVous(Stage primaryStage, Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.orthophoniste = orthophoniste;
    }

    public void load(Scene previousScene) {
        BorderPane root = new BorderPane();
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Button rendezVousConsultationButton = createButton("Rendez-vous consultation");
        rendezVousConsultationButton.setOnAction(e -> showRendezVousConsultationPopup());

        Button rendezVousSuiviButton = createButton("Rendez-vous de suivi");
        rendezVousSuiviButton.setOnAction(e -> showRendezVousSuiviPopup());

        Button rendezVousAtelierButton = createButton("Rendez-vous atelier du groupe");
        rendezVousAtelierButton.setOnAction(e -> showRendezVousAtelierPopup());

        Button backButton = new Button("Retour");
        backButton.getStyleClass().add("button-style");
        backButton.setOnAction(e -> {
            MenuPrincipal menuPage = new MenuPrincipal(primaryStage, orthophoniste);
            menuPage.load(previousScene);
        });

        vbox.getChildren().addAll(
                rendezVousConsultationButton,
                rendezVousSuiviButton,
                rendezVousAtelierButton
        );

        root.setCenter(vbox);
        root.setBottom(backButton);
        BorderPane.setAlignment(backButton, Pos.CENTER);
        BorderPane.setMargin(backButton, new Insets(10));

        Scene scene = new Scene(root, 500, 400);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("menu-button");
        button.setPrefWidth(300);
        button.setPrefHeight(60);
        return button;
    }

    private void showRendezVousConsultationPopup() {
        Stage popupStage = new Stage();
        popupStage.setTitle("Rendez-vous Consultation");

        TextField dateField = new TextField();
        dateField.setPromptText("Date du rendez-vous");
        TextField heureField = new TextField();
        heureField.setPromptText("Heure du rendez-vous");
        TextField nomField = new TextField();
        nomField.setPromptText("Nom du patient");
        TextField prenomField = new TextField();
        prenomField.setPromptText("Prénom du patient");
        TextField ageField = new TextField();
        ageField.setPromptText("Age du patient");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button saveButton = new Button("Enregistrer");
        saveButton.setOnAction(event -> {
            String date = dateField.getText();
            String heure = heureField.getText();
            String nomPatient = nomField.getText();
            String prenomPatient = prenomField.getText();
            String ageText = ageField.getText();

            if (date.isEmpty() || heure.isEmpty() || nomPatient.isEmpty() || prenomPatient.isEmpty() || ageText.isEmpty()) {
                errorLabel.setText("Tous les champs sont obligatoires.");
            } else {
                try {
                    int agePatient = Integer.parseInt(ageText);
                    Consultation consultation = new Consultation(date, heure, agePatient);
                    orthophoniste.ajouterConsultation(consultation);
                    popupStage.close();
                } catch (NumberFormatException e) {
                    errorLabel.setText("L'âge doit être un nombre valide.");
                }
            }
        });

        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(dateField, heureField, nomField, prenomField, ageField, saveButton, errorLabel);

        Scene popupScene = new Scene(popupRoot, 300, 300);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    private void showRendezVousSuiviPopup() {
        Stage popupStage = new Stage();
        popupStage.setTitle("Rendez-vous de suivi");

        TextField dateField = new TextField();
        dateField.setPromptText("Date du rendez-vous");
        TextField heureField = new TextField();
        heureField.setPromptText("Heure du rendez-vous");
        TextField etatField = new TextField();
        etatField.setPromptText("Présentiel ou en ligne ?");
        TextField nmrPatientField = new TextField();
        nmrPatientField.setPromptText("Numéro de dossier du patient");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button saveButton = new Button("Enregistrer");
        saveButton.setOnAction(event -> {
            String date = dateField.getText();
            String heure = heureField.getText();
            String etatStr = etatField.getText();
            String numDossierText = nmrPatientField.getText();

            if (date.isEmpty() || heure.isEmpty() || etatStr.isEmpty() || numDossierText.isEmpty()) {
                errorLabel.setText("Tous les champs sont obligatoires.");
            } else {
                try {
                    int numDossierPatient = Integer.parseInt(numDossierText);
                    Etat etat = Etat.valueOf(etatStr.toUpperCase());
                    SeanceDeSuivi seanceDeSuivi = new SeanceDeSuivi(date, heure, numDossierPatient, etat);
                    orthophoniste.ajouterSeanceDeSuivi(seanceDeSuivi);
                    popupStage.close();
                } catch (NumberFormatException e) {
                    errorLabel.setText("Le numéro de dossier doit être un nombre valide.");
                } catch (IllegalArgumentException e) {
                    errorLabel.setText("L'état doit être 'PRESENTIEL' ou 'ENLIGNE'.");
                }
            }
        });

        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(dateField, heureField, etatField, nmrPatientField, saveButton, errorLabel);

        Scene popupScene = new Scene(popupRoot, 300, 300);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    private void showRendezVousAtelierPopup() {
        Stage popupStage = new Stage();
        popupStage.setTitle("Rendez-vous atelier du groupe");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        TextField dateField = new TextField();
        dateField.setPromptText("Date du rendez-vous");
        TextField heureField = new TextField();
        heureField.setPromptText("Heure du rendez-vous");
        TextField thematiqueField = new TextField();
        thematiqueField.setPromptText("Thématique");
        TextField numPatientsField = new TextField();
        numPatientsField.setPromptText("Nombre de patients");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button generateFieldsButton = new Button("Continuer");
        VBox patientFieldsBox = new VBox(10);
        patientFieldsBox.setAlignment(Pos.CENTER_LEFT);

        generateFieldsButton.setOnAction(e -> {
            patientFieldsBox.getChildren().clear();
            try {
                int numPatients = Integer.parseInt(numPatientsField.getText());
                for (int i = 1; i <= numPatients; i++) {
                    TextField patientField = new TextField();
                    patientField.setPromptText("Le numéro du dossier du patient " + i);
                    patientFieldsBox.getChildren().add(patientField);
                }
            } catch (NumberFormatException ex) {
                patientFieldsBox.getChildren().clear();
                errorLabel.setText("Veuillez entrer un nombre valide de patients.");
            }
        });

        Button admitButton = new Button("Enregistrer");
        admitButton.setOnAction(event -> {
            try {
                int numPatients = Integer.parseInt(numPatientsField.getText());
                int[] tabNumPatients = new int[numPatients];
                for (int i = 0; i < numPatients; i++) {
                    TextField patientField = (TextField) patientFieldsBox.getChildren().get(i);
                    tabNumPatients[i] = Integer.parseInt(patientField.getText());
                }
                String date = dateField.getText();
                String heure = heureField.getText();
                String thematique = thematiqueField.getText();
                AtelierDeGroupe atelierDeGroupe = new AtelierDeGroupe(date, heure, thematique, tabNumPatients);
                orthophoniste.ajouterAtelierDeGroupe(atelierDeGroupe);
                popupStage.close();
            } catch (NumberFormatException ex) {
                errorLabel.setText("Veuillez entrer des numéros de dossier valides.");
            }
        });

        vbox.getChildren().addAll(dateField, heureField, thematiqueField, numPatientsField, generateFieldsButton, patientFieldsBox, admitButton, errorLabel);

        Scene popupScene = new Scene(vbox, 400, 400);
        popupStage.setScene(popupScene);
        popupStage.show();
    }
}
