package application;

import java.io.Serializable;
import java.util.Scanner ; 
public class Question implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int score ; 
    protected String enonce ; 

    public Question (String enonce){
        this.enonce=enonce ; 
        this.score = 1 ; // le score de la question est initialisé à 1
    }

    // pour que l'ortophoniste puisse saisir le score
    public void saisirScore() throws InvalidScoreException { 
        int score ; 
        Scanner sc = new Scanner(System.in) ; 
        System.out.println("Veuillez saisir le score : ");
        score = sc.nextInt();
        sc.close() ;
        // condition pour tester que le score suit la norme précisée 
        if (score < 1 || score > 10) {
            throw new InvalidScoreException("Score must be between 1 and 10.");
        } else {
        setScore(score);
        }
    }

    // Les affichages 

    public void afficherEnonce (){
        System.out.println(enonce);
    }

    public void afficherScore (){
        System.out.println(score);
    }


    //LES SETTERS ET LES GETTERS 
    //do we need access with them even with the protected ? (to be continued ...)

    public void setScore(int score){
        this.score = score ; 

    }

   public int getScore(){
        return score ; 
    }

	public void setEnonce(String enonce){
		this.enonce = enonce ; 
	}

	public String getEnonce(){
		return enonce ; 
	}
 
}






