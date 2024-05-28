package application;

public class BOAdulte extends BO {

    private String diplome;
    private String profession;
    private String numTelPersonnel ;

    public BOAdulte(Anamnese anamnese, String numTel, String diplome, String profession) {
        super(anamnese);
        this.diplome = diplome;
        this.profession = profession;
        numTelPersonnel = numTel ;
    }

    // Méthode d'affichage d'un bilan orthophonique d'un adulte
    public void afficherBOAdulte() {
    	super.afficherBO() ;
    	
    	// Afficher le diplome et la profession
    	System.out.print("Le patient a un diplome en : ");
        System.out.println(diplome);
        System.out.println();
        
        System.out.print("Sa profession est : ");
        System.out.println(profession);
        System.out.println();
    	
    	// Afficher le numéro de téléphone personnel
        System.out.print("Numéro de téléphone personnel : ");
        System.out.println(numTelPersonnel != null ? numTelPersonnel : "Pas de numéro de téléphone disponible.");
        System.out.println();
    	
    }
    
    public String getDiplome() {
        return(this.diplome);
    }

    public String getProfession() {
        return(this.profession);
    }

    public void setDiplome(String diplome) {
        this.diplome=diplome;
    }

    public void setProfession(String profission) {
        this.profession =profission;
    }

}
