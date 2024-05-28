// Compte d'utilisateur de l'application "Cabinet Orthophonique"
package application;

import java.util.* ;
import java.io.Serializable;
import java.time.* ;
import java.time.format.* ;
import java.util.ArrayList;
public class Orthophoniste implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Informations de l'orthophoniste
	
	private String nom ;
	private String prenom ;
	private String numeroTelephone ;
	private String adresse ;
	private String adresseEmail ;
	private String motDePass ;
	private boolean connecte;
	
	// Liste des patients de l'orthophoniste
	private HashSet<Patient> listePatients = new HashSet<Patient>();
	
	private HashSet<Consultation> listeConsultation = new HashSet<Consultation>();
	private HashSet<SeanceDeSuivi> listeSeancesSuivi = new HashSet<SeanceDeSuivi>();
	private HashSet<AtelierDeGroupe> listeAtelierDeGroupe = new HashSet<AtelierDeGroupe>();
	private HashSet<Patient> listePatientsConsultation = new HashSet<Patient>();
	
	// Agenda de l'orthophoniste
	// Agenda agendaOrthophoniste ;
	
	
	// Constructeur
	public Orthophoniste(String nom ,String prenom , String numeroTelephone , String adresse , String adresseEmail , String motDePass,HashSet<Patient> listePatients) {
		this.nom = nom ;
		this.prenom = prenom ;
		this.numeroTelephone = numeroTelephone ;
		this.adresse = adresse ;
		this.adresseEmail = adresseEmail ;
		this.motDePass = motDePass ;
		this.listePatients=listePatients;
	}
	

	// Méthode d'ajout d'un patient 
	public void AjouterPatient () {
		Scanner sc = new Scanner(System.in);
        
        System.out.print("Saisir le nom du patient : ");
        String nomPatient = sc.nextLine();
        
        System.out.print("Saisir le prénom du patient : ");
        String prenomPatient = sc.nextLine();
        
        System.out.print("Saisir la date de naissance du patient (format JJ/MM/AAAA) : ");
        String dateDeNaissanceStr = sc.nextLine();
        
        System.out.print("Saisir le lieu de naissance du patient : ");
        String lieuDeNaissance = sc.nextLine();
        
        System.out.print("Saisir l'adresse du patient : ");
        String adressePatient = sc.nextLine();

        // Convertir la date de naissance en LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateDeNaissance = LocalDate.parse(dateDeNaissanceStr, formatter);
        
        // Calculer l'âge du patient
        int agePatient = Period.between(dateDeNaissance, LocalDate.now()).getYears();
        
        // Créer le dossier du patient
        Dossier dossierPatient = new Dossier(nomPatient, prenomPatient, agePatient, adressePatient, dateDeNaissanceStr, lieuDeNaissance);
        
        // Créer le patient avec le dossier
        Patient patient = new Patient(dossierPatient);
        
        // Ajouter le patient à la liste des patients
        listePatients.add(patient);
        
        System.out.println("Patient ajouté avec succès !");
        
        sc.close() ;
    }
	
	
	// Méthode de suppression d'un patient 
	public void SupprimerPatient() {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Saisir le nom du patient à supprimer: ");
        String nomPatient = sc.nextLine();
        
        System.out.print("Saisir le prénom du patient à supprimer: ");
        String prenomPatient = sc.nextLine();
        
        Patient patientASupprimer = null;
        
        // Recherche du patient dans la liste (critère : nom & prénom)
        for (Patient patient : listePatients) {
            if (patient.getDossierPatient().getNom().equalsIgnoreCase(nomPatient) && 
                patient.getDossierPatient().getPrenom().equalsIgnoreCase(prenomPatient)) {
                patientASupprimer = patient;
                break;
            }
        }
        
        if (patientASupprimer != null) {
        	// Patient trouvé dans la liste
            listePatients.remove(patientASupprimer);
            System.out.println("Patient supprimé avec succès !");
        } else {
        	// Le patient n'existe pas dans la liste
            System.out.println("Patient non trouvé !");
        }
        
        sc.close() ;
    }

	
	// Méthode pour créer une anamnèse
	public void creerAnamnese(Patient patient,Anamnese anamnese) {
		Anamnese a= new Anamnese();
		Scanner sc = new Scanner(System.in);
		System.out.println("Saisir le nombre des questions que vous voulez : ");
		int nbrQst = sc.nextInt();
		sc.nextLine();
		
		for (int i=0;i<nbrQst;i++) {
			System.out.println("Saisir la question ("+(i+1)+") : ");
			String QstStr = sc.nextLine();
			
			if (patient.getDossierPatient().getAge()>=18) { // Si le patient est un adulte
				
				System.out.println("Saisir la catégorie de la question (hisoire de la maladie , suivi médical) :");
			    String categorie = sc.nextLine();
				QstLibreAnamneseAdulte Qst = new QstLibreAnamneseAdulte(QstStr,categorie);
				
				a.ajouterQuestion(Qst);
				
			} else { // Si le patient est un enfant 
				
				System.out.println("Saisir la catégorie de la question (Structure Familiale , Dynamique Familiale , Antécédents Familiaux , Conditions Natales ,  Développement Psychomoteur, Développement Langagier , Caractère Comportement )");
			    String categorie = sc.nextLine();
				QstLibreAnamneseEnfant Qst = new QstLibreAnamneseEnfant(QstStr,categorie);	
				
				a.ajouterQuestion(Qst);
			}			
		}
		
		anamnese =a;  // Affectation de la variable "a" à l'argument de la méthode creerAnamnese
		sc.close();
		
	}
	
	// Méthode pour sauvegarder une anamnèse 
	public void sauvegarderAnamnese (Patient patient ,Anamnese anamnese) {
		ArrayList<BO> listBO=new ArrayList<BO>();
		BO BilanOrtho = new BO(anamnese); 
		listBO.set(0,BilanOrtho);  // Le premier Bilan Orthophonique
	}
	
	// Méthode pour modifier une anamnèse
	public void modifierAnamnese(Patient patient, Anamnese anamnese) {
		Scanner sc = new Scanner(System.in);
		Anamnese a =patient.getDossierPatient().getListeBOs().get(0).getAnamnese();
		System.out.println(" Voici les questions de l'anamnèse courrante : ");
		a.afficherAnamnese();
		
		System.out.println(" Saisir la question que vous voulez modifier : ");
		String QstAncienneStr=sc.nextLine();
		System.out.println(" Saisir la catégorie de cette question :  ");
		String QstCategorieAncienne=sc.nextLine();
		
		System.out.println(" Saisir la nouvelle question : : ");
		String QstNouvelleStr=sc.nextLine();
		System.out.println(" Saisir la catégorie de cette question :  ");
		String QstcategorieNouvelle=sc.nextLine();
		
		if (patient.getDossierPatient().getAge()>=18) { // Si le patient est un adulte 
			QstLibreAnamneseAdulte QstAncienne= new QstLibreAnamneseAdulte (QstAncienneStr,QstCategorieAncienne);
			QstLibreAnamneseAdulte QstNouvelle = new QstLibreAnamneseAdulte(QstNouvelleStr,QstcategorieNouvelle);
			a.modifierQuestion(QstAncienne,QstNouvelle);
		} else {
			QstLibreAnamneseEnfant QstAncienne= new QstLibreAnamneseEnfant (QstAncienneStr,QstCategorieAncienne);
			QstLibreAnamneseEnfant QstNouvelle = new QstLibreAnamneseEnfant(QstNouvelleStr,QstcategorieNouvelle);
			a.modifierQuestion(QstAncienne,QstNouvelle);
		}
	   
		sc.close();
	}
	
	// Méthode pour créer un test des exercices  
	public void creerTestExercices(TestExercices test) {
		TestExercices t= new TestExercices(null,0); // juste pour l'initialisation
		Scanner sc = new Scanner ( System.in);
		System.out.println(" Saisir le nom du test : ");
	    String nomTest= sc.nextLine();
		System.out.println(" Saisir le nombre des exercices du test : ");
		int nbrEx = sc.nextInt();
		sc.nextLine();
		t.setNom(nomTest);
		t.setCapacite(nbrEx);
		for (int i=0 ; i<nbrEx ; i++){
	    System.out.println(" L'exercice (+"+(i+1)+") : ");
	    System.out.println("----------------------------");
		System.out.println(" Saisir le nom du material utilisé dans l'exercice ("+(i+1)+") : ")	;
		String nomMaterial = sc.nextLine();
		System.out.println(" Saisir la consigne de l'exercice ("+(i+1)+") : ")	;
		String consigne = sc.nextLine();
		Exercice Ex = new Exercice(nomMaterial,consigne);
		t.ajouterExercice(Ex);
		}
		sc.close();
		test=t;
	}
	
	// Méthode pour créer un test QCU
	public void creerTestQCU (Questionnaire test ) {
		Scanner sc = new Scanner (System.in);
		System.out.println(" Saisir le nom du test : ");
	    String nomQuestionnaire= sc.nextLine();
	    
	    System.out.println(" Saisir le nombre des questions dans le QCU :");
		int nbrQst = sc.nextInt();
		sc.nextLine();
		ArrayList<Question> listeQst = new ArrayList<Question>();
		 
		Questionnaire questionnaire = new Questionnaire (nomQuestionnaire, nbrQst);
		
		for (int i=0 ; i<nbrQst ; i++) {
			
			System.out.println(" Saisir l'énoncé de la question ("+(i+1)+") :");
			String QstStr = sc.nextLine();
			QCU qcu = new QCU(QstStr);
			
			System.out.println(" Saisir 4 réponses à choisir ( seulement une réponse doit etre juste ) ! ");
			for (int j=0 ; j<3 ; j++) {
				
				System.out.println(" La réponse fausse ("+(j+1)+") : ");
				String repFausse = sc.nextLine();
				qcu.ajoutRepFausse(repFausse);
			}
			
			System.out.println(" La réponse juste (4) : ");
			String repJuste = sc.nextLine();
			qcu.ajoutRepJuste(repJuste);
			listeQst.add(qcu); // ajouter la question à la liste des QCUs
		}
		
		questionnaire.setListeQuestion(listeQst); // ajouter les QCUs au questionnaire
		sc.close();
	}
	
	// Méthode pour créer un test QCM
	public void creerTestQCM ( Questionnaire test ) {
		Scanner sc = new Scanner (System.in);
		System.out.println(" Saisir le nom du test : ");
	    String nomQuestionnaire= sc.nextLine();
	    
	    System.out.println(" Saisir le nombre des questions dans le QCU :");
		int nbrQst = sc.nextInt();
		sc.nextLine();
		ArrayList<Question> listeQst = new ArrayList<Question>();
		 
		Questionnaire questionnaire = new Questionnaire (nomQuestionnaire, nbrQst);
		
		for (int i=0 ; i<nbrQst ; i++) {
			
			System.out.println(" Saisir l'énoncé de la question ("+(i+1)+") :");
			String QstStr = sc.nextLine();
			QCM qcm = new QCM(QstStr);
			
			System.out.println(" Saisir 4 réponses à choisir avec indication si la questio est juste ou fausse ! ");
			for (int j=0 ; j<3 ; j++) {
				
				System.out.println(" La réponse ("+(j+1)+") : ");
				String rep = sc.nextLine();
				
				System.out.println(" Fausse ou juste (0/1) ? ");
				int etatReponse = sc.nextInt();
				sc.nextLine();
				
				if (etatReponse == 0) {
					qcm.ajoutRepFausse(rep);	
				} else {
					qcm.ajoutRepJuste(rep);	
				}				
			}
			
			listeQst.add(qcm); // ajouter la question à la liste des QCUs
		}
		
		questionnaire.setListeQuestion(listeQst); // ajouter les QCUs au questionnaire
		sc.close();
	}
	
	// Méthode pour sauvegarder un test
	/*public void sauvgarderTest(Patient patient, Test test ) {
		ArrayList<BO> listeBOs= patient.getDossierPatient().getListeBOs();
		BO bo = listeBOs.get(listeBOs.size());
		EpreuveClinique[] listeEpreuveCliniques = bo.getListeEpreuves();
	  EpreuveClinique epreuveClinique = listeEpreuveCliniques[listeEpreuveCliniques.length-1];
	  epreuveClinique.ajouterTest(test);
	}
	
	// Méthode pour modifier un test 
	public void modifierTest(Patient patient,Test ancienTest, Test nouveauTest) {
		ArrayList<BO> listeBOs= patient.getDossierPatient().getListeBOs();
		BO bo = listeBOs.get(listeBOs.size());
		EpreuveClinique[] listeEpreuveCliniques = bo.getListeEpreuves();
		EpreuveClinique epreuveClinique = listeEpreuveCliniques[listeEpreuveCliniques.length-1];
		epreuveClinique.modifierTest(ancienTest, nouveauTest);
		
	}*/
	
	
	// Méthode pour consulter le dossier d'un patient 
	public void consulterDossierPatient(Patient patient) {
		Scanner sc = new Scanner (System.in);
		System.out.println(" Veuillez choisir une actio;n :");
		System.out.println(" (1). Visualiser la liste des rendez vous d'un patient.");       // lazem agenda 
		System.out.println(" (2). Consulter l'observation notée lors d'un rendez-vous.");    // lazem agenda
		System.out.println(" (3). Consulter les comptes rendus des tests effectués d'un patient."); 
		System.out.println(" (4). Consulter les Bilans orthophoniques d'un patient.");
		System.out.println(" (5). Afficher la liste des patients souffrant d'un trouble en particulier.");
		System.out.println(" (6). Afficher le pourcentage de patients par trouble.");
		int choix = sc.nextInt();
		sc.nextLine();
		switch (choix) {
		
		
		case 3:{
			
			System.out.println(" Saisir le nom du patient : ");
			String nomPatient = sc.nextLine();
			System.out.println(" Saisir le prénom du patient : ");
			String prenomPatient = sc.nextLine();
			for (Patient p : listePatients) {
				
	            if (p.getDossierPatient().getNom().equalsIgnoreCase(nomPatient) && 
	                p.getDossierPatient().getPrenom().equalsIgnoreCase(prenomPatient)) {  // cherchons le patient
	            	
	                ArrayList<BO> listeBOs=p.getDossierPatient().getListeBOs();
	                Iterator<BO> it_BOs = listeBOs.iterator();
	                
	                // Parcours la liste des Bilans Orthophoniques
	                while(it_BOs.hasNext()) {           		      
	                	BO bo = it_BOs.next();
	                	EpreuveClinique[] listeEpreuveClinique = bo.getListeEpreuves();
	                	
	                	// Parcours le tableau des epreuves cliniques 
	                	for (int i=0 ; i< listeEpreuveClinique.length ; i++) {
	                		ArrayList<Test> listeTests = listeEpreuveClinique[i].getListeTests();
	                		Iterator<Test> it_Tests = listeTests.iterator();
	                		int j=1; // juste pour l'affichage qui vient aprés
	                		// Parcours de  la liste des tests et affichage des comptes rendus de chaque test
	                		while (it_Tests.hasNext()) {	
	                			Test test = it_Tests.next();
	                			System.out.println(" Le compte rendu du test ("+j+") : "+test.getConclusion());
	                			j++;
	                		}
	                	}
	                
	                	}	                
	            }
	        }
		}
		
		case 4 :{
			System.out.println(" Saisir le nom du patient : ");
			String nomPatient = sc.nextLine();
			System.out.println(" Saisir le prénom du patient : ");
			String prenomPatient = sc.nextLine();
			for (Patient p : listePatients) {
				
	            if (p.getDossierPatient().getNom().equalsIgnoreCase(nomPatient) && 
	                p.getDossierPatient().getPrenom().equalsIgnoreCase(prenomPatient)) {
	            	ArrayList<BO> listeBOs=p.getDossierPatient().getListeBOs();
	                Iterator<BO> it_BOs = listeBOs.iterator();
	                while(it_BOs.hasNext()) {
	                	BO bo = it_BOs.next();
	                	bo.afficherBO();
	                }
	            	}
	            }            		
		}
		

		case 5 : {
			System.out.println(" Saisir le nom du trouble : ");
			String nomTrouble = sc.nextLine();
			
			int nbrPatientTotal =0;
			int nbrPatientTrouble=0;
			System.out.println(" Les patients qui ont le trouble : "+nomTrouble+" sont : ");
			
			Iterator<Patient> it_Patients = listePatients.iterator();			
			while(it_Patients.hasNext()) {
				nbrPatientTotal++;
				Patient p = it_Patients.next();
				ArrayList<BO> listeBOs = p.getDossierPatient().getListeBOs(); // La liste des BOs du patient courrant
				
				Iterator<BO> it_BOs = listeBOs.iterator();
				while (it_BOs.hasNext()) {
					BO bo = it_BOs.next();
					ArrayList<Trouble> listeTroubles= bo.getDiagnostic().listeTroubles;
					Iterator<Trouble> it_Troubles = listeTroubles.iterator();
					while (it_Troubles.hasNext()) {
						Trouble trouble = it_Troubles.next();
						if (trouble.nom.equalsIgnoreCase(nomTrouble) ) {
							nbrPatientTrouble++;
							String nom = p.getDossierPatient().getNom();
							String prenom = p.getDossierPatient().getPrenom();
							System.out.println(" Le patient ("+nbrPatientTrouble+") est : "+nom+" "+prenom+".");							
						}
					}
				}
			}
			
	    }
		
		case 6: {
			
			int nbrPatientTotal =0;
			int TroubleDeDeglutition=0;
			int TroubleNeuroDevloppemental=0;
			int TroubleCognitif=0;
			
			Iterator<Patient> it_Patients = listePatients.iterator();			
			while(it_Patients.hasNext()) {
				nbrPatientTotal++;
				Patient p = it_Patients.next();
				ArrayList<BO> listeBOs = p.getDossierPatient().getListeBOs(); // La liste des BOs du patient courrant
				
				Iterator<BO> it_BOs = listeBOs.iterator();
				while (it_BOs.hasNext()) {
					BO bo = it_BOs.next();
					ArrayList<Trouble> listeTroubles= bo.getDiagnostic().listeTroubles;
					Iterator<Trouble> it_Troubles = listeTroubles.iterator();
					while (it_Troubles.hasNext()) {
						Trouble trouble = it_Troubles.next();
						switch (trouble.categorie) {
						case Deglutition : TroubleDeDeglutition++;
						case NeuroDevloppemental : TroubleNeuroDevloppemental++;
						case Cognitif : TroubleCognitif++;
							
						}
					}
				}
			}
			
			System.out.println(" Le pourcentage des patients qui ont des troubles de catégorie \"Trouble de déglutition\" est : "+(TroubleDeDeglutition/nbrPatientTotal)*100);
			System.out.println(" Le pourcentage des patients qui ont des troubles de catégorie \"Trouble neuro-dévloppemental\" est : "+(TroubleNeuroDevloppemental/nbrPatientTotal)*100);
			System.out.println(" Le pourcentage des patients qui ont des troubles de catégorie \"Trouble cognitif\" est : "+(TroubleCognitif/nbrPatientTotal)*100);
		}
	
		}
		sc.close();
	}
	
	
	
	/* Autres méthodes : ajouterRendezVous, SupprimerRendezVous */
	

	// Getters & Setters 
	public String getAdresseEmail() {
		return adresseEmail ;
	}
	
	public String getNom() {
		return this.nom ;
	}
	
	public String getPrenom() {
		return this.prenom ;
	}
	
	public String getMotDePass() {
		return motDePass ;
	}
	
	public boolean getConnecte() {
		return this.connecte;
	}
	
	
	public String getNumTelephone() {
		return this.numeroTelephone;
	}
	
	public String getAddress() {
		return this.adresse;
	}
	
	
	public void setConnecte(boolean connecte) {
		this.connecte=connecte;
	}
	
	public void setMotDePass (String motDePass) {
		this.motDePass=motDePass;
	}
	
	public void setNom (String nom) {
		this.nom=nom;
	}
	
	public void setPrenom (String prenom) {
		this.prenom=prenom;
	}
	
	public void setNumTelephone (String num) {
		this.numeroTelephone=num;
	}
	
	public void setAdresse (String address) {
		this.adresse=address;
	}
	
	public void setAdressEmail (String address) {
		this.adresseEmail=address;
	}
	
	public  HashSet<Patient> getListePatients(){
		return this.listePatients;
	}
	
	public void setListePatients (HashSet<Patient> liste) {
		this.listePatients=liste;
	}
	
	public HashSet<Consultation> getListeConsultation (){
		return this.listeConsultation;
	}
	
	public HashSet<SeanceDeSuivi> getListeSeancesSuivi (){
		return this.listeSeancesSuivi;
	}
	
	public HashSet<AtelierDeGroupe> getListeAtelierDeGroupe (){
		return this.listeAtelierDeGroupe;
	}
	
	public HashSet<Patient> getListePatientsConsultation (){
		return this.listePatientsConsultation;
	}
	
	
	
	public void setListeConsultation (HashSet<Consultation> liste ) {
		this.listeConsultation=liste;
	}
	
	public void setListeSeanceDeSuivi (HashSet<SeanceDeSuivi> liste ) {
		this.listeSeancesSuivi=liste;
	}
	
	public void setListeAtelierDeGroupe (HashSet<AtelierDeGroupe> liste ) {
		this.listeAtelierDeGroupe=liste;
	}
	
	public void setListePatientsConsultation (HashSet<Patient> liste ) {
		this.listePatientsConsultation=liste;
	}
   
	
	public void ajouterConsultation( Consultation c) {
		if (!this.listeConsultation.contains(c)) this.listeConsultation.add(c);
	}

	
	public void ajouterSeanceDeSuivi( SeanceDeSuivi s) {
		if (!this.listeSeancesSuivi.contains(s)) this.listeSeancesSuivi.add(s);
	}
	
	public void ajouterAtelierDeGroupe(AtelierDeGroupe a) {
		if (!this.listeAtelierDeGroupe.contains(a)) this.listeAtelierDeGroupe.add(a);
	}
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Orthophoniste that = (Orthophoniste) o;
	        return Objects.equals(adresseEmail, that.adresseEmail) && 
	               Objects.equals(motDePass, that.motDePass);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(adresseEmail, motDePass);
	    }
	}
	
	
