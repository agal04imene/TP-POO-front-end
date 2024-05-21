package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AccountSettingsPage {
    private Stage primaryStage;

    public AccountSettingsPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void load(Scene scene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Titre
        Text title = new Text("Paramètres du Compte");
        title.setFont(Font.font("Ubuntu", 28));
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);

        // Options du Menu
        VBox menuOptions = new VBox(20);
        menuOptions.setAlignment(Pos.CENTER);
        menuOptions.setPadding(new Insets(20));

        // Option : Changer le mot de passe
        Button changePasswordButton = createMenuButton("Changer le mot de passe");
        changePasswordButton.setOnAction(e -> {
            // Naviguer vers la Page de Changement de Mot de Passe
            ChangePasswordPage changePasswordPage = new ChangePasswordPage(primaryStage);
            changePasswordPage.load(scene);
        });

        // Option : Changer l'adresse e-mail
        Button changeEmailButton = createMenuButton("Changer l'adresse e-mail");
        changeEmailButton.setOnAction(e -> {
            // Naviguer vers la Page de Changement d'Adresse E-mail
            ChangeEmailPage changeEmailPage = new ChangeEmailPage(primaryStage);
            changeEmailPage.load(scene);
        });

        // Option : Modifier les informations personnelles
        Button editPersonalInfoButton = createMenuButton("Modifier les informations personnelles");
        editPersonalInfoButton.setOnAction(e -> {
            // Naviguer vers la Page de Modification des Informations Personnelles
            EditPersonalInfoPage editPersonalInfoPage = new EditPersonalInfoPage(primaryStage);
            editPersonalInfoPage.load(scene);
        });

        menuOptions.getChildren().addAll(
                changePasswordButton,
                changeEmailButton,
                editPersonalInfoButton
        );

        root.setCenter(menuOptions);

        // Bouton Retour
        Button backButton = new Button("Retour");
        backButton.getStyleClass().add("button-style");
        backButton.setOnAction(e -> {
            MenuPrincipal menuPage = new MenuPrincipal(primaryStage);
            menuPage.load(scene);
        });
        root.setBottom(backButton);

        Scene accountSettingsScene = new Scene(root, 700, 600);
        accountSettingsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(accountSettingsScene);
        
        primaryStage.centerOnScreen();
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("menu-button");
        button.setPrefWidth(500);
        button.setPrefHeight(60);
        return button;
    }
}
