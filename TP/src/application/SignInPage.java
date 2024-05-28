package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashSet;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SignInPage {
    private Stage primaryStage;
    private HashSet<Orthophoniste> comptesUtilisateurs;
    private Label messageLabel;

    public SignInPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void load(Scene scene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Title
        Label title = new Label("Sign In");
        title.setFont(Font.font("Ubuntu", 20));
        title.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(title, Pos.CENTER);

        // Logo Image
        ImageView logoImageView = null;
        try {
            FileInputStream imagePath = new FileInputStream("logo.png");
            Image image = new Image(imagePath);
            logoImageView = new ImageView(image);
            logoImageView.setFitHeight(300); // Adjust the height as needed
            logoImageView.setPreserveRatio(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        VBox topContainer = new VBox(10);
        topContainer.setAlignment(Pos.CENTER);
        topContainer.getChildren().addAll(title, logoImageView);
        root.setTop(topContainer);

        // Form
        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(200);

        // Password field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(200);

        // Message Label
        messageLabel = new Label();
        messageLabel.setFont(Font.font("Ubuntu", 14));
        messageLabel.setStyle("-fx-text-fill: red;");

        // Sign in button
        Button signInButton = new Button("Sign In");
        signInButton.getStyleClass().add("button-style");

        signInButton.setOnAction(e -> {
            String addresseEmail = emailField.getText();
            String motDePass = passwordField.getText();

            if (addresseEmail.isEmpty() || motDePass.isEmpty()) {
                setMessage("Please fill all the information.", "red");
                return;
            }

            // réupérer la liste des comptes des orthophonistes
            comptesUtilisateurs = loadComptesOrthophonisteFromFile();

            boolean authenticated = false;
            // recherche du compte dans les comptes déjà crées
            for (Orthophoniste orthophoniste : comptesUtilisateurs) {
                if (orthophoniste.getAdresseEmail().equals(addresseEmail) && orthophoniste.getMotDePass().equals(motDePass)) {
                    orthophoniste.setConnecte(true);
                    authenticated = true;
                    setMessage("Connexion réussie !", "green");
                    navigateToMenuPrincipal(scene, orthophoniste);
                    break;
                }
            }

            if (!authenticated) {
                setMessage("Le compte n'existe pas. Veuillez réessayer.", "red");
            }
        });

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(signInButton);

        form.getChildren().addAll(emailField, passwordField, buttonBox, messageLabel);

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

        Scene signInScene = new Scene(root, 800, 700);
        signInScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(signInScene);
    }

    private void setMessage(String message, String color) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: " + color + ";");
    }

    private void navigateToMenuPrincipal(Scene scene, Orthophoniste orthophoniste) {
        MenuPrincipal menuPrincipal = new MenuPrincipal(primaryStage, orthophoniste);
        menuPrincipal.load(scene);
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
