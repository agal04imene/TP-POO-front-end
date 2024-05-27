package application;

import java.util.ArrayList;
import java.util.Iterator;

public class QCM extends Question {
	
	private int nbPropositions;
	private ArrayList<String> reponsesJustes;
	private ArrayList<String> reponsesFausses;
	private ArrayList<String> reponses;
	
	
	
	public int getNbPropositions() {
		return nbPropositions;
	}

	public void setNbPropositions(int nbPropositions) {
		this.nbPropositions = nbPropositions;
	}

	public ArrayList<String> getReponsesJustes() {
		return reponsesJustes;
	}

	public void setReponsesJustes(ArrayList<String> reponsesJustes) {
		this.reponsesJustes = reponsesJustes;
	}

	public ArrayList<String> getReponsesFausses() {
		return reponsesFausses;
	}

	public void setReponsesFausses(ArrayList<String> reponsesFausses) {
		this.reponsesFausses = reponsesFausses;
	}

	public ArrayList<String> getReponses() {
		return reponses;
	}

	public void setReponses(ArrayList<String> reponses) {
		this.reponses = reponses;
	}

	public QCM(String enonce) {
		super(enonce);
		this.reponses= new ArrayList<String>();
		this.reponsesFausses=new ArrayList<String>();
		this.reponsesJustes=new ArrayList<String>();
	}
	
	public float evaluer()
	{
		float taux=1/nbPropositions,moyenne=0;
		Iterator<String> it=reponses.iterator();
		while(it.hasNext()) {
			String rep=it.next();
			if (this.reponsesJustes.contains(rep)) {
				moyenne+=taux;
			}
			if(this.reponsesFausses.contains(rep)) {
				moyenne-=taux;
			}	
		}
		Iterator<String> it1=reponsesJustes.iterator();
		while(it1.hasNext()) {
			if(reponses.contains(it1.next())== false)
				moyenne-=taux;
		}
		Iterator<String> it2=reponsesFausses.iterator();
		while (it2.hasNext()) {
			if(reponses.contains(it2.next())==false )
				moyenne+=taux;
		}
		if (moyenne<0) {
			return 0;
		}
		return moyenne;
	}
	
	public boolean ajoutRepJuste(String reponse) {
		if (reponse!= "") {
			this.reponsesJustes.add(reponse);
			
			return true;
		}else {//Erreur
			}
		return false;
	}
	public boolean ajoutRepFausse(String reponse) {
		if (reponse!= "") {
			this.reponsesJustes.add(reponse);
			
			return true;
		}else {//Erreur
			}
		return false;
	}
	
	
	public void repondre(String reponse) {
		
			this.reponses.add(reponse);

	}
	
	
	public void afficheReponses()
	{
		Iterator<String> it= reponsesJustes.iterator();
		int i=1;
		while (it.hasNext()) {
			System.out.println(i+"-"+ it.next());
			i++;
		
	}
		Iterator<String> it1= reponsesFausses.iterator();
		
		while (it1.hasNext()) {
			System.out.println(i+"-"+ it1.next());
			i++;
		
	}
}

	
}
