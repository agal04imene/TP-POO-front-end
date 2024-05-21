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

public class SignInPage {
    private Stage primaryStage;

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
        root.setTop(title);

        // Form
        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER);

        
        // Email field
        TextField emailField = new TextField();
        emailField.setPromptText("Username");
        emailField.setMaxWidth(200);

        // Password field
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(200);

        // Sign in button
        Button signInButton = new Button("Sign In");
        signInButton.getStyleClass().add("button-style");

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(signInButton);
        
        // JUST TESTING THE MENU
        signInButton.setOnAction(e -> {
            MenuPrincipal menu = new MenuPrincipal(primaryStage);
            menu.load(scene);
        });

        form.getChildren().addAll(emailField, passwordField, buttonBox);

        root.setCenter(form);

        // Back button
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button-style");

        backButton.setOnAction(e -> {
            HomePage homePage = new HomePage(primaryStage);
            homePage.load(scene);
        });

        root.setBottom(backButton);

        Scene signInScene = new Scene(root, 800, 700);
        primaryStage.setScene(signInScene);
    }
}
