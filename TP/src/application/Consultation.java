package application;
public class Consultation extends RendezVous {
    private String NomPatient;
    private String PrenomPatient;
    private int AgePatient;
    private final String duree;
    
    // Le constructeur de la classe Consultation
	public Consultation(String date, String heure,int agePatient) {
		super(date,heure);
		this.AgePatient=agePatient;
		
		// Determinons la duree de la consultation d'aprés l'age du patient 
		// (instead of doing two seperate classes "ConsultationAdulte" & "ConsultationEnfant")
		if (agePatient>=18) {
			this.duree="1h30";
		} else {
			this.duree="2h30";
		}
	 }
	
	// Les getters de la classe Consultation 
	
	public String getNomPatient () {
		return this.NomPatient;
	}
	
	public String getPrenomPatient () {
		return this.PrenomPatient;
	}

	public int AgePatient () {
		return this.AgePatient;
	}
	
	public String getDuree() {
		return this.duree;
	}
	
	// Les setters de la classe Consultation
	
	public void setNomPatient(String NomPatient) {
		this.NomPatient=NomPatient;
	}
	
	public void setPrenomPatient(String PrenomPatient) {
		this.PrenomPatient=PrenomPatient;
	}
	
	public void setAgePatient(int AgePatient) {
		this.AgePatient=AgePatient;
	}
	
	public void AfficherRendezVous() {
		super.AfficherRendezVous();
		System.out.println("Le nom du patient : "+this.NomPatient);
		System.out.println("Le prénom du patient : "+this.PrenomPatient);
		System.out.println("L'age du patient : "+this.AgePatient);
		System.out.println("La durée de la consultation : "+this.duree);
	}
	
	
}

