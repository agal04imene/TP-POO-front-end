package application;

import java.io.Serializable;

public class QstLibreAnamneseEnfant extends QstLibreAnamnese  {
	

	
	private TypeQstAnamneseEnfant categorie;
	
	public QstLibreAnamneseEnfant(String enonce, String categorie) {
		super(enonce);
		switch (categorie) {
		case "Antecedents Familiaux" : this.categorie = TypeQstAnamneseEnfant.AntecedentsFamiliaux ; break ;
		case "Caractère et comportement" : this.categorie = TypeQstAnamneseEnfant.CaractereComportement ; break ;
		case "Conditions natales" : this.categorie = TypeQstAnamneseEnfant.ConditionsNatales ; break ;
		case "Développement langagier" : this.categorie = TypeQstAnamneseEnfant.DeveloppementLangagier ; break ;
		case "Développement psychomoteur" : this.categorie = TypeQstAnamneseEnfant.DeveloppementPsychomoteur ; break ;
		case "Dynamique familiale" : this.categorie = TypeQstAnamneseEnfant.DynamiqueFamiliale ; break ;
		case "Structure familiale" : this.categorie = TypeQstAnamneseEnfant.StructureFamiliale ; break ;
		default : throw new IllegalArgumentException("Catégorie de question inconnue : " + categorie) ;
		}
	}
	
	public String getCategorie() {
		String cat = null ;
		switch (this.categorie) {
			case AntecedentsFamiliaux : cat = "Antecedents Familiaux" ; break ;
			case CaractereComportement : cat = "Caractère et Comportement" ; break ;
			case ConditionsNatales : cat = "Conditions Natales" ; break ;
			case DeveloppementLangagier : cat = "Développement Langagier" ; break ;
			case DeveloppementPsychomoteur : cat = "Développement Psychomoteur" ; break ;
			case DynamiqueFamiliale : cat = "Dynamique Familiale" ; break ;
			case StructureFamiliale : cat = "Structure Familiale" ; break ;
		} ;
		return cat;
	}
	
	public void setCategorie(String cat) {
		switch (cat) {
		case "Antecedents familiaux" : this.categorie = TypeQstAnamneseEnfant.AntecedentsFamiliaux ; break ;
		case "Caractère et comportement" : this.categorie = TypeQstAnamneseEnfant.CaractereComportement ; break ;
		case "Conditions natales" : this.categorie = TypeQstAnamneseEnfant.ConditionsNatales ; break ;
		case "Développement langagier" : this.categorie = TypeQstAnamneseEnfant.DeveloppementLangagier ; break ;
		case "Développement psychomoteur" : this.categorie = TypeQstAnamneseEnfant.DeveloppementPsychomoteur ; break ;
		case "Dynamique familiale" : this.categorie = TypeQstAnamneseEnfant.DynamiqueFamiliale ; break ;
		case "Structure familiale" : this.categorie = TypeQstAnamneseEnfant.StructureFamiliale ; break ;
		default : throw new IllegalArgumentException("Catégorie de question inconnue : " + categorie) ;
		}
	}

	// Méthode d'affichage d'une question d'anamnèse (Enfant)
		public void afficherQuestion() {
			System.out.print("Enoncé : ") ;
			System.out.println(super.enonce) ;
			System.out.print("Catégorie : ") ;
			System.out.println(categorie) ;
		}
}