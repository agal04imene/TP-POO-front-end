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
    private Orthophoniste orthophoniste;
    private HashSet<Patient> listePatients = new HashSet<>();
    private HashSet<Orthophoniste> comptesUtilisateurs;

    public RemovePatientPage(Stage primaryStage, Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.orthophoniste = orthophoniste;
        this.comptesUtilisateurs = loadComptesOrthophonisteFromFile();
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

            listePatients = orthophoniste.getListePatients();
            boolean patientFound = false;
            for (Patient patient : listePatients) {
                if (patient.getDossierPatient().getNom().equals(nom) && patient.getDossierPatient().getPrenom().equals(prenom)) {
                    boolean supp = listePatients.remove(patient);
                    patientFound = true;
                    if (supp) {
                        if (comptesUtilisateurs.contains(orthophoniste)) {
                            comptesUtilisateurs.remove(orthophoniste);
                        }
                        comptesUtilisateurs.add(orthophoniste);
                        saveComptesOrthophonisteToFile(comptesUtilisateurs);

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
            }
            if (!patientFound) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Patient non trouvé");
                alert.setHeaderText(null);
                alert.setContentText("Il n'y a aucun patient avec ce nom et prénom.");
                alert.showAndWait();
            }
        });

        // Back button
        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> {
            MenuPrincipal menuPrincipal = new MenuPrincipal(primaryStage, orthophoniste);
            menuPrincipal.load(previousScene);
        });

        root.getChildren().addAll(titleLabel, nomField, prenomField, removeButton, backButton);

        Scene removePatientScene = new Scene(root, 800, 700);
        removePatientScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(removePatientScene);
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
}
