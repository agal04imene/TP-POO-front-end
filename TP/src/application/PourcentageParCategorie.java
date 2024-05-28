package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PourcentageParCategorie extends Application {
    Orthophoniste orthophoniste;
    
    
    public PourcentageParCategorie (Orthophoniste orthophoniste) {
    	this.orthophoniste=orthophoniste;
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Statistiques");
        
         HashSet<Patient> listePatients = orthophoniste.getListePatients()
;         
         int NeuroDev = 0;
         int Deglutition =0;
         int Cognitif = 0;
         
        for( CatTrouble catTrouble : CatTrouble.values()){
        	

        
        // Récupération des pourcentages
        Iterator<Patient> it_Patients = listePatients.iterator();			
		while(it_Patients.hasNext()) {
			
			Patient p = it_Patients.next();
			ArrayList<BO> listeBOs = p.getDossierPatient().getListeBOs(); // La liste des BOs du patient courrant
			
			Iterator<BO> it_BOs = listeBOs.iterator();
			while (it_BOs.hasNext()) {
				BO bo = it_BOs.next();				
				ArrayList<Trouble> listeTroubles= bo.getDiagnostic().listeTroubles;
				
				Iterator<Trouble> it_Troubles = listeTroubles.iterator();
				while (it_Troubles.hasNext()) {
					Trouble trouble = it_Troubles.next();
					if (trouble.nom.equalsIgnoreCase("Troubles Neuro-dévloppemental") ) {
						 NeuroDev++;						
					}
					if (trouble.nom.equalsIgnoreCase("Troubles De déglutition") ) {
						Deglutition++;						
					}
					if (trouble.nom.equalsIgnoreCase("Troubles Cognitif") ) {
						Cognitif++;						
					}
				}
				
			}
		}
        }
        
        
        // Create PieChart data
        PieChart.Data NeuroDevData = new PieChart.Data("Troubles Neuro-dévloppemental", NeuroDev);
        PieChart.Data DegData = new PieChart.Data("Troubles De déglutition", Deglutition);
        PieChart.Data CognData = new PieChart.Data("Troubles Cognitif", Cognitif);

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