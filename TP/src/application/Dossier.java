package application;
import java.io.Serializable;
import java.util.* ;

public class Dossier implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1972846372103195672L;

	// Numero du dossier
    public static int numDossier ;
    
    // Informations du patients
    private String nom ;
    private String prenom ;
    private int age ; // pour différencier entre adulte (+18) et enfant (-18)
    private String adresse ;
    private String dateDeNaissance ;
    private String lieuDeNaissance ;
    // Dossier médicale du patient
    private ArrayList<BO> listeBOs ;
    private ArrayList<FicheDeSuivi> historiqueFichesSuivi ;
     private ArrayList<RendezVous> listeRendezVous ;
    
    public Dossier(String nom, String prenom, int age, String adresse, String dateDeNaissance, String lieuDeNaissance ) {
    	numDossier++ ; // le numéro du dossier s'incrémente chaque fois qu'on ajoute un patient
    	this.nom = nom ;
    	this.prenom = prenom ;
    	this.age = age ;
    	this.adresse = adresse ;
    	this.dateDeNaissance = dateDeNaissance ;
    	this.lieuDeNaissance = lieuDeNaissance ;
    	
    	listeBOs = new ArrayList<BO>() ;
    	historiqueFichesSuivi = new ArrayList<FicheDeSuivi>() ;
        listeRendezVous = new ArrayList<RendezVous>() ;
    }
    
    
    // Méthode d'affichage de toutes les informations du dossier d'un patient
    // THIS METHOD IS SO BIG AND NEEDS MILLIONS OF OTHER METHODS UGH
    public void afficherDossier() {
        System.out.println("Numéro du dossier: " + numDossier);
        
        System.out.println("\nInformations générales du patient :");
        System.out.println("Nom: " + nom);
        System.out.println("Prénom: " + prenom);
        System.out.println("Âge: " + age);
        System.out.println("Adresse: " + adresse);
        System.out.println("Date de naissance: " + dateDeNaissance);
        System.out.println("Lieu de naissance: " + lieuDeNaissance);

        System.out.println("\nListe des bilans orthophoniques :");
        for (BO bo : listeBOs) {
            if (age >= 18 && bo instanceof BOAdulte) {
                ((BOAdulte) bo).afficherBOAdulte();
            } else if (age < 18 && bo instanceof BOEnfant) {
                ((BOEnfant) bo).afficherBOEnfant();
            }
        }

        System.out.println("\nHistorique des fiches de suivi :");
        for (FicheDeSuivi fiche : historiqueFichesSuivi) {
            fiche.afficherListeObjectifs();
        }
    }
    
    
    // Getters & Setters
    public String getNom() {
    	return nom ;
    }
    
    public String getPrenom() {
    	return prenom ;
    }
    
    public int getAge() {
    	return age ;
    }
    
    public ArrayList<BO> getListeBOs(){
    	return this.listeBOs;
    }
    
    public void setListeBOs (ArrayList<BO> listeBO) {
    	this.listeBOs=listeBO;
    }
    
    public static int getNumDossier () {
    	return numDossier;
    }
    public  ArrayList<FicheDeSuivi>  getListeFichesDeSuivi(){
    	return this.historiqueFichesSuivi;
    }
}