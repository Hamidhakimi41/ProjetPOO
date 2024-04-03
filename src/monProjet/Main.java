package monProjet;
import java.io.FileInputStream;
import java.util.ArrayList;


import VueControleur.VueControleur;
import modele.Jeu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Main {
	public ArrayList<String> niveaux;
	public static int niveausuivant=0;
	/*pourque si une instance modifie ca sera modifier pour tout le monde*/
	
	public Jeu jeu;
	public VueControleur vc;
	public Main() {
		this(niveausuivant);
		
    	
    	
		
	}
	
	public  Main(int lvl) {
		String lvl1="Niveaux/niveau1.txt";
		String lvl2="Niveaux/niveau2.txt";
		String lvl3="Niveaux/niveau3.txt";
		String lvl4="Niveaux/niveau4.txt";
    	
    	
    	niveaux = new ArrayList<>();
    	//niveaux.add(lvl1);
    	//niveaux.add(lvl2);
    	//niveaux.add(lvl3);
    	niveaux.add(lvl4);
    	
		if(lvl<niveaux.size()) {
	
		
		
				
    	jeu = new Jeu(niveaux.get(lvl));
    	vc = new VueControleur(jeu,this);
    	vc.setVisible(true);
    	jeu.addObserver(vc);	}
	}
	public void change_De_Niveau(int result) {
		
    	
    	if(result==0) {
    		//si on choisi de passer au niveau suivant
    		niveausuivant++;
    		System.out.println("niveausuivant "+niveausuivant);
    		this.vc.setVisible(false);
    		new Main(niveausuivant);
    	
    		
    	}
    	else if(result==1) {//si on choisi de rejouer
    		new Main(niveausuivant);
    		
    	}
    	else {
    		this.vc.setVisible(false);
    	}
		
	}
	
	/*public static void lireSonMouvement() {
		
		
			String chemin="Audios/bouge.mp3";
			try {
	            FileInputStream fileInputStream = new FileInputStream(chemin);
	            Player player = new Player(fileInputStream);
	            player.play();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}*/
    public static void main(String[] args) {
    	/*ArrayList<String> niveaux = new ArrayList<>();
    	String lvl1="Niveaux/FichierNiveau1.txt";
    	String lvl2="Niveaux/niveaux3.txt";
    	
    	int niveauCourant=0;
    	
        Jeu jeu = new Jeu(niveaux.get(1));
        
        VueControleur vc = new VueControleur(jeu);
        vc.setVisible(true);
        jeu.addObserver(vc);*/
    	Main mn=new Main();
    	

    }
}
