package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashSet;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditPersonalInfoPage {
    private Stage primaryStage;
    private Orthophoniste orthophoniste;
    private HashSet<Orthophoniste> comptesUtilisateurs;

    public EditPersonalInfoPage(Stage primaryStage, Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.orthophoniste = orthophoniste;
    }

    public void load(Scene scene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #FFFFFF;"); // Light background color

        // Edit Personal Info Title
        Text title = new Text("Modifier les Informations Personnelles");
        title.getStyleClass().add("change-title");
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);

        // Edit Personal Info Container
        VBox editPersonalInfoContainer = new VBox(10);
        editPersonalInfoContainer.setAlignment(Pos.CENTER);
        editPersonalInfoContainer.getStyleClass().add("change-container");

        // Name Field
        TextField nameField = new TextField(orthophoniste.getNom());
        nameField.getStyleClass().add("text-field");
        nameField.setPromptText("Nom");

        // Surname Field
        TextField surnameField = new TextField(orthophoniste.getPrenom());
        surnameField.getStyleClass().add("text-field");
        surnameField.setPromptText("Prénom");

        // Phone Number Field
        TextField phoneNumberField = new TextField(orthophoniste.getNumTelephone());
        phoneNumberField.getStyleClass().add("text-field");
        phoneNumberField.setPromptText("Numéro de téléphone");

        // Address Field
        TextField addressField = new TextField(orthophoniste.getAddress());
        addressField.getStyleClass().add("text-field");
        addressField.setPromptText("Adresse");

        // Button Bar
        HBox buttonBar = new HBox(20);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.getStyleClass().add("change-button-bar");

        // Confirm Button
        Button confirmButton = new Button("Confirmer");
        confirmButton.getStyleClass().add("change-button");

        // Cancel Button
        Button cancelButton = new Button("Annuler");
        cancelButton.getStyleClass().add("change-button");

        cancelButton.setOnAction(e -> {
            AccountSettingsPage accountSettingsPage = new AccountSettingsPage(primaryStage, orthophoniste);
            accountSettingsPage.load(scene);
        });
        
        confirmButton.setOnAction(e -> {
        	
        	comptesUtilisateurs=loadComptesOrthophonisteFromFile();
        	
        	// supprimer l'ancien code de l'orthophoniste
        	comptesUtilisateurs.remove(orthophoniste);
        	
        	//changer les informations 
            orthophoniste.setNom(nameField.getText());
            orthophoniste.setPrenom(surnameField.getText());
            orthophoniste.setNumTelephone(phoneNumberField.getText());
            orthophoniste.setAdresse(addressField.getText());
            
           // sauvegarder le compte de l'orthophoniste avec les nouvelles informations 
            comptesUtilisateurs.add(orthophoniste);
            
            

            AccountSettingsPage accountSettingsPage = new AccountSettingsPage(primaryStage, orthophoniste);
            accountSettingsPage.load(scene);
        });

        buttonBar.getChildren().addAll(confirmButton, cancelButton);

        editPersonalInfoContainer.getChildren().addAll(nameField, surnameField, phoneNumberField, addressField);

        root.setCenter(editPersonalInfoContainer);

        root.setBottom(buttonBar);

        Scene editPersonalInfoScene = new Scene(root, 700, 500);
        editPersonalInfoScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(editPersonalInfoScene);

        primaryStage.centerOnScreen();
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
