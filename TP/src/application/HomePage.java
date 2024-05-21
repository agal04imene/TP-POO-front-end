package application;

import java.io.FileInputStream;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomePage {
    private Stage primaryStage;

    public HomePage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void load(Scene scene) {
        // Top Label
        Label topLabel = new Label("Application Cabinet Orthophoniste");
        topLabel.setId("topLabel");
        VBox topVBox = new VBox(topLabel);
        topVBox.setId("topHbox");
        topVBox.setAlignment(Pos.CENTER); // Center align the content
        Main.root.setTop(topVBox);

        // Center Content (Image and Text)
        try {
            FileInputStream imagePath = new FileInputStream("logo.png");
            Image image = new Image(imagePath);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(420);
            imageView.setFitWidth(400);

            Text centerText = new Text("OrthoNet : La gestion optimisée pour votre cabinet d'orthophonie!");
            centerText.setFont(Font.font("Ubuntu", 16));

            VBox centerVBox = new VBox(10);
            centerVBox.setId("centerVBox");

            // Testing EventHandler on an image
            imageView.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    scene.setCursor(Cursor.HAND);
                }
            });

            imageView.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    scene.setCursor(Cursor.DEFAULT);
                }
            });

            Button signInButton = new Button("Sign In");
            signInButton.getStyleClass().add("button-style");
            Button signUpButton = new Button("Sign Up");
            signUpButton.getStyleClass().add("button-style");

            HBox buttonsHBox = new HBox(20, signInButton, signUpButton);
            buttonsHBox.setId("buttonsBox");
            buttonsHBox.setAlignment(Pos.CENTER);
            buttonsHBox.setPadding(new Insets(20));

            signInButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent arg0) {
                    // Navigate to Sign In Page
                	SignInPage signInPage = new SignInPage(primaryStage);
                	signInPage.load(scene);
                }
            });

            signUpButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    // Navigate to Sign Up Page
                    SignUpPage signUpPage = new SignUpPage(primaryStage);
                    signUpPage.load(scene);
                }
            });

            centerVBox.getChildren().addAll(centerText, imageView, buttonsHBox);
            centerVBox.setAlignment(Pos.CENTER);
            centerVBox.setPadding(new Insets(20));
            Main.root.setCenter(centerVBox);

            Label bottomLabel = new Label("All rights reserved - ESI 2024");
            bottomLabel.setId("bottomLabel");
            HBox bottomHbox = new HBox(bottomLabel);
            bottomHbox.setId("bottomHbox");
            bottomHbox.setAlignment(Pos.CENTER);
            Main.root.setBottom(bottomHbox);

            // Remove left and right parts of BorderPane
            Main.root.setLeft(null);
            Main.root.setRight(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
