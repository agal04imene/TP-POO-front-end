package application;
public class AtelierDeGroupe extends RendezVous {
    private String Thematique;
    private int ListePatients[];  // liste qui contient les numéros des dossiers des patients
    private final String duree ="1h";
    
	public AtelierDeGroupe(String date,String heure,String thematique,int ListePatients[]) {
		super(date,heure);
		this.Thematique=thematique;
		this.ListePatients=ListePatients;
	}

	// Les getters 
	
	public String getThematique() {
		return this.Thematique;
	}
	
	public int[] getListePatients() {
		return this.ListePatients;
	}
	
	public String getDuree() {
		return this.duree;
	}
	
	// Les setters 
	
	public void setThematique(String Thematique) {
		this.Thematique=Thematique;
	}
	
	public void setListePatients(int[] ListePatients) {
		this.ListePatients=ListePatients;
	}
	
	public void AfficherRendezVous() {
		super.AfficherRendezVous();
		System.out.println(" La thématique de l'atelier de groupe est : "+this.Thematique);
		System.out.println(" La durée de l'atelier de groupe est : 1h");
		System.out.println("La liste des patients : \n");
		for (int i=0;i<this.ListePatients.length;i++) {
		System.out.println("Le numéro du patient "+(i+1)+" : "+this.ListePatients[i]);	
		}
	}
}