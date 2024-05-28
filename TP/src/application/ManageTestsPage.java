package application;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ManageTestsPage {
    private Stage primaryStage;
    private Patient patient;

    public ManageTestsPage(Stage primaryStage, Patient patient) {
        this.primaryStage = primaryStage;
        this.patient = patient;
    }

    public void load(Scene scene) {
        primaryStage.setTitle("Gestion des Tests");

        // Main menu options
        Label gestionQuestionnaireLabel = createLabel("Gestion des Questionnaires");
        Label gestionExercicesLabel = createLabel("Gestion des Séries d'Exercices");
        Label gestionAnamnesesLabel = createLabel("Gestion des Anamnèses");

        // Sub-menus
        VBox questionnaireOptions = createSubMenu(
                createLabel("Création d'un nouveau questionnaire", event -> showQuestionnaireCreationDialog()),
                createLabel("Ajouter une question à un questionnaire", event -> goToAjoutQuestionPage()),
                createLabel("Supprimer une question d'un questionnaire", event -> goToSupprQuestionPage()),
                createLabel("Modifier une question dans un questionnaire", event -> goToModifQuestionPage())
        );

        VBox exercicesOptions = createSubMenu(
                createLabel("Ajouter un exercice à une série d'exercices"),
                createLabel("Supprimer un exercice d'une série d'exercices"),
                createLabel("Modifier un exercice dans une série d'exercices")
        );

        VBox anamnesesOptions = createSubMenu(
                createLabel("Créer une anamnèse et la sauvegarder dans un bilan orthophonique", event -> showCreateAnamnesePopup())
        );

        VBox modifyAnamneseOptions = createSubMenu(
                createLabel("Ajouter une nouvelle question à l'anamnèse d'un patient", event -> handleAddAnamneseQuestion()),
                createLabel("Modifier une question dans une anamnèse", event -> handleModifAnamneseQuestion())
        );

        TitledPane modifyAnamnesePane = new TitledPane("Modifier l'anamnèse d'un patient", modifyAnamneseOptions);
        modifyAnamnesePane.getStyleClass().add("titled-pane");
        anamnesesOptions.getChildren().add(modifyAnamnesePane);

        // TitledPanes
        TitledPane gestionQuestionnaireTitledPane = new TitledPane("Gestion des Questionnaires", questionnaireOptions);
        TitledPane gestionExercicesTitledPane = new TitledPane("Gestion des Séries d'Exercices", exercicesOptions);
        TitledPane gestionAnamnesesTitledPane = new TitledPane("Gestion des Anamnèses", anamnesesOptions);

        // Accordion
        Accordion accordion = new Accordion();
        accordion.getPanes().addAll(gestionQuestionnaireTitledPane, gestionExercicesTitledPane, gestionAnamnesesTitledPane);

        StackPane root = new StackPane(accordion);
        scene = new Scene(root, 600, 600);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("options-label");
        return label;
    }
    
    private Label createLabel(String text, EventHandler<MouseEvent> eventHandler) {
        Label label = createLabel(text);
        label.setOnMouseClicked(eventHandler);
        return label;
    }

    private VBox createSubMenu(Label... options) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getStyleClass().add("options-box");
        for (Label option : options) {
            vbox.getChildren().add(option);
        }
        return vbox;
    }

    private void goToAjoutQuestionPage() {
        AjoutQuestionPage ajoutQuestionPage = new AjoutQuestionPage(primaryStage, patient);
        ajoutQuestionPage.load(new Scene(new StackPane(), 600, 600));  // Adjust the scene as needed
    }

    private void goToSupprQuestionPage() {
        SupprQuestionPage supprQuestionPage = new SupprQuestionPage(primaryStage, patient);
        supprQuestionPage.load();  // Adjust the scene as needed
    }
    
    private void goToModifQuestionPage() {
        ModifQuestionPage modifQuestionPage = new ModifQuestionPage();
        modifQuestionPage.start(primaryStage);  // Adjust the scene as needed
    }

    private void handleAddAnamneseQuestion() {
        int age = patient.getDossierPatient().getAge();
        if (age >= 18) {
            showAddAnamneseQuestionPopup("Ajouter une nouvelle question à l'anamnèse", 
                new String[]{"Histoire de la Maladie", "Suivi Médical"});
        } else {
            showAddAnamneseQuestionPopup("Ajouter une nouvelle question à l'anamnèse", 
                new String[]{"Structure Familiale", "Dynamique Familiale", "Antecedents Familiaux", "Conditions Natales", 
                             "Developpement Psychomoteur", "Developpement Langagier", "Caractere Comportement"});
        }
    }

    private void handleModifAnamneseQuestion() {
        int age = patient.getDossierPatient().getAge();
        if (age >= 18) {
            showModifyAnamneseQuestionPopup("Modifier une question dans l'anamnèse", 
                new String[]{"Histoire de la Maladie", "Suivi Médical"});
        } else {
            showModifyAnamneseQuestionPopup("Modifier une question dans l'anamnèse", 
                new String[]{"Structure Familiale", "Dynamique Familiale", "Antecedents Familiaux", "Conditions Natales", 
                             "Developpement Psychomoteur", "Developpement Langagier", "Caractere Comportement"});
        }
    }

    private void showAddAnamneseQuestionPopup(String title, String[] categories) {
        Stage popup = new Stage();
        VBox popupRoot = new VBox(10);
        popupRoot.setPadding(new Insets(20));
        popupRoot.setAlignment(Pos.CENTER);

        TextField enonceField = new TextField();
        enonceField.setPromptText("Énoncé");

        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(categories);
        categoryComboBox.setPromptText("Catégorie");

        Button confirmButton = new Button("Confirmer");
        Button cancelButton = new Button("Annuler");

        confirmButton.setOnAction(e -> {
            String enonce = enonceField.getText();
            String categorie = categoryComboBox.getValue();
            if (enonce != null && !enonce.isEmpty() && categorie != null) {
                // Logic to add the question to the anamnese !!!
                popup.close();
            } else {
                // Show error message
            }
        });

        cancelButton.setOnAction(e -> popup.close());

        HBox buttonBox = new HBox(10, confirmButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        popupRoot.getChildren().addAll(new Text(title), enonceField, categoryComboBox, buttonBox);
        Scene popupScene = new Scene(popupRoot, 300, 250);
        popupScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        popup.setScene(popupScene);
        popup.showAndWait();
    }

    private void showModifyAnamneseQuestionPopup(String title, String[] categories) {
        Stage popup = new Stage();
        VBox popupRoot = new VBox(10);
        popupRoot.setPadding(new Insets(20));
        popupRoot.setAlignment(Pos.CENTER);

        ComboBox<QstLibreAnamnese> questionsComboBox = new ComboBox<>();
        Set<QstLibreAnamnese> questions = patient.getDossierPatient().getListeBOs().get(0).getAnamnese().getQuestions();
        questionsComboBox.getItems().addAll(questions);
        questionsComboBox.setPromptText("Choisir une question");

        TextField enonceField = new TextField();
        TextField reponseField = new TextField();
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(categories);

        questionsComboBox.setOnAction(e -> {
            QstLibreAnamnese selectedQuestion = questionsComboBox.getValue();
            enonceField.setText(selectedQuestion.getEnonce());
            reponseField.setText(selectedQuestion.getReponse());
            
            if (selectedQuestion instanceof QstLibreAnamneseAdulte) {
                categoryComboBox.setValue(((QstLibreAnamneseAdulte) selectedQuestion).getCategorie());
            } else if (selectedQuestion instanceof QstLibreAnamneseEnfant) {
                categoryComboBox.setValue(((QstLibreAnamneseEnfant) selectedQuestion).getCategorie());
            }
        });

        Button confirmButton = new Button("Confirmer");
        Button cancelButton = new Button("Annuler");

        confirmButton.setOnAction(e -> {
            QstLibreAnamnese selectedQuestion = questionsComboBox.getValue();
            if (selectedQuestion != null) {
                selectedQuestion.setEnonce(enonceField.getText());
                selectedQuestion.setReponse(reponseField.getText());
                String newCategorie = categoryComboBox.getValue();
                if (selectedQuestion instanceof QstLibreAnamneseAdulte) {
                    ((QstLibreAnamneseAdulte) selectedQuestion).setCategorie(newCategorie);
                } else if (selectedQuestion instanceof QstLibreAnamneseEnfant) {
                    ((QstLibreAnamneseEnfant) selectedQuestion).setCategorie(newCategorie);
                }
                popup.close();
            } else {
                // Show error message
            }
        });

        cancelButton.setOnAction(e -> popup.close());

        HBox buttonBox = new HBox(10, confirmButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        popupRoot.getChildren().addAll(new Text(title), questionsComboBox, enonceField, reponseField, categoryComboBox, buttonBox);
        Scene popupScene = new Scene(popupRoot, 300, 300);
        popupScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        popup.setScene(popupScene);
        popup.showAndWait();
    }

    private void showQuestionnaireCreationDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Création d'un nouveau questionnaire");
        dialog.getDialogPane().setPrefSize(600, 400);

        // Ajout de boutons Annuler et Confirmer
        ButtonType confirmButtonType = new ButtonType("Confirmer", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, cancelButtonType);

        // Contenu de la boîte de dialogue
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField typeField = new TextField();
        typeField.setPromptText("Type (QCM, QCU, Libre)");
        TextField numberField = new TextField();
        numberField.setPromptText("Nombre de questions");

        grid.add(new Label("Type de questions:"), 0, 0);
        grid.add(typeField, 1, 0);
        grid.add(new Label("Nombre de questions:"), 0, 1);
        grid.add(numberField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Optional<Void> result = dialog.showAndWait();
        if (result.isPresent()) {
            String type = typeField.getText().trim();
            int numberOfQuestions = Integer.parseInt(numberField.getText().trim());

            // Affichage des formulaires de question selon le type
            if ("QCM".equalsIgnoreCase(type) || "QCU".equalsIgnoreCase(type)) {
                showQCMQCUForms(numberOfQuestions, type);
            } else if ("Libre".equalsIgnoreCase(type)) {
                showQuestionLibreForms(numberOfQuestions);
            }
        }
    }


    private void showQCMQCUForms(int numberOfQuestions, String type) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Formulaires de " + type);

        ScrollPane scrollPane = new ScrollPane();
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        scrollPane.setContent(vbox);
        
        // Apply style class to scrollPane
        scrollPane.getStyleClass().add("custom-scroll-pane");

        List<Question> questions = new ArrayList<>();

        for (int i = 0; i < numberOfQuestions; i++) {
            GridPane questionGrid = new GridPane();
            questionGrid.setHgap(10);
            questionGrid.setVgap(10);

            // Apply style class to questionGrid
            questionGrid.getStyleClass().add("custom-grid-pane");

            TextField enonceField = new TextField();
            enonceField.setPromptText("Énoncé de la question");

            TextField choiceNumberField = new TextField();
            choiceNumberField.setPromptText("Nombre de choix");

            Button addChoicesButton = new Button("Ajouter les choix");
            VBox choicesBox = new VBox();
            choicesBox.setSpacing(5);

            // Apply style classes to text fields and button
            enonceField.getStyleClass().add("custom-text-field");
            choiceNumberField.getStyleClass().add("custom-text-field");
            addChoicesButton.getStyleClass().add("custom-button");

            addChoicesButton.setOnAction(e -> {
                int choiceNumber = Integer.parseInt(choiceNumberField.getText().trim());
                choicesBox.getChildren().clear();
                for (int j = 0; j < choiceNumber; j++) {
                    HBox choiceHBox = new HBox();
                    choiceHBox.setSpacing(5);
                    TextField choiceField = new TextField();
                    choiceField.setPromptText("Choix " + (j + 1));
                    CheckBox correctCheckBox = new CheckBox("Réponse juste");

                    // Apply style classes to choice fields and checkboxes
                    choiceField.getStyleClass().add("custom-text-field");
                    correctCheckBox.getStyleClass().add("custom-check-box");

                    choiceHBox.getChildren().addAll(choiceField, correctCheckBox);
                    choicesBox.getChildren().add(choiceHBox);
                }
            });

            questionGrid.add(new Label("Énoncé:"), 0, 0);
            questionGrid.add(enonceField, 1, 0);
            questionGrid.add(new Label("Nombre de choix:"), 0, 1);
            questionGrid.add(choiceNumberField, 1, 1);
            questionGrid.add(addChoicesButton, 2, 1);
            questionGrid.add(choicesBox, 1, 2, 2, 1);

            vbox.getChildren().add(questionGrid);
        }

        dialog.getDialogPane().setContent(scrollPane);
        dialog.getDialogPane().setPrefSize(600, 400);

        ButtonType confirmButtonType = new ButtonType("Confirmer", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, cancelButtonType);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                for (Node node : vbox.getChildren()) {
                    if (node instanceof GridPane) {
                        GridPane gridPane = (GridPane) node;
                        String enonce = ((TextField) gridPane.getChildren().get(1)).getText();
                        VBox choicesVBox = (VBox) gridPane.getChildren().get(5);
                        ArrayList<String> reponsesJustes = new ArrayList<>();
                        ArrayList<String> reponsesFausses = new ArrayList<>();

                        for (Node choiceNode : choicesVBox.getChildren()) {
                            if (choiceNode instanceof HBox) {
                                HBox hBox = (HBox) choiceNode;
                                String choice = ((TextField) hBox.getChildren().get(0)).getText();
                                boolean isCorrect = ((CheckBox) hBox.getChildren().get(1)).isSelected();
                                if (isCorrect) {
                                    reponsesJustes.add(choice);
                                } else {
                                    reponsesFausses.add(choice);
                                }
                            }
                        }
                        Question question;
                        if ("QCM".equalsIgnoreCase(type)) {
                            QCM qcm = new QCM(enonce);
                            qcm.setNbPropositions(reponsesJustes.size() + reponsesFausses.size());
                            qcm.setReponsesJustes(reponsesJustes);
                            qcm.setReponsesFausses(reponsesFausses);
                            question = qcm;
                        } else {
                            QCU qcu = new QCU(enonce);
                            qcu.setReponseJuste(reponsesJustes.isEmpty() ? null : reponsesJustes.get(0));
                            qcu.setReponsesFausses(reponsesFausses);
                            question = qcu;
                        }
                        questions.add(question);
                    }
                }
                return null;
            }
            return null;
        });

        dialog.showAndWait();
        sauvegarderQuestions(questions);
    }



        
    private void showQuestionLibreForms(int numberOfQuestions) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Formulaires de Question Libre");
        dialog.getDialogPane().setPrefSize(600, 400);

        ScrollPane scrollPane = new ScrollPane();
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        scrollPane.setContent(vbox);

        List<QuestionLibre> questions = new ArrayList<>();

        for (int i = 0; i < numberOfQuestions; i++) {
            GridPane questionGrid = new GridPane();
            questionGrid.setHgap(10);
            questionGrid.setVgap(10);

            TextField enonceField = new TextField();
            enonceField.setPromptText("Énoncé de la question");

            questionGrid.add(new Label("Énoncé:"), 0, 0);
            questionGrid.add(enonceField, 1, 0);

            vbox.getChildren().add(questionGrid);
        }

        dialog.getDialogPane().setContent(scrollPane);

        ButtonType confirmButtonType = new ButtonType("Confirmer", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, cancelButtonType);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                for (Node node : vbox.getChildren()) {
                    if (node instanceof GridPane) {
                        GridPane gridPane = (GridPane) node;
                        String enonce = ((TextField) gridPane.getChildren().get(1)).getText();
                        QuestionLibre question = new QuestionLibre(enonce);
                        questions.add(question);
                    }
                }
                return null;
            }
            return null;
        });

        dialog.showAndWait();
        sauvegarderQuestions(questions);
    }

    
    private void sauvegarderQuestions(List<? extends Question> questions) {
        // la logic de l'ajout des questions est la (back-end work please) !!!!
    }
    

    private void showCreateAnamnesePopup() {
        Stage popup = new Stage();
        VBox popupRoot = new VBox(10);
        popupRoot.setPadding(new Insets(20));
        popupRoot.setAlignment(Pos.CENTER);

        TextField numberOfQuestionsField = new TextField();
        numberOfQuestionsField.setPromptText("Nombre de questions");

        Button confirmButton = new Button("Confirmer");
        Button cancelButton = new Button("Annuler");

        confirmButton.setOnAction(e -> {
            int numberOfQuestions;
            try {
                numberOfQuestions = Integer.parseInt(numberOfQuestionsField.getText());
            } catch (NumberFormatException ex) {
                // Show error message for invalid input
                return;
            }

            if (numberOfQuestions > 0) {
                showAnamneseQuestionsPopup(numberOfQuestions);
                popup.close();
            } else {
                // Show error message for invalid input
            }
        });

        cancelButton.setOnAction(e -> popup.close());

        HBox buttonBox = new HBox(10, confirmButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        popupRoot.getChildren().addAll(new Text("Entrer le nombre de questions pour l'anamnèse"), numberOfQuestionsField, buttonBox);
        Scene popupScene = new Scene(popupRoot, 400, 200);
        popupScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        popup.setScene(popupScene);
        popup.showAndWait();
    }

    private void showAnamneseQuestionsPopup(int numberOfQuestions) {
        Stage popup = new Stage();
        VBox popupRoot = new VBox(10);
        popupRoot.setPadding(new Insets(20));
        popupRoot.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);

        VBox scrollContent = new VBox(10);
        scrollContent.setPadding(new Insets(10));

        List<TextField> enonceFields = new ArrayList<>();
        List<ComboBox<String>> categoryComboBoxes = new ArrayList<>();

        String[] categoriesAdult = {"Histoire de la Maladie", "Suivi Médical"};
        String[] categoriesChild = {"Structure Familiale", "Dynamique Familiale", "Antecedents Familiaux", "Conditions Natales", "Developpement Psychomoteur", "Developpement Langagier", "Caractere Comportement"};
        String[] categories = patient.getDossierPatient().getAge() >= 18 ? categoriesAdult : categoriesChild;

        for (int i = 0; i < numberOfQuestions; i++) {
            TextField enonceField = new TextField();
            enonceField.setPromptText("Énoncé de la question " + (i + 1));
            enonceFields.add(enonceField);

            ComboBox<String> categoryComboBox = new ComboBox<>();
            categoryComboBox.getItems().addAll(categories);
            categoryComboBox.setPromptText("Catégorie de la question " + (i + 1));
            categoryComboBoxes.add(categoryComboBox);

            scrollContent.getChildren().addAll(enonceField, categoryComboBox);
        }

        scrollPane.setContent(scrollContent);

        Button saveButton = new Button("Sauvegarder");
        Button cancelButton = new Button("Annuler");

        saveButton.setOnAction(e -> {
            // Logique de sauvegarde des questions
            popup.close();
        });

        cancelButton.setOnAction(e -> popup.close());

        HBox buttonBox = new HBox(10, saveButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        popupRoot.getChildren().addAll(scrollPane, buttonBox);
        Scene popupScene = new Scene(popupRoot, 500, 400);
        popupScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        popup.setScene(popupScene);
        popup.showAndWait();
    }



}
