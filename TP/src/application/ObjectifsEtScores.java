package application;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import java.util.*;

public class ObjectifsEtScores extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Statistiques");

        // Creating the BarChart
        BarChart<String, Number> barChart = createBarChart();

        // Root layout
        BorderPane root = new BorderPane();
        root.setCenter(barChart);

        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); // Apply CSS
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private BarChart<String, Number> createBarChart() {
        // Defining the axes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Objectives");
        xAxis.setTickLabelRotation(360);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Score");

        // Creating the BarChart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Scores des objectifs du patient");

        // Data for the bar chart
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Scores attribués aux objectifs");

        // Get the list of objectives
        List<Objectif> objectives = generateRandomObjectifs(10);

        // Add data to the series
        for (Objectif obj : objectives) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(obj.getNom(), obj.getNote());
            dataSeries.getData().add(data);
        }

        barChart.getData().add(dataSeries);
        barChart.getStyleClass().add("custom-bar-chart");

        // Couleurs
        for (int i = 0; i < dataSeries.getData().size(); i++) {
            XYChart.Data<String, Number> data = dataSeries.getData().get(i);
            String color = getColorForIndex(i);
            data.getNode().setStyle("-fx-bar-fill: " + color + ";");
        }

        return barChart;
    }

    // Méthode de génération de couleurs
    private String getColorForIndex(int index) {
        String[] colors = {"#4CAF50", "#2196F3", "#FFC107", "#F44336"};
        return colors[index % colors.length];
    }

    // Méthode pour générer une liste aléatoire d'objectifs
    private List<Objectif> generateRandomObjectifs(int count) {
        List<Objectif> objectifs = new ArrayList<>();
        Random random = new Random();

        String[] names = {
                "Améliorer la \nprononciation",
                "Développer le \nvocabulaire",
                "Améliorer la \ncompréhension \nauditive",
                "Augmenter la \nfluidité verbale",
                "Améliorer la \nmémoire verbale",
                "Travailler sur \nla déglutition",
                "Développer les \ncompétences \nen lecture",
                "Améliorer la \ncapacité de \nnarration",
                "Améliorer la \ncommunication \nnon verbale",
                "Améliorer la \ncapacité à suivre \nune conversation"
        };
        String[] types = {"Court terme", "Moyen terme", "Long terme"};

        Set<String> usedNames = new HashSet<>();

        for (int i = 0; i < count; i++) {
            String name;
            do {
                name = names[random.nextInt(names.length)];
            } while (usedNames.contains(name) || name.isEmpty());

            usedNames.add(name);

            String type = types[random.nextInt(types.length)];
            int score = random.nextInt(5) + 1; // Score between 1 and 5
            Objectif objectif = new Objectif(name, type, score);
            objectifs.add(objectif);
        }

        return objectifs;
    }

}


