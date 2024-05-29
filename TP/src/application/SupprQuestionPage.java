package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class SupprQuestionPage {
    private Stage primaryStage;
    private Patient patient;
    private QCM qcm = new QCM(null);
    private QCU qcu = new QCU(null);
    private QuestionLibre qstLibre = new QuestionLibre(null);
    private String proposition = null;
    private HashSet<Orthophoniste> comptesUtilisateurs = loadComptesOrthophonisteFromFile();
    private Orthophoniste orthophoniste;



    public SupprQuestionPage(Stage primaryStage, Patient patient,Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.patient = patient;
        this.orthophoniste=orthophoniste;
    }

    public void load(Scene scene) {
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
            primaryStage.setScene(createQCMListScene(primaryStage));
            primaryStage.setTitle("Liste des questionnaires QCM");
        });
        qcuButton.setOnAction(e -> {
            primaryStage.setScene(createQCUListScene(primaryStage));
            primaryStage.setTitle("Ajouter une question QCU");
        });
        libreButton.setOnAction(e -> {
            primaryStage.setScene(createQstLibreListScene(primaryStage));
            primaryStage.setTitle("Ajouter une question libre");
        });

        // Scene setup
        scene = new Scene(root, 500, 200);
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

    private Scene createQCMListScene(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        ArrayList<Questionnaire> listeQuestionnaires = patient.getDossierPatient().getListeQuestionnaire();
        for (int i = 0; i < listeQuestionnaires.size(); i++) {
            int index = i; // To use inside the lambda
            Button qcmButton = new Button("QCM " + (i + 1));
            qcmButton.setOnAction(e -> {
                primaryStage.setScene(createQCMScene(primaryStage, listeQuestionnaires.get(index)));
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

    private Scene createQCMScene(Stage primaryStage, Questionnaire questionnaire) {
        VBox qcmLayout = new VBox(10);
        qcmLayout.setAlignment(Pos.CENTER);
        qcmLayout.setPadding(new Insets(20));

        ArrayList<QCM> listeQcm = questionnaire.getListeQcm();
        for (int i = 0; i < listeQcm.size(); i++) {
            QCM qcm = listeQcm.get(i);
            Label questionLabel = new Label("Question : " + qcm.getEnonce());
            questionLabel.getStyleClass().add("label-style");

            VBox propositionsBox = new VBox(10);
          

            for (int j = 0; j < qcm.getNbPropositions(); j++) {
                Label choixLabel = new Label("Choix " + (j + 1) + ": " + qcm.getReponses().get(j));
                choixLabel.getStyleClass().add("label-style");
                propositionsBox.getChildren().add(choixLabel);
            }

            qcmLayout.getChildren().addAll(questionLabel, propositionsBox);
        }

        Button removeButton = new Button("Supprimer");
        Button cancelButton = new Button("Annuler");
        HBox buttonsBox = new HBox(20);
        buttonsBox.getChildren().addAll(removeButton, cancelButton);
        buttonsBox.setAlignment(Pos.CENTER);

        removeButton.setOnAction(event -> {
        	showQCMQCUForms("QCM",questionnaire);
          
        });
        cancelButton.setOnAction(event -> primaryStage.close());

        qcmLayout.getChildren().add(buttonsBox);

        ScrollPane scrollPane = new ScrollPane(qcmLayout);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 500, 400);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.centerOnScreen();

        return scene;
    }
    
    private Scene createQCUListScene(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        ArrayList<Questionnaire> listeQuestionnaires = patient.getDossierPatient().getListeQuestionnaire();
        for (int i = 0; i < listeQuestionnaires.size(); i++) {
            int index = i; // To use inside the lambda
            Button qcuButton = new Button("QCU " + (i + 1));
            qcuButton.setOnAction(e -> {
                primaryStage.setScene(createQCUScene(primaryStage, listeQuestionnaires.get(index)));
                primaryStage.setTitle("Ajouter une question QCM " + (index + 1));
            });
            root.getChildren().add(qcuButton);
        }

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 500, 400);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.centerOnScreen();

        return scene;
    }

    private Scene createQCUScene(Stage primaryStage,Questionnaire questionnaire) {
    	 VBox qcuLayout = new VBox(10);
         qcuLayout.setAlignment(Pos.CENTER);
         qcuLayout.setPadding(new Insets(20));

         ArrayList<QCU> listeQcu = questionnaire.getListeQcu();
         for (int i = 0; i < listeQcu.size(); i++) {
             QCU qcu = listeQcu.get(i);
             Label questionLabel = new Label("Question : " + qcm.getEnonce());
             questionLabel.getStyleClass().add("label-style");

             VBox propositionsBox = new VBox(10);
           

             for (int j = 0; j < qcm.getNbPropositions(); j++) {
                 Label choixLabel = new Label("Choix " + (j + 1) + ": " + qcm.getReponses().get(j));
                 choixLabel.getStyleClass().add("label-style");
                 propositionsBox.getChildren().add(choixLabel);
             }

             qcuLayout.getChildren().addAll(questionLabel, propositionsBox);
         }

         Button addButton = new Button("Supprimer");
         Button cancelButton = new Button("Annuler");
         HBox buttonsBox = new HBox(20);
         buttonsBox.getChildren().addAll(addButton, cancelButton);
         buttonsBox.setAlignment(Pos.CENTER);

         addButton.setOnAction(event -> {


         });
         cancelButton.setOnAction(event -> primaryStage.close());

         qcuLayout.getChildren().add(buttonsBox);

         ScrollPane scrollPane = new ScrollPane(qcuLayout);
         scrollPane.setFitToWidth(true);

         Scene scene = new Scene(scrollPane, 500, 400);
         scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
         primaryStage.centerOnScreen();

         return scene;
     }
    

    
    private Scene createQstLibreListScene(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        ArrayList<Questionnaire> listeQuestionnaires = patient.getDossierPatient().getListeQuestionnaire();
        for (int i = 0; i < listeQuestionnaires.size(); i++) {
            int index = i; // To use inside the lambda
            Button qstLibreButton = new Button("Question " + (i + 1));
            qstLibreButton.setOnAction(e -> {
                primaryStage.setScene(createLibreScene(primaryStage, listeQuestionnaires.get(index)));
                primaryStage.setTitle("Ajouter une question libre " + (index + 1));
            });
            root.getChildren().add(qstLibreButton);
        }

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 500, 400);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.centerOnScreen();

        return scene;
    }


    private Scene createLibreScene(Stage primaryStage,Questionnaire questionnaire) {
    	 VBox qstLibreLayout = new VBox(10);
         qstLibreLayout.setAlignment(Pos.CENTER);
         qstLibreLayout.setPadding(new Insets(20));

         ArrayList<QuestionLibre> listeQstLibre = questionnaire.getListeQstLibre();
         for (int i = 0; i < listeQstLibre.size(); i++) {
             QuestionLibre qstLibre = listeQstLibre.get(i);
             Label questionLabel = new Label("Question : " + qstLibre.getEnonce());
             questionLabel.getStyleClass().add("label-style");



         Button addButton = new Button("Supprimer");
         Button cancelButton = new Button("Annuler");
         HBox buttonsBox = new HBox(20);
         buttonsBox.getChildren().addAll(addButton, cancelButton);
         buttonsBox.setAlignment(Pos.CENTER);

         addButton.setOnAction(event -> {
        	 showQCMQCUForms("Libre",questionnaire);

         });
         cancelButton.setOnAction(event -> primaryStage.close());

         qstLibreLayout.getChildren().add(buttonsBox);

    }
         
         ScrollPane scrollPane = new ScrollPane(qstLibreLayout);
         scrollPane.setFitToWidth(true);
         Scene scene = new Scene(scrollPane, 500, 400);
         scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
         primaryStage.centerOnScreen();
         return scene;
    }
    
    private void showQCMQCUForms(String type,Questionnaire questionnaire) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Formulaires de " + type);

        ScrollPane scrollPane = new ScrollPane();
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        scrollPane.setContent(vbox);
        
        // Apply style class to scrollPane
        scrollPane.getStyleClass().add("custom-scroll-pane");


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
        

        dialog.getDialogPane().setContent(scrollPane);
        dialog.getDialogPane().setPrefSize(600, 400);

        ButtonType confirmButtonType = new ButtonType("Confirmer", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, cancelButtonType);

        dialog.setResultConverter(dialogButton -> {
        	ArrayList<Questionnaire>  listeQuestionnaires = patient.getDossierPatient().getListeQuestionnaire();
        	if (listeQuestionnaires==null) {
        		listeQuestionnaires= new ArrayList<Questionnaire>();
        	}
   
            if (dialogButton == confirmButtonType) {
                for (Node node : vbox.getChildren()) {
                    if (node instanceof GridPane) {
                        GridPane gridPane = (GridPane) node;
                        String enonce = ((TextField) gridPane.getChildren().get(1)).getText();
                        VBox choicesVBox = (VBox) gridPane.getChildren().get(5);
                        ArrayList<String> reponsesJustes = new ArrayList<>();
                        ArrayList<String> reponsesFausses = new ArrayList<>();
                        ArrayList<String> reponses = new ArrayList<>();

                        for (Node choiceNode : choicesVBox.getChildren()) {
                            if (choiceNode instanceof HBox) {
                                HBox hBox = (HBox) choiceNode;
                                String choice = ((TextField) hBox.getChildren().get(0)).getText();
                                boolean isCorrect = ((CheckBox) hBox.getChildren().get(1)).isSelected();
                                reponses.add(choice);
                                if (isCorrect) {
                                    reponsesJustes.add(choice);
                                } else {
                                    reponsesFausses.add(choice);
                                }
                            }
                        }

                        if ("QCM".equalsIgnoreCase(type)) {
                            QCM qcm = new QCM(enonce);
                            qcm.setNbPropositions(reponsesJustes.size() + reponsesFausses.size());
                            qcm.setReponsesJustes(reponsesJustes);
                            qcm.setReponsesFausses(reponsesFausses);
                            qcm.setReponses(reponses);
                            questionnaire.ajouterQcm(qcm);
                        } else {
                            QCU qcu = new QCU(enonce);
                            qcu.setReponseJuste(reponsesJustes.isEmpty() ? null : reponsesJustes.get(0));
                            qcu.setReponsesFausses(reponsesFausses);
                            qcu.setReponses(reponses);
                            questionnaire.ajouterQcu(qcu);
                        }                
                        
                    }
                }
                if (listeQuestionnaires.contains(questionnaire)) listeQuestionnaires.remove(questionnaire);
                listeQuestionnaires.add(questionnaire);
                             
            } 
            
             // sauvegarder dans le fichier               
            
            if (this.comptesUtilisateurs.contains(orthophoniste)) {
             	comptesUtilisateurs.remove(orthophoniste);
            
             }
          	comptesUtilisateurs.add(orthophoniste);
          	saveComptesOrthophonisteToFile(comptesUtilisateurs);
            return null;
           
        });
        // sauvegarder dans le fichier               
        
        if (this.comptesUtilisateurs.contains(orthophoniste)) {
         	comptesUtilisateurs.remove(orthophoniste);
        
         }
      	comptesUtilisateurs.add(orthophoniste);
      	saveComptesOrthophonisteToFile(comptesUtilisateurs);
        dialog.showAndWait();
    }
    
    public void saveComptesOrthophonisteToFile(HashSet<Orthophoniste> comptesUtilisateurs) {
    	File f = new File ("comptesOrthophoniste.ser");
        try (FileOutputStream fileOut = new FileOutputStream(f);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(comptesUtilisateurs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    public HashSet<Orthophoniste> loadComptesOrthophonisteFromFile() {
        HashSet<Orthophoniste> comptesUtilisateurs = null;
        try (FileInputStream fileIn = new FileInputStream("comptesOrthophoniste.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            comptesUtilisateurs = (HashSet<Orthophoniste>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return comptesUtilisateurs;
    }
    
}
