//gonna need pages for : adding & saving a new test, modifying an existing test, removing a test as well + same things for anamnèse
// gotta adding choosing a patient from the list of patients first as well
// also, animations would be nice + different background color + a page for choosing an orthophoniste account :)

package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Accordion;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class ManageTestsPage extends Application {

    @Override
    public void start(Stage primaryStage) {

        Label ajoutQuestionLabel, supprQuestionLabel, modifQuestionLabel;

        Label ajoutExerciceLabel, supprExerciceLabel, modifExerciceLabel;

        Label ajoutAnamneseLabel, supprAnamneseLabel, modifAnamneseLabel;

        Label ajoutQuestAnamneseLabel, supprQuestAnamneseLabel, modifQuestAnamneseLabel;
    	
        primaryStage.setTitle("Gestion des Tests");

        // Main menu options
        Label gestionQuestionnaireLabel = createLabel("Gestion des Questionnaires");
        Label gestionExercicesLabel = createLabel("Gestion des Séries d'Exercices");
        Label gestionAnamnesesLabel = createLabel("Gestion des Anamnèses");

        // Sub-menus
        VBox questionnaireOptions = createSubMenu(
                ajoutQuestionLabel = createLabel("Ajouter une question à un questionnaire"),
                supprQuestionLabel = createLabel("Supprimer une question d'un questionnaire"),
                modifQuestionLabel = createLabel("Modifier une question dans un questionnaire")
        );

        VBox exercicesOptions = createSubMenu(
                ajoutExerciceLabel = createLabel("Ajouter un exercice à une série d'exercices"),
                supprExerciceLabel = createLabel("Supprimer un exercice d'une série d'exercices"),
                modifExerciceLabel = createLabel("Modifier un exercice dans une série d'exercices")
        );

        VBox anamnesesOptions = createSubMenu(
                ajoutAnamneseLabel = createLabel("Ajouter une anamnèse à un bilan orthophonique"),
                supprAnamneseLabel = createLabel("Supprimer l'anamnèse d'un patient de son BO"),
                modifAnamneseLabel = createLabel("Modifier l'anamnèse d'un patient")
        );

        VBox modifyAnamneseOptions = createSubMenu(
                ajoutQuestAnamneseLabel = createLabel("Ajouter une nouvelle question à l'anamnèse d'un patient"),
                supprQuestAnamneseLabel = createLabel("Supprimer une question d'une anamnèse"),
                modifQuestAnamneseLabel = createLabel("Modifier une question dans une anamnèse")
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

        
        // Actions
        ajoutQuestionLabel.setOnMouseClicked(e -> {
            // Navigation
        	AjoutQuestionPage ajoutQuestionPage = new AjoutQuestionPage();
            Stage stage = new Stage();
            ajoutQuestionPage.start(stage);
        });
        
        
        
        
        StackPane root = new StackPane(accordion);
        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("options-label");
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
    
}
