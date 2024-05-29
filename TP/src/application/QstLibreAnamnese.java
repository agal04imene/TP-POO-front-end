package application;

import java.io.Serializable;

public  class QstLibreAnamnese  {
	

	protected String enonce;
	protected String reponse;
	
	public QstLibreAnamnese(String enonce) {
		this.enonce = enonce;
	}

	public String getEnonce() {
		return enonce;
	}
	
	public String getReponse() {
		return reponse;
	}
	
	public void setEnonce(String enonce) {
		this.enonce = enonce;
	}
	
	public void setReponse(String rep) {
		reponse = rep ;
	}
	

}