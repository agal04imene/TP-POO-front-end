package application;

public class QuestionLibre extends Question {
    protected String reponse ;
    
   

    public QuestionLibre (String enonce) {
        super(enonce) ; 
    }
    
    //LES AFFICHAGES 
    public void afficherReponse (){
        System.out.println(reponse);
    }

    //getters and setters 
    public void setReponse (String reponse){
        this.reponse = reponse ; 
    }

    public String getReponse (){
    	return reponse ; 
    }

   
}