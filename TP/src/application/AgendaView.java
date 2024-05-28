package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.Label;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AgendaView {
    private Stage primaryStage;
    private VBox view;
    private List<RendezVous> rendezVousList;
    private VBox calendarView;
    private DatePicker datePicker;
    private TextArea rendezVousDetails;

    public AgendaView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        view = new VBox(10);
        view.setPadding(new Insets(10));
        rendezVousList = new ArrayList<>();

        // Title
        Text title = new Text("Agenda");
        title.setFont(Font.font("Ubuntu", 24));
        BorderPane.setAlignment(title, Pos.CENTER);

        // Date Picker
        datePicker = new DatePicker(LocalDate.now());
        datePicker.setOnAction(e -> updateCalendarView());

        // Rendez-vous Details
        rendezVousDetails = new TextArea();
        rendezVousDetails.setPromptText("Enter rendez-vous details...");

        // Add Rendez-vous Button
        Button addRendezVousButton = new Button("Add Rendez-vous");
        addRendezVousButton.setOnAction(e -> addRendezVous());

        HBox controls = new HBox(10, datePicker, addRendezVousButton);
        controls.setAlignment(Pos.CENTER);

        // Calendar View
        calendarView = new VBox(10);
        calendarView.setPadding(new Insets(10));
        calendarView.setAlignment(Pos.CENTER);

        view.getChildren().addAll(title, controls, rendezVousDetails, calendarView);
        updateCalendarView();
    }

    public void load(Scene scene) {
        Scene agendaScene = new Scene(view, 800, 700);
        primaryStage.setScene(agendaScene);
    }

    private void addRendezVous() {
        LocalDate date = datePicker.getValue();
        String details = rendezVousDetails.getText();
        if (details.isEmpty()) {
            showAlert("No details", "Please enter rendez-vous details.");
            return;
        }
        rendezVousList.add(new RendezVous(date, details));
        rendezVousDetails.clear();
        updateCalendarView();
    }

    private void updateCalendarView() {
        calendarView.getChildren().clear();
        LocalDate selectedDate = datePicker.getValue();
        for (RendezVous rendezVous : rendezVousList) {
            if (rendezVous.getDate().equals(selectedDate)) {
                Label rendezVousLabel = new Label(rendezVous.getDetails());
              //  calendarView.getChildren().add(rendezVousLabel);
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private class RendezVous {
        private LocalDate date;
        private String details;

        public RendezVous(LocalDate date, String details) {
            this.date = date;
            this.details = details;
        }

        public LocalDate getDate() {
            return date;
        }

        public String getDetails() {
            return details;
        }
    }
}
