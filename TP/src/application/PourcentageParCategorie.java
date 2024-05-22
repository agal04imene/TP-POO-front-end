package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PourcentageParCategorie extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Statistiques");

        // Récupération des pourcentages
        // add your back-end logic here !!
        double NeuroDev = 20.56 ;
        double Deglutition = 60.20 ;
        double Cognitif = 19.24 ;
        
        // Create PieChart data
        PieChart.Data NeuroDevData = new PieChart.Data("Troubles Neuro-dévloppemental", NeuroDev);
        PieChart.Data DegData = new PieChart.Data("Troubles De déglutition", Deglutition);
        PieChart.Data CognData = new PieChart.Data("Troubles Congnitif", Cognitif);

        // Create a PieChart
        PieChart pieChart = new PieChart();
        pieChart.getData().addAll(NeuroDevData, DegData, CognData);

        // Set PieChart title
        pieChart.setTitle("Pourcentage de patients par catégorie de troubles");

        // Customize PieChart colors (optional)
        NeuroDevData.getNode().setStyle("-fx-pie-color: #FF6666;"); // Red
        DegData.getNode().setStyle("-fx-pie-color: #66CC66;"); // Green
        CognData.getNode().setStyle("-fx-pie-color: #66B2FF;"); // Blue

        // Add PieChart to the layout
        StackPane root = new StackPane(pieChart);
        Scene StatsScene = new Scene(root, 600, 400);
        primaryStage.setScene(StatsScene);
        primaryStage.show();
    }

}
