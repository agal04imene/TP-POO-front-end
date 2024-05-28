package application;

import java.io.*;
import java.util.HashSet;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
    private Orthophoniste orthophoniste;
    private HashSet<Orthophoniste> comptesUtilisateurs;

    public DeleteAccountPage(Stage primaryStage, Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.orthophoniste = orthophoniste;
    }

    public void load(Scene scene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #FFFFFF;");

        Text title = new Text("Supprimer le Compte");
        title.getStyleClass().add("delete-title");
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);

        Label warningLabel = new Label("    Cette opération est définitive et le compte n'est pas récupérable.\n"
                + "Vos données seront perdues. Saisissez votre adresse e-mail et mot de passe \n"
                + "\t\t   ci-dessous si vous comprenez les risques et souhaitez procéder.");
        warningLabel.getStyleClass().add("warning-label");
        HBox warningBox = new HBox(20);
        warningBox.getChildren().add(warningLabel);
        HBox.setHgrow(warningLabel, Priority.ALWAYS);

        TextField emailField = new TextField();
        emailField.setPromptText("Adresse E-mail");
        emailField.getStyleClass().add("delete-text-field");
        emailField.setPrefWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de Passe");
        passwordField.getStyleClass().add("delete-text-field");
        passwordField.setPrefWidth(300);

        HBox buttonBar = new HBox(20);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.getStyleClass().add("delete-button-bar");

        Button confirmButton = new Button("Confirmer");
        confirmButton.getStyleClass().add("delete-button");

        Button cancelButton = new Button("Annuler");
        cancelButton.getStyleClass().add("delete-button");
        cancelButton.setOnAction(e -> {
            AccountSettingsPage accountSettingsPage = new AccountSettingsPage(primaryStage, orthophoniste);
            accountSettingsPage.load(scene);
        });

        buttonBar.getChildren().addAll(confirmButton, cancelButton);

        VBox deleteAccountContainer = new VBox(20);
        deleteAccountContainer.setAlignment(Pos.CENTER);
        deleteAccountContainer.getChildren().addAll(warningBox, emailField, passwordField, buttonBar);

        root.setCenter(deleteAccountContainer);

        confirmButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            comptesUtilisateurs = loadComptesOrthophonisteFromFile();

            if (orthophoniste.getMotDePass().equals(password) && orthophoniste.getAdresseEmail().equals(email)) {
                comptesUtilisateurs.remove(orthophoniste);
                saveComptesOrthophonisteToFile(comptesUtilisateurs);
                if (!comptesUtilisateurs.contains(orthophoniste))
                showSuccessPopup();
            } else {
                showFailurePopup();
            }
        });

        Scene deleteAccountScene = new Scene(root, 500, 400);
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
            primaryStage.close();
        });

        VBox popupLayout = new VBox(20);
        popupLayout.setAlignment(Pos.CENTER);
        popupLayout.getChildren().addAll(messageLabel, closeButton);

        Scene popupScene = new Scene(popupLayout, 400, 250);
        popupScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    private void showFailurePopup() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Erreur de Suppression");
        popupStage.setMinWidth(250);

        Label messageLabel = new Label("Erreur : Adresse e-mail ou mot de passe incorrect !\n\n\n");
        messageLabel.getStyleClass().add("failure-message");

        Button closeButton = new Button("Fermer");
        closeButton.getStyleClass().add("popup-button");
        closeButton.setOnAction(e -> popupStage.close());

        VBox popupLayout = new VBox(20);
        popupLayout.setAlignment(Pos.CENTER);
        popupLayout.getChildren().addAll(messageLabel, closeButton);

        Scene popupScene = new Scene(popupLayout, 400, 250);
        popupScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        popupStage.setScene(popupScene);
        popupStage.showAndWait();
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
        try (FileOutputStream fileOut = new FileOutputStream("comptesOrthophoniste.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(comptesUtilisateurs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearFile(String filePath) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath, false)) {
            fileWriter.write("");
        }
    }
}
