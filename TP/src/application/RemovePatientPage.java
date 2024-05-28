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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RemovePatientPage {
    private Stage primaryStage;
    Orthophoniste orthophoniste;
    private HashSet<Patient> listePatients = new HashSet<Patient>();

    public RemovePatientPage(Stage primaryStage,Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.orthophoniste= orthophoniste;
    }

    public void load(Scene previousScene) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // Title
        Label titleLabel = new Label("Supprimer le dossier d'un Patient");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #00215E;");

        // Text fields
        TextField nomField = new TextField();
        nomField.setPromptText("Nom du Patient");

        TextField prenomField = new TextField();
        prenomField.setPromptText("Prénom du Patient");

        // Remove button
        Button removeButton = new Button("Supprimer Le patient");
        removeButton.getStyleClass().add("button-style");
        
        // Suppression du patient s'il existe
        removeButton.setOnAction(e -> {           
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            
            listePatients = loadlistePatientsFromFile();
            boolean patientFound=false;
            for (Patient patient : listePatients) {
                if (patient.getDossierPatient().getNom().equals(nom) && patient.getDossierPatient().getPrenom().equals(prenom)) {
                    boolean supp=listePatients.remove(patient);
                    patientFound=true;
                    if (supp) {
                        savelistePatientsToFile(listePatients);
                        
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Suppression réussie");
                        alert.setHeaderText(null);
                        alert.setContentText("Le dossier du patient a été supprimé avec succès.");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Erreur de suppression");
                        alert.setHeaderText(null);
                        alert.setContentText("La suppression du dossier du patient a échoué.");
                        alert.showAndWait();
                    }
                    break;
                }
                if (!patientFound) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Patient non trouvé");
                    alert.setHeaderText(null);
                    alert.setContentText("Il n'y a aucun patient avec ce nom et prénom.");
                    alert.showAndWait();
                }
               }                     
            
        });

        // Back button
        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> {
        	MenuPrincipal menuPrincipal = new MenuPrincipal(primaryStage,orthophoniste);
            menuPrincipal.load(previousScene);
        });

        root.getChildren().addAll(titleLabel, nomField, prenomField, removeButton, backButton);

        Scene removePatientScene = new Scene(root, 800, 700);
        removePatientScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(removePatientScene);
    }
    
    public HashSet<Patient> loadlistePatientsFromFile() {
        HashSet<Patient> listePatients = null;
        File f = new File("listPatients.ser");
        if (!f.exists()) {
            // If the file doesn't exist, return an empty HashSet
            return new HashSet<Patient>();
        }

        try (FileInputStream fileIn = new FileInputStream(f);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            listePatients = (HashSet<Patient>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return listePatients;
    }
    
    public void savelistePatientsToFile(HashSet<Patient> listePatients) {
        File f = new File("listPatients.ser");
        try (FileOutputStream fileOut = new FileOutputStream(f);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(listePatients);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
