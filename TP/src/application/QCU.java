package application;

import java.util.ArrayList;
import java.util.Iterator;

public class QCU extends Question{
	
	private String reponseJuste;  // une seule réponse juste
	private ArrayList<String> reponsesFausses;
	private String reponse;
	
	public String getReponseJuste() {
		return reponseJuste;
	}
	public void setReponseJuste(String reponseJuste) {
		this.reponseJuste = reponseJuste;
	}
	public ArrayList<String> getReponsesFausses() {
		return reponsesFausses;
	}
	public void setReponsesFausses(ArrayList<String> reponsesFausses) {
		this.reponsesFausses = reponsesFausses;
	}
	public String getReponse() {
		return reponse;
	}
	public void setReponse(String reponse) {
		this.reponse = reponse;
	}
	public QCU(String enonce) {
		super(enonce);
		reponsesFausses=new ArrayList<String>();
		
	}
	
	public float evaluer() {
		if(reponse==reponseJuste) return 1;
		return 0;
	}
	public void ajoutRepFausse(String reponse) {
		
		this.reponsesFausses.add(reponse);
	}
	public void ajoutRepJuste(String reponse) {
		this.reponseJuste=reponse;
		
	}
	public void repondre(String reponse) {
		this.reponse=reponse;
	}
	public void afficheReponses()
	{
		
		int i=1;
		
			System.out.println(i+"-"+ reponseJuste);
			i++;
		Iterator<String> it1= reponsesFausses.iterator();
		
		while (it1.hasNext()) {
			System.out.println(i+"-"+ it1.next());
			i++;
			}
}
	}
