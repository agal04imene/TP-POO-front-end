package application;

public class BO {

    private Anamnese anamnese;
    private EpreuveClinique[] listeEpreuves;
    private Diagnostic diagnostic;
    private String projetTherapeutique;

    public BO (Anamnese anamnese) {
        this.anamnese = anamnese;
    }
    
    // Méthode d'affichage d'un bilan orthophonique
    public void afficherBO() {
    	System.out.println("Bilan Orthophonique :");
        System.out.println("----------------------");
        
        // Afficher l'anamnèse
        System.out.println("Anamnèse :");
        if (anamnese != null) {
            anamnese.afficherAnamnese();
        } else {
            System.out.println("Pas d'anamnèse disponible.");
        }
        System.out.println();
        
        // Afficher les épreuves cliniques
        /*System.out.println("Epreuves Cliniques :");
        if (listeEpreuves.length > 0) {
            for (EpreuveClinique epreuve : listeEpreuves) {
                epreuve.afficherEpreuveClinique();
            }
        } else {
            System.out.println("Pas d'épreuves cliniques disponibles.");
        }*/
        System.out.println();
        
        // Afficher le diagnostic
        System.out.println("Diagnostic :");
        if (diagnostic != null) {
            diagnostic.afficherDiagnostic();
        } else {
            System.out.println("Pas de diagnostic disponible.");
        }
        System.out.println();
        
        // Afficher le projet thérapeutique
        System.out.println("Projet Thérapeutique :");
        System.out.println(projetTherapeutique != null ? projetTherapeutique : "Pas de projet thérapeutique disponible.");
        System.out.println();
        
    }

    public Anamnese getAnamnese() {
        return anamnese;
    }
    public void setAnamnese(Anamnese anamnese) {
        this.anamnese = anamnese;
    }

    public EpreuveClinique[] getListeEpreuves() {
        return listeEpreuves;
    }

    public void setListeEpreuves(EpreuveClinique[] listeEpreuves) {
        this.listeEpreuves = listeEpreuves;
    }

    public Diagnostic getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(Diagnostic diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getProjetTherapeutique() {
        return projetTherapeutique;
    }

    public void setProjetTherapeutique(String projetTherapeutique) {
        this.projetTherapeutique = projetTherapeutique;
    }

}