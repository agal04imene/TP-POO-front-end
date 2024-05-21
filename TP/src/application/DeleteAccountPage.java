package application;

//import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DeleteAccountPage {
    private Stage primaryStage;

    public DeleteAccountPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void load(Scene scene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #FFFFFF;"); // Light background color

        // Delete Account Title
        Text title = new Text("Supprimer le Compte");
        title.getStyleClass().add("delete-title");
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);
        
        // Warning Message
        Label warningLabel = new Label("    Cette opération est définitive et le compte n'est pas récupérable.\n"
                + "Vos données seront perdues. Tapez 'confirmation' ci-dessous si vous \n" + "\t\t   comprenez les risques et souhaitez procéder.");
        warningLabel.getStyleClass().add("warning-label");
        HBox warningBox = new HBox(20);
        warningBox.getChildren().add(warningLabel);
        HBox.setHgrow(warningLabel, Priority.ALWAYS);
        

        // Confirmation Text Field
        TextField confirmationField = new TextField();
        confirmationField.setPromptText("Tapez 'confirmation' ici");
        confirmationField.getStyleClass().add("delete-text-field");
        confirmationField.setPrefWidth(300);

        // Button Bar
        HBox buttonBar = new HBox(20);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.getStyleClass().add("delete-button-bar");

        // Confirm Button
        Button confirmButton = new Button("Confirmer");
        confirmButton.getStyleClass().add("delete-button");
        confirmButton.setDisable(true); // Initially disabled until correct confirmation is entered

        // Cancel Button
        Button cancelButton = new Button("Annuler");
        cancelButton.getStyleClass().add("delete-button");
        cancelButton.setOnAction(e -> {
            AccountSettingsPage accountSettingsPage = new AccountSettingsPage(primaryStage);
            accountSettingsPage.load(scene);
        });

        buttonBar.getChildren().addAll(confirmButton, cancelButton);

        // Verify Confirmation Text Field
        confirmationField.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.equals("confirmation")) {
                confirmButton.setDisable(false); // Enable confirm button if confirmation is correct
            } else {
                confirmButton.setDisable(true); // Disable confirm button if confirmation is incorrect
            }
        });

        VBox deleteAccountContainer = new VBox(20);
        deleteAccountContainer.setAlignment(Pos.CENTER);
        deleteAccountContainer.getChildren().addAll(warningBox, confirmationField, buttonBar);

        root.setCenter(deleteAccountContainer);

        confirmButton.setOnAction(e -> {
            // Show success message popup
        	// Add back-end logic of account deletion here !!
            showSuccessPopup();
        });

        Scene deleteAccountScene = new Scene(root, 500, 300);
        deleteAccountScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(deleteAccountScene);
        primaryStage.centerOnScreen();
    }

    private void showSuccessPopup() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Compte Supprimé");
        popupStage.setMinWidth(250);

        Label messageLabel = new Label("Compte supprimé avec succès !\n\n\n");
        messageLabel.getStyleClass().add("success-message");

        Button closeButton = new Button("Quitter");
        closeButton.getStyleClass().add("popup-button");
        closeButton.setOnAction(e -> {
            popupStage.close();
            primaryStage.close(); // Close the JavaFX application when popup is closed
        });

        VBox popupLayout = new VBox(20);
        popupLayout.setAlignment(Pos.CENTER);
        popupLayout.getChildren().addAll(messageLabel, closeButton);

        Scene popupScene = new Scene(popupLayout, 400, 250);
        popupScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }
}
