// La classe qui génère les objectifs à ajouter aux listes de suivi
package application;

public class Objectif {
	String nom ;
	TypeObj type ;
	int note ;
	
	
	public Objectif(String nom, String type, int note) {
		this.nom = nom ;
		this.note = 1 ; //La note est initialisée à 1 lorsque l'objectif est crée
		switch (type) {
			case "Court terme" : this.type = TypeObj.CourtTerme ; break ;
			case "Moyen terme" : this.type = TypeObj.MoyenTerme ; break ;
			case "Long terme" : this.type = TypeObj.LongTerme ; break ;
			default : throw new IllegalArgumentException("Type d'objectif inconnu : " + type);
		}
	}
	
	
	// Getter pour récupérer le nom de l'objectif
		public String getNom() {
	        return nom;
	    }
	
	// Getter pour récupérer la note de l'objectif
	public int getNote() {
        return note;
    }
	
	
	// Setter pour modifier la note de l'objectif
	public void setNote (int noteObj) throws noteObjInacceptableException {
		if (noteObj > 5 || noteObj < 1) 
			throw new noteObjInacceptableException("La note doit etre comprise entre 1 et 5") ;
		this.note = noteObj ;
	}

	
	// Méthode pour afficher les détailles de l'objectif
    public String afficherObjectif() {
        return "Nom : " + nom + ", Type : " + type +", Note : " + note;
    }
	
}
