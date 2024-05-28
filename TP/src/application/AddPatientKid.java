package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddPatientKid {
    private Stage primaryStage;
    private Orthophoniste orthophoniste;
    private InfoDossier infos;
    private HashSet<Orthophoniste> comptesUtilisateurs = new HashSet<>();
    private Label messageLabel;  // Declare messageLabel as an instance variable

    public AddPatientKid(Stage primaryStage, Orthophoniste orthophoniste, InfoDossier infos) {
        this.primaryStage = primaryStage;
        this.orthophoniste = orthophoniste;
        this.infos=infos;
    }

    public void load(Scene scene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Form
        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER);

        TextField studyClassField = new TextField();
        studyClassField.setPromptText("Classe d'étude");
        studyClassField.setMaxWidth(200);

        TextField motherPhoneField = new TextField();
        motherPhoneField.setPromptText("Numéro de la mère");
        motherPhoneField.setMaxWidth(200);

        TextField fatherPhoneField = new TextField();
        fatherPhoneField.setPromptText("Numéro du père");
        fatherPhoneField.setMaxWidth(200);

        form.getChildren().addAll(studyClassField, motherPhoneField, fatherPhoneField);
        
        // Message Label
        messageLabel = new Label();  // Initialize messageLabel
        form.getChildren().add(messageLabel);


        // Submit Button
        Button submitButton = new Button("Ajouter le Patient");
        submitButton.getStyleClass().add("button-style");
        submitButton.setOnAction(e -> {
            // Handle submission
            String studyClass = studyClassField.getText();
            String motherPhone = motherPhoneField.getText();
            String fatherPhone = fatherPhoneField.getText();

            DossierAdulte dossierAdulte = new DossierAdulte(infos.name0, infos.surename0, infos.age0, infos.birthday0, infos.birthPlace0, infos.address0, studyClass, motherPhone, fatherPhone);
            Patient patient = new Patient(dossierAdulte);

            comptesUtilisateurs = loadComptesOrthophonisteFromFile();

            if (orthophoniste.getListePatients() == null) {
                orthophoniste.setListePatients(new HashSet<>());
            }
            if (!orthophoniste.getListePatients().contains(patient)) {
                orthophoniste.getListePatients().add(patient);
            }

            comptesUtilisateurs.add(orthophoniste); // ajouter après le changement

            saveComptesOrthophonisteToFile(comptesUtilisateurs); // sauvegarder les nouvelles données dans un fichier

            setMessage("Le patient a été ajouté avec succès.", "green");
           
        });

        // Back Button
        Button backButton = new Button("Retour");
        backButton.getStyleClass().add("button-style");
        backButton.setOnAction(e -> {
            AddPatientPage addPatientPage = new AddPatientPage(primaryStage, orthophoniste);
            addPatientPage.load(scene);
        });

        // Button Container
        VBox buttonBox = new VBox(10, submitButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));

        root.setCenter(form);
        root.setBottom(buttonBox);
        BorderPane.setAlignment(buttonBox, Pos.CENTER);

        Scene addPatientKidScene = new Scene(root, 800, 700);
        addPatientKidScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(addPatientKidScene);
    }

    public void saveComptesOrthophonisteToFile(HashSet<Orthophoniste> comptesUtilisateurs) {
        File f = new File("comptesOrthophoniste.ser");
        try (FileOutputStream fileOut = new FileOutputStream(f);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(comptesUtilisateurs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashSet<Orthophoniste> loadComptesOrthophonisteFromFile() {
        HashSet<Orthophoniste> comptesUtilisateurs = new HashSet<>();
        File f = new File("comptesOrthophoniste.ser");
        if (f.exists()) {
            try (FileInputStream fileIn = new FileInputStream(f);
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {
                comptesUtilisateurs = (HashSet<Orthophoniste>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return comptesUtilisateurs;
    }

    private void setMessage(String message, String color) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: " + color + ";");
    }
}
