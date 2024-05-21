package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
        
        confirmButton.setOnAction(e -> {
            String oldPassword = oldPasswordField.getText();
            String newPassword = newPasswordField.getText();
            String confirmNewPassword = confirmNewPasswordField.getText();

            if (!isValidPassword(oldPassword)) {
                showAlert("Veuillez saisir un ancien mot de passe valide.");
            }
            if (!isValidPassword(newPassword)) {
                showAlert("Le nouveau mot de passe doit contenir au moins 8 caractères, une lettre majuscule, une lettre minuscule, une chiffre et un caractère spécial");
            } else if (!newPassword.equals(confirmNewPassword)) {
                showAlert("Les nouveaux mots de passe ne correspondent pas.");
            } else {
                // Include your back-end logic here !!
                showAlert("Mot de passe changé avec succès !");
                AccountSettingsPage accountSettingsPage = new AccountSettingsPage(primaryStage);
                accountSettingsPage.load(scene);
            }
        });

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
    
    private boolean isValidPassword(String password) {
        // Vérifie si le mot de passe respecte toutes les exigences
        return password.length() >= 8 &&             // Au moins 8 caractères
               password.matches(".*[A-Z].*") &&      // Contient au moins une lettre majuscule
               password.matches(".*[a-z].*") &&      // Contient au moins une lettre minuscule
               password.matches(".*\\d.*") &&        // Contient au moins un chiffre
               password.matches(".*[!@#$%^&*].*");  // Contient au moins un caractère spécial
    }


    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



}
