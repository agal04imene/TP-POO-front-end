package application;

import java.util.ArrayList;
import java.util.Arrays;

public class Questionnaire  extends Test {    //extends Test : add it when u push 
    private ArrayList<Question> listeQuestion ; 
    private ArrayList<QCM> listeQcm;  
    private ArrayList<QCU> listeQcu;
    private ArrayList<QuestionLibre> listeQstLibre;


    public Questionnaire(String nom, int capacite) {
        super(nom, capacite);
        this.listeQuestion = new ArrayList<>();
    }

    public ArrayList<Question> getListeQuestion() {
        return listeQuestion;
    }

    public void setListeQuestion(ArrayList<Question> listeQuestion) {
        this.listeQuestion = listeQuestion;
    }

    public void addQuestion(Question qst) {
        listeQuestion.add(qst);
    }

    
	@Override
    public float calculeScoreTotal() {
        int totalScore = 0;
        for (Question question : listeQuestion) {
            totalScore += question.getScore();
        }
        return totalScore;
    }

    public int rechercherQuestion(String enonce) {
		int pos = 0;
		for (Question ex : listeQuestion) {
			if (ex.equals(ex) == true) {
				break;
			}
			pos++;
		}
		return (pos);
	}

    public void supprimerQuestion(String enonce) {
		int p=rechercherQuestion(enonce);
		listeQuestion.remove(p);
	}
    
    public void ajouterQcm(QCM nouveauQcm) {
        if (!listeQcm.contains(nouveauQcm)) {
            listeQcm.add(nouveauQcm);
        } else {
            System.out.println("Le test que vous voulez ajouter existe déjà.");
        }
    }
    
    public void ajouterQcu(QCU nouveauQcu) {
        if (!listeQcu.contains(nouveauQcu)) {
            listeQcu.add(nouveauQcu);
        } else {
            System.out.println("Le test que vous voulez ajouter existe déjà.");
        }
    }
    
    public void ajouterQstLibre(QuestionLibre nouveauQstLibre) {
        if (!listeQstLibre.contains(nouveauQstLibre)) {
            listeQstLibre.add(nouveauQstLibre);
        } else {
            System.out.println("Le test que vous voulez ajouter existe déjà.");
        }
    }
    
    public void supprimerQcm(QCM ancienQcm) {
        if (listeQcm.contains(ancienQcm)) {
            listeQcm.remove(ancienQcm);
        } else {
            System.out.println("Le test que vous voulez supprimer n'existe pas.");
        }
    }
    
    public void supprimerQcu(QCU ancienQcu) {
        if (listeQcu.contains(ancienQcu)) {
            listeQcu.remove(ancienQcu);
            System.out.println("Le test QCU a été supprimé avec succès.");
        } else {
            System.out.println("Le test QCU que vous voulez supprimer n'existe pas.");
        }
    }
    
    public void supprimerQuestionLibre(QuestionLibre ancienneQuestionLibre) {
        if (listeQstLibre.contains(ancienneQuestionLibre)) {
            listeQstLibre.remove(ancienneQuestionLibre);
            System.out.println("La question libre a été supprimée avec succès.");
        } else {
            System.out.println("La question libre que vous voulez supprimer n'existe pas.");
        }
    }
    
    
    public void modifierQcm(QCM ancienQcm, QCM nouveauQcm) {
    	if (listeQcm.contains(ancienQcm)) {
    		listeQcm.remove(ancienQcm);
    		listeQcm.add(nouveauQcm);
        } else {
            System.out.println("La question à modifier n'existe pas dans la liste.");
        }
    }
    
    public void modifierQcu(QCU ancienQcu, QCU nouveauQcu) {
    	if (listeQcu.contains(ancienQcu)) {
    		listeQcu.remove(ancienQcu);
    		listeQcu.add(nouveauQcu);
        } else {
            System.out.println("La question à modifier n'existe pas dans la liste.");
        }
    }
    
    public void modifierTQcm(QuestionLibre ancienQstLibre, QuestionLibre nouveauQstLibre) {
    	if (listeQstLibre.contains(ancienQstLibre)) {
    		listeQstLibre.remove(ancienQstLibre);
    		listeQstLibre.add(nouveauQstLibre);
        } else {
            System.out.println("La question à modifier n'existe pas dans la liste.");
        }
    }


    // Getters and setters for listeQcm
    public ArrayList<QCM> getListeQcm() {
        return listeQcm;
    }

    public void setListeQcm(ArrayList<QCM> listeQcm) {
        this.listeQcm = listeQcm;
    }

    // Getters and setters for listeQcu
    public ArrayList<QCU> getListeQcu() {
        return listeQcu;
    }

    public void setListeQcu(ArrayList<QCU> listeQcu) {
        this.listeQcu = listeQcu;
    }

    // Getters and setters for listeQstLibre
    public ArrayList<QuestionLibre> getListeQstLibre() {
        return listeQstLibre;
    }

    public void setListeQstLibre(ArrayList<QuestionLibre> listeQstLibre) {
        this.listeQstLibre = listeQstLibre;
    }
}





