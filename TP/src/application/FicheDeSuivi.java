package application;
import java.util.*;

public class FicheDeSuivi {
	private ArrayList<Objectif> listeObjectifs ;
	private boolean atteint ;
	
	public int numFiche ;
	
	public FicheDeSuivi() {
		listeObjectifs = new ArrayList<Objectif>();
		this.atteint = false ; // les objectifs ne sont pas atteints initialement
	}
	
	// Setter pour modifier l'attribut "atteint"
	public void setAtteint(boolean atteint) {
		this.atteint = atteint ;
	}
	
	// Getter pour récupérer l'attribut "atteint"
		public boolean isAtteint() {
	        return atteint;
	    }
		
		
	// Méthode pour mettre "atteint" à "vrai" lorsque tout les objectifs sont atteints
	public void objectifsAtteints() {
		for (Objectif obj : listeObjectifs) {
			if (obj.getNote() < 5) {
				// Si la note d'un objectif est inférieure à 5, mettre "atteint" à faux
	            atteint = false ;
	            return; // quitter la boucle
	        }
	    }
	    // Si la note de tout les objectifs est à 5, mettre "atteint" à vrai
	    atteint = true ;
	}
		
	// Méthode d'ajout d'un objectif à la fiche de suivi
	public void ajouterObjectif(Objectif objectif) throws ObjectifDejaExistantException {
		// Si l'objectif existe déja dans la fiche, une exception est lancée
        if (listeObjectifs.contains(objectif)) {
            throw new ObjectifDejaExistantException("L'objectif existe déjà.");
        }
        listeObjectifs.add(objectif);
    }

	// Méthode de suppression d'un objectif de la fiche de suivi
    public void supprimerObjectif(Objectif objectif) throws ObjectifInexistantException {
    	// Si l'objectif n'existe pas dans la fiche, une exception est lancée
        if (!listeObjectifs.contains(objectif)) {
            throw new ObjectifInexistantException("L'objectif n'existe pas.");
        }
        listeObjectifs.remove(objectif);
    }
	
	
    // Méthode pour afficher tous les objectifs dans la fiche de suivi
    public void afficherListeObjectifs() {
        if (listeObjectifs.isEmpty()) {
            System.out.println("Il n'y a aucun objectif dans la fiche de suivi.");
            return;
        }

        System.out.println("Voici la liste des objectifs :");
        int numero = 1;
        for (Objectif obj : listeObjectifs) {
            System.out.println("Objectif " + numero + " : " + obj.afficherObjectif()); 
            numero++; // Incrément du compteur
        }
    }
		
}
