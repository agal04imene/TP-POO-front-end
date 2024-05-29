package application;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AgendaView {
    private Stage primaryStage;
    private Patient patient;
    private Orthophoniste orthophoniste;

    public AgendaView(Stage primaryStage, Patient patient, Orthophoniste orthophoniste) {
        this.primaryStage = primaryStage;
        this.patient = patient;
        this.orthophoniste = orthophoniste;
    }

    public void load(Scene previousScene) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Title
        Text title = new Text(patient.getDossierPatient().getNom() + " " + patient.getDossierPatient().getPrenom());
        title.setFont(Font.font("Ubuntu", 30));
        title.setFill(Color.web("#00215E"));
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);

        // Content
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        ArrayList<RendezVous> listeRendezVous = patient.getDossierPatient().getListeRendezVous();
        int rendezVousCount = 1;
        for (RendezVous rendezVous : listeRendezVous) {
            Label dateLabel = createInfoLabel("Date du rendez-vous (" + rendezVousCount + "): " + rendezVous.getDate());
            Label heureLabel = createInfoLabel("Heure du rendez-vous (" + rendezVousCount + "): " + rendezVous.getHeure());
            Label typeLabel = createInfoLabel("Type du rendez-vous (" + rendezVousCount + "): " + rendezVous.getTypeStr());
            Label lineLabel = createInfoLabel("-------------------------------------------------------------------------");
            rendezVousCount++;

            content.getChildren().addAll(dateLabel, heureLabel,typeLabel,lineLabel); // Add labels to the content VBox
        }

        // Back button
        Button backButton = new Button("Retour");
        backButton.getStyleClass().add("button-style");
        backButton.setOnAction(e -> {
            ViewPatientRecordsPage viewPatientRecordsPage = new ViewPatientRecordsPage(primaryStage, orthophoniste);
            viewPatientRecordsPage.load(previousScene);
        });

        BorderPane.setAlignment(backButton, Pos.CENTER);
        root.setBottom(backButton);

        root.setCenter(content);

        Scene scene = new Scene(root, 800, 700);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    private Label createInfoLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("info-label");
        return label;
    }
}
