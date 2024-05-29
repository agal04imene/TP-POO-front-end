package application;

import java.io.Serializable;
import java.util.*;

public class Anamnese  {
	

	Set <QstLibreAnamnese> listeQuestions;
	
	public Anamnese() {
        listeQuestions = new HashSet<>();
	}

    public void ajouterQuestion(QstLibreAnamnese question) {
        listeQuestions.add(question);
    }
	
    public void modifierQuestion(QstLibreAnamnese ancienneQuestion, QstLibreAnamnese nouvelleQuestion) {
        if (listeQuestions.contains(ancienneQuestion)) {
            listeQuestions.remove(ancienneQuestion);
            listeQuestions.add(nouvelleQuestion);
        } else {
            System.out.println("La question à modifier n'existe pas dans la liste.");
        }
    }

    public void supprimerQuestion(QstLibreAnamnese question) {
        listeQuestions.remove(question);
    }
    
    
    // Méthode d'affichage d'une anamnèse
    public void afficherAnamnese() {
        if (listeQuestions.isEmpty()) {
            System.out.println("Aucune question dans l'anamnèse.");
        } else {
            for (QstLibreAnamnese question : listeQuestions) {
                if (question instanceof QstLibreAnamneseEnfant) {
                    ((QstLibreAnamneseEnfant) question).afficherQuestion();
                } else if (question instanceof QstLibreAnamneseAdulte) {
                    ((QstLibreAnamneseAdulte) question).afficherQuestion();
                } else {
                    System.out.println("Type de question inconnu.");
                }
            }
        }
    }

	public Set<QstLibreAnamnese> getQuestions() {
		return listeQuestions;
	}

}