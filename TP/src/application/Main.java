package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
    static BorderPane root;

    // "Back" buttons are still not working. Update needed.
    
    @Override
    public void start(Stage primaryStage) {
        try {
            root = new BorderPane();
            Scene scene = new Scene(root, 800, 700);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Application Cabinet Orthophoniste");
            primaryStage.show();

            HomePage homePage = new HomePage(primaryStage);
            homePage.load(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
