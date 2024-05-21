package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SignUpPage {
    private Stage primaryStage;

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
        TextField passwordField = new TextField();
        passwordField.setPromptText("Mot de pass");
        passwordField.setMaxWidth(200);

        // Confirm password field
        TextField confirmPasswordField = new TextField();
        confirmPasswordField.setPromptText("Confirmer le mot de pass");
        confirmPasswordField.setMaxWidth(200);

        // Sign up button
        Button signUpButton = new Button("Sign Up");
        signUpButton.getStyleClass().add("button-style");
        
        signUpButton.setOnAction(e -> {
        	// add your back-end logic here !!
        	// in the end, show an alert then go back to the sign-in page
        	SignInPage signInPage = new SignInPage(primaryStage);
        	signInPage.load(scene);
        });
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(signUpButton);

        form.getChildren().addAll(nameField, surnameField, phoneNumberField, addressField, emailField, passwordField, confirmPasswordField, buttonBox);

        root.setCenter(form);

        // Back button
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button-style");

        backButton.setOnAction(e -> {
            HomePage homePage = new HomePage(primaryStage);
            homePage.load(scene);
        });

        root.setBottom(backButton);

        Scene signUpScene = new Scene(root, 800, 700);
        primaryStage.setScene(signUpScene);
    }
}
