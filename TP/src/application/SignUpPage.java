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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SignUpPage {
    private Stage primaryStage;

    // les comptes des orthophonistes qui l'utilisent 
    private HashSet<Orthophoniste> comptesUtilisateurs = new HashSet<Orthophoniste>();
    private HashSet<Patient> listePatients = new HashSet<Patient>();

    public SignUpPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void load(Scene scene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Title
        Label title = new Label("Sign Up");
        title.setFont(Font.font("Ubuntu", 20));
        title.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);
        

        // Form
        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER);

        // Name field
        TextField nameField = new TextField();
        nameField.setPromptText("Nom");
        nameField.setMaxWidth(200);

        // Surname field
        TextField surnameField = new TextField();
        surnameField.setPromptText("Prénom");
        surnameField.setMaxWidth(200);

        // Phone number field
        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Numéro de téléphone");
        phoneNumberField.setMaxWidth(200);

        // Address field
        TextField addressField = new TextField();
        addressField.setPromptText("Adresse du cabinet");
        addressField.setMaxWidth(200);

        // Email field
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(200);

        // Password field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.setMaxWidth(200);

        // Confirm password field
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirmer le mot de passe");
        confirmPasswordField.setMaxWidth(200);

        // Sign up button
        Button signUpButton = new Button("Sign Up");
        signUpButton.getStyleClass().add("button-style");

        signUpButton.setOnAction(e -> {
            String nom = nameField.getText();
            String prenom = surnameField.getText();
            String numeroTelephone = phoneNumberField.getText();
            String adresse = addressField.getText();
            String adresseEmail = emailField.getText();
            String motDePass = passwordField.getText();
            String confirmMotDePass = confirmPasswordField.getText();

            if (nom.isEmpty() || prenom.isEmpty() || numeroTelephone.isEmpty() || adresse.isEmpty() || adresseEmail.isEmpty() || motDePass.isEmpty() || confirmMotDePass.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Incomplete Information");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the informations.");
                alert.showAndWait();
            } else if (!motDePass.equals(confirmMotDePass)) {
                confirmPasswordField.setStyle("-fx-border-color: red;");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Password Mismatch");
                alert.setHeaderText(null);
                alert.setContentText("The passwords entered do not match. Please check your password.");
                alert.showAndWait();
            } else if (!isStrongPassword(motDePass)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Weak Password");
                alert.setHeaderText(null);
                alert.setContentText("The password is weak. Please try again using capital letters, numbers, and special characters.");
                alert.showAndWait();
            } else {
                // Créer un nouvel orthophoniste avec les informations fournies
                Orthophoniste nouvelOrthophoniste = new Orthophoniste(nom, prenom, numeroTelephone, adresse, adresseEmail, motDePass,listePatients);
                
                comptesUtilisateurs=loadComptesOrthophonisteFromFile();
                if (comptesUtilisateurs==null)  comptesUtilisateurs=new HashSet<Orthophoniste>();
                // Ajouter le nouvel orthophoniste à la liste des comptes utilisateurs
                comptesUtilisateurs.add(nouvelOrthophoniste);
                
                // Sauvegarder les données des comptes des utilisateurs
                saveComptesOrthophonisteToFile(comptesUtilisateurs);
                

                // Show success alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Your account has been successfully created.");
                alert.showAndWait();
                
            }
        });

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(signUpButton);

        form.getChildren().addAll(nameField, surnameField, phoneNumberField, addressField, emailField, passwordField, confirmPasswordField, buttonBox);

        root.setCenter(form);

        // Back button
        Button backButton = new Button("Retour");
        backButton.getStyleClass().add("button-style");
        backButton.setOnAction(e -> {
            HomePage viewPatientRecordsPage = new HomePage(primaryStage);
            viewPatientRecordsPage.load(scene);
        });

        BorderPane.setAlignment(backButton, Pos.CENTER);
        root.setBottom(backButton);

        Scene signUpScene = new Scene(root, 800, 700);
        signUpScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(signUpScene);
    }

    private boolean isStrongPassword(String password) {
        boolean hasCapitalLetter = !password.equals(password.toLowerCase());
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecialCharacter = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
        return hasCapitalLetter && hasNumber && hasSpecialCharacter;
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
        File f = new File ("comptesOrthophoniste.ser");
        if (!f.exists()) {
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
