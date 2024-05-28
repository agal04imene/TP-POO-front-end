package application;

public class BOEnfant extends BO {
    private String classeEtude ;
    private String numTelParents ;

    public BOEnfant(Anamnese anamnese, String numTel, String classeEtude) {
        super(anamnese);
        this.classeEtude = classeEtude;
        numTelParents = numTel ;
    }
    
    // Méthode d'affichage d'un bilan orthophonique
    public void afficherBOEnfant() {
    	super.afficherBO() ;
    	
    	// Afficher la classe d'étude 
    	System.out.print("Classe d'étude : ");
        System.out.println(classeEtude);
        System.out.println();
        
    	// Afficher le numéro de téléphone des parents
        System.out.print("Numéro de téléphone des parents : ");
        System.out.println(numTelParents != null ? numTelParents : "Pas de numéro de téléphone disponible.");
        System.out.println();
    	
    }

    public String getClasseEtude()
    {
        return(this.classeEtude);
    }
    public void setClasseEtude(String classeEtude)
    {
        this.classeEtude=classeEtude;
    }

}
