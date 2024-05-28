package application;

public class DossierEnfant extends Dossier {
    private String numeroTelPere;
    private String numeroTelMere;
    private String classEtude;

    public DossierEnfant(String nom, String prenom, int age, String adresse, String dateDeNaissance, String lieuDeNaissance, String numeroTelPere, String numeroTelMere, String classEtude) {
        super(nom, prenom, age, adresse, dateDeNaissance, lieuDeNaissance);
        this.numeroTelPere = numeroTelPere;
        this.numeroTelMere = numeroTelMere;
        this.classEtude = classEtude;
    }

    public String getNumeroTelPere() {
        return numeroTelPere;
    }

    public void setNumeroTelPere(String numeroTelPere) {
        this.numeroTelPere = numeroTelPere;
    }

    public String getNumeroTelMere() {
        return numeroTelMere;
    }

    public void setNumeroTelMere(String numeroTelMere) {
        this.numeroTelMere = numeroTelMere;
    }

    public String getClassEtude() {
        return classEtude;
    }

    public void setClassEtude(String classEtude) {
        this.classEtude = classEtude;
    }
}

