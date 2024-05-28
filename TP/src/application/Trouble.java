package application;

public class Trouble {

	String nom ;
	CatTrouble categorie ;
	public String categorieStr;
	
	public Trouble(String nom, String categorie) {
		this.nom = nom ;
		this.categorieStr=categorie;
		switch (categorie) {
		case "Trouble de déglutition" : this.categorie = CatTrouble.Deglutition ; break ;
		case "Trouble neuro-dévloppemental" : this.categorie = CatTrouble.NeuroDevloppemental ; break ;
		case "Trouble cognitif" : this.categorie = CatTrouble.Cognitif ; break ;
		default : throw new IllegalArgumentException("Catégorie de trouble inconnue : " + categorie);
		}
	}

	// Méthode d'affichage d'un trouble
    public void afficherTrouble() {
        System.out.println("Nom du trouble : " + nom);
        System.out.println("Catégorie : " + categorie);
    }

}
