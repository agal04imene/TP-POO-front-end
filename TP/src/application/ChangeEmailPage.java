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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ChangeEmailPage {
    private Stage primaryStage;
    private Orthophoniste orthophoniste;
    private HashSet<Orthophoniste> comptesUtilisateurs;

    public ChangeEmailPage(Stage primaryStage,Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.orthophoniste=orthophoniste;
    }

    public void load(Scene scene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #FFFFFF;"); // Light background color

        // Change Email Title
        Text title = new Text("Changer l'adresse E-mail");
        title.getStyleClass().add("change-title");
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);

        // Change Email Container
        VBox changeEmailContainer = new VBox(10);
        changeEmailContainer.setAlignment(Pos.CENTER);
        changeEmailContainer.getStyleClass().add("change-container");

        // Old Email Field
        TextField oldEmailField = new TextField();
        oldEmailField.getStyleClass().add("text-field");
        oldEmailField.setPromptText("Ancienne adresse E-mail");

        // New Email Field
        TextField newEmailField = new TextField();
        newEmailField.getStyleClass().add("text-field");
        newEmailField.setPromptText("Nouvelle adresse E-mail");

        // Confirm New Email Field
        TextField confirmNewEmailField = new TextField();
        confirmNewEmailField.getStyleClass().add("text-field");
        confirmNewEmailField.setPromptText("Confirmer la Nouvelle adresse E-mail");

        // Button Bar
        HBox buttonBar = new HBox(20);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.getStyleClass().add("change-button-bar");

        // Confirm Button
        Button confirmButton = new Button("Confirmer");
        confirmButton.getStyleClass().add("change-button");

        // Add action to confirm button
        confirmButton.setOnAction(e -> {
            String oldEmail = oldEmailField.getText();
            String newEmail = newEmailField.getText();
            String confirmNewEmail = confirmNewEmailField.getText();

            if (!isValidEmail(oldEmail)) {
            	// Another condition needs to be added here (correct e-mail of the account) !!
                showAlert("Veuillez saisir une ancienne adresse e-mail valide.");
            } else if (!isValidEmail(newEmail)) {
            	showAlert("Veuillez saisir une nouvelle adresse e-mail valide.");
            } else if (!newEmail.equals(confirmNewEmail)) {
            	showAlert("Les nouvelles adresses e-mail ne correspondent pas.");
            } else {
            	
                
                // Recupérer le compte d'orthophoniste
            	comptesUtilisateurs = loadComptesOrthophonisteFromFile();
            	
            	// Changement du l'email du compte 
            	  for (Orthophoniste orthophoniste : comptesUtilisateurs) {
                      if (orthophoniste.getAdresseEmail().equals(oldEmail)) {
                          orthophoniste.setAdressEmail(newEmail);
                          
                          // sauvegarder les changement dans un fichier
                          saveComptesOrthophonisteToFile(comptesUtilisateurs);
                          break;
                      }
                  }
                showAlert("Changement d'adresse e-mail effectué avec succès.");
                AccountSettingsPage accountSettingsPage = new AccountSettingsPage(primaryStage,orthophoniste);              
                accountSettingsPage.load(scene);
            }
        });

        // Cancel Button
        Button cancelButton = new Button("Annuler");
        cancelButton.getStyleClass().add("change-button");

        // Add action to cancel button
        cancelButton.setOnAction(e -> {
            AccountSettingsPage accountSettingsPage = new AccountSettingsPage(primaryStage,orthophoniste);
            accountSettingsPage.load(scene);
        });

        buttonBar.getChildren().addAll(confirmButton, cancelButton);

        changeEmailContainer.getChildren().addAll(oldEmailField, newEmailField, confirmNewEmailField);

        root.setCenter(changeEmailContainer);

        root.setBottom(buttonBar);

        Scene changeEmailScene = new Scene(root, 500, 500);
        changeEmailScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(changeEmailScene);

        primaryStage.centerOnScreen();
    }

    // Email validation method
    private boolean isValidEmail(String email) {
        // Simple email validation regex pattern
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
    
    public void saveComptesOrthophonisteToFile(HashSet<Orthophoniste> comptesUtilisateurs) {
    	File f = new File ("comptesOrthophoniste.ser");
        try (FileOutputStream fileOut = new FileOutputStream(f);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(comptesUtilisateurs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }


}