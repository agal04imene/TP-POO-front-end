package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChangePasswordPage {
    private Stage primaryStage;

    public ChangePasswordPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void load(Scene scene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #FFFFFF;"); // Light background color

        // Change Password Title
        Text title = new Text("Changer le Mot de Passe");
        title.getStyleClass().add("change-title");
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);
        
        // Change Password Container
        VBox changePasswordContainer = new VBox(10);
        changePasswordContainer.setAlignment(Pos.CENTER);
        changePasswordContainer.getStyleClass().add("change-container");

        // Old Password Field
        PasswordField oldPasswordField = new PasswordField();
        oldPasswordField.getStyleClass().add("text-field");
        oldPasswordField.setPromptText("Ancien Mot de Passe");

        // New Password Field
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.getStyleClass().add("text-field");
        newPasswordField.setPromptText("Nouveau Mot de Passe");

        // Confirm New Password Field
        PasswordField confirmNewPasswordField = new PasswordField();
        confirmNewPasswordField.getStyleClass().add("text-field");
        confirmNewPasswordField.setPromptText("Confirmer le Nouveau Mot de Passe");

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
        	AccountSettingsPage accountSettingsPage = new AccountSettingsPage(primaryStage);
        	accountSettingsPage.load(scene);
        });

        buttonBar.getChildren().addAll(confirmButton, cancelButton);

        changePasswordContainer.getChildren().addAll(oldPasswordField, newPasswordField, confirmNewPasswordField);

        root.setCenter(changePasswordContainer);
        
        root.setBottom(buttonBar);

        Scene changePasswordScene = new Scene(root, 500, 500);
        changePasswordScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(changePasswordScene);
        
        primaryStage.centerOnScreen();
    }
}
