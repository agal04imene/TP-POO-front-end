package application;

public class DossierAdulte extends Dossier {
	
	private String diplome;
	private String profession;
	private String numeroTel;

	public DossierAdulte(String nom, String prenom, int age, String adresse, String dateDeNaissance, String lieuDeNaissance,String diplome,String prefession,String numeroTel ) {
		super( nom,prenom,  age,adresse, dateDeNaissance, lieuDeNaissance );
		this.diplome=diplome;
		this.profession=prefession;
		this.numeroTel=numeroTel;		
	}
	
	
	public String getDiplome() {
		return this.diplome;
	}
	
	public String getProfession () {
		return this.profession;
	}
	
	public String getNumeroTel() {
		return this.numeroTel;
	}
	
	
	public void setDiplome (String diplome) {
		this.diplome=diplome;
	}

	
	public void setProfession(String profession) {
		this.profession=profession;
	}
	
	public void setNumeroTel(String numeroTel) {
		this.numeroTel=numeroTel;
	}
}
