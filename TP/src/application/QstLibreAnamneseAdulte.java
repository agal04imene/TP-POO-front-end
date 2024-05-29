package application;

import java.io.Serializable;

public class QstLibreAnamneseAdulte extends QstLibreAnamnese{
	


    private TypeQstAnamneseAdulte categorie;

    public QstLibreAnamneseAdulte(String enonce, String categorie) {
        super(enonce);
        setCategorie(categorie);
    }

    public String getCategorie() {
        String cat = null;
        switch (this.categorie) {
            case HistoireMaladie:
                cat = "Histoire de la maladie";
                break;
            case SuiviMedical:
                cat = "Suivi médical";
                break;
        }
        return cat;
    }

    public void setCategorie(String cat) {
        if (cat == null) {
            throw new IllegalArgumentException("Catégorie de question ne peut pas être null.");
        }
        switch (cat) {
            case "Histoire de la maladie":
                this.categorie = TypeQstAnamneseAdulte.HistoireMaladie;
                break;
            case "Suivi médical":
                this.categorie = TypeQstAnamneseAdulte.SuiviMedical;
                break;
            default:
                throw new IllegalArgumentException("Catégorie de question inconnue : " + cat);
        }
    }

    // Méthode d'affichage d'une question d'anamnèse (Adulte)
    public void afficherQuestion() {
        System.out.print("Enoncé : ");
        System.out.println(super.enonce);
        System.out.print("Catégorie : ");
        System.out.println(getCategorie());
    }
}
