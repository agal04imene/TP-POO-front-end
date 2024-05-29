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

public class AddPatientAdult {
    private Stage primaryStage;
    private Orthophoniste orthophoniste;
    private Patient patient;
    private InfoDossier infos;
    private HashSet<Orthophoniste> comptesUtilisateurs = new HashSet<>();
    private Label messageLabel;  // Declare messageLabel as an instance variable

    public AddPatientAdult(Stage primaryStage, Orthophoniste orthophoniste, InfoDossier infos) {
        this.primaryStage = primaryStage;
        this.orthophoniste = orthophoniste;
        this.infos = infos;
    }

    public void load(Scene scene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Form
        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER);

        TextField diplomaField = new TextField();
        diplomaField.setPromptText("Diplôme");
        diplomaField.setMaxWidth(200);

        TextField professionField = new TextField();
        professionField.setPromptText("Profession");
        professionField.setMaxWidth(200);

        TextField phoneField = new TextField();
        phoneField.setPromptText("Numéro de téléphone");
        phoneField.setMaxWidth(200);

        form.getChildren().addAll(diplomaField, professionField, phoneField);

        // Message Label
        messageLabel = new Label();  // Initialize messageLabel
        form.getChildren().add(messageLabel);

        // Submit Button
        Button submitButton = new Button("Ajouter le Patient");
        submitButton.getStyleClass().add("button-style");
        submitButton.setOnAction(e -> {
            // Handle submission
            String diploma = diplomaField.getText();
            String profession = professionField.getText();
            String phone = phoneField.getText();

            DossierAdulte dossierAdulte = new DossierAdulte(infos.name0, infos.surename0, infos.age0,  infos.address0,infos.birthday0, infos.birthPlace0, diploma, profession, phone);
            
            Patient patient = new Patient(dossierAdulte);

            comptesUtilisateurs = loadComptesOrthophonisteFromFile();
            if (comptesUtilisateurs.contains(orthophoniste)) {
           	 comptesUtilisateurs.remove(orthophoniste);
           }
           
            if (orthophoniste.getListePatients() == null) {
           	 orthophoniste.setListePatients(new HashSet<>())  ;
           	}
            if (!orthophoniste.getListePatients().contains(patient))   orthophoniste.getListePatients().add(patient);
          
           comptesUtilisateurs.add(orthophoniste); // ajouter apres le changement
           
           saveComptesOrthophonisteToFile( comptesUtilisateurs); // sauvegarder les nouvelles données dans un fichier
           

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

        Scene addPatientAdultScene = new Scene(root, 800, 700);
        addPatientAdultScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(addPatientAdultScene);
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
    
    public Anamnese  genererAnamnese () {
    	Anamnese anamnese = new Anamnese ();
    	return anamnese;
    }

    private void setMessage(String message, String color) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: " + color + ";");
    }
}
