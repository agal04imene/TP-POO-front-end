package application;

import java.io.Serializable;
import java.util.ArrayList;

public class Questionnaire  extends Test implements Serializable {    
	private static final long serialVersionUID = 1L;
    private ArrayList<Question> listeQuestion;
    private ArrayList<QCM> listeQcm = new ArrayList<>();
    private ArrayList<QCU> listeQcu = new ArrayList<>();
    private ArrayList<QuestionLibre> listeQstLibre = new ArrayList<>();

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
            if (ex.getEnonce().equals(enonce)) {
                return pos;
            }
            pos++;
        }
        return -1;  // Return -1 if the question is not found
    }

    public void supprimerQuestion(String enonce) {
        int p = rechercherQuestion(enonce);
        if (p >= 0) {
            listeQuestion.remove(p);
        }
    }

    public void ajouterQcm(QCM nouveauQcm) {
        ajouterQuestion(listeQcm, nouveauQcm);
    }

    public void ajouterQcu(QCU nouveauQcu) {
        ajouterQuestion(listeQcu, nouveauQcu);
    }

    public void ajouterQstLibre(QuestionLibre nouveauQstLibre) {
        ajouterQuestion(listeQstLibre, nouveauQstLibre);
    }

    public void supprimerQcm(QCM ancienQcm) {
        supprimerQuestion(listeQcm, ancienQcm);
    }

    public void supprimerQcu(QCU ancienQcu) {
        supprimerQuestion(listeQcu, ancienQcu);
    }

    public void supprimerQuestionLibre(QuestionLibre ancienneQuestionLibre) {
        supprimerQuestion(listeQstLibre, ancienneQuestionLibre);
    }

    private <T> void ajouterQuestion(ArrayList<T> liste, T nouvelleQuestion) {
        if (!liste.contains(nouvelleQuestion)) {
            liste.add(nouvelleQuestion);
        } else {
            System.out.println("La question que vous voulez ajouter existe déjà.");
        }
    }

    private <T> void supprimerQuestion(ArrayList<T> liste, T question) {
        if (liste.contains(question)) {
            liste.remove(question);
            System.out.println("La question a été supprimée avec succès.");
        } else {
            System.out.println("La question que vous voulez supprimer n'existe pas.");
        }
    }

    public void modifierQcm(QCM ancienQcm, QCM nouveauQcm) {
        modifierQuestion(listeQcm, ancienQcm, nouveauQcm);
    }

    public void modifierQcu(QCU ancienQcu, QCU nouveauQcu) {
        modifierQuestion(listeQcu, ancienQcu, nouveauQcu);
    }

    public void modifierTQcm(QuestionLibre ancienQstLibre, QuestionLibre nouveauQstLibre) {
        modifierQuestion(listeQstLibre, ancienQstLibre, nouveauQstLibre);
    }

    private <T> void modifierQuestion(ArrayList<T> liste, T ancienneQuestion, T nouvelleQuestion) {
        if (liste.contains(ancienneQuestion)) {
            liste.remove(ancienneQuestion);
            liste.add(nouvelleQuestion);
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
