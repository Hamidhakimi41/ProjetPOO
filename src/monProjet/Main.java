package monProjet;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import VueControleur.VueControleur;
import modele.Jeu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*Cette classe est le controleur dans le modele MVC
 * elle est charge de changer de niveau de jeu suite au notification du vu
 * elle charge les sont et lire quand le vu l'appel
 * de creer un un jeu,un vu et agir comme intermediare
 * */
/*@param niveausuivant elle est declarée static
 *  pourque si une instance modifie ca sera modifier pour tout le monde */

public class Main {
	public ArrayList<String> niveaux;
	public static int niveausuivant = 0;
	

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
		String lvl5="Niveaux/niveau5.txt";
		String end="Niveaux/niveaufinal.txt";
    	
    	
    	niveaux = new ArrayList<>();
    	niveaux.add(lvl1);
    	niveaux.add(lvl2);
    	niveaux.add(lvl3);
    	niveaux.add(lvl4);
		niveaux.add(lvl5);
		niveaux.add(end);

		if(lvl<niveaux.size()) {
	
		
		
				
    	jeu = new Jeu(niveaux.get(lvl));
    	vc = new VueControleur(jeu,this);
    	vc.setVisible(true);
    	jeu.addObserver(vc);	}
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

	
/* cette fonction parent en paramet une decision de l'utilisateur
 * 	afin de decider si on creer le meme niveau ou on passe au niveau suivant
 * ainsi 0 pour passer au niveau suivant 1 sinon*/
	public void change_De_Niveau(int result) {

		if (result == 0) {
			
			niveausuivant++;
			System.out.println("niveausuivant " + niveausuivant);
			this.vc.setVisible(false);
			new Main(niveausuivant);

		} else if (result == 1) {
			new Main(niveausuivant);

		} else {
			this.vc.setVisible(false);
		}

	}
/*@param chemin:est le chemin pour acceder au fichier son afin de lire
 * cette fonction elle permet de lire un son apartir de son chemin
 * */
	public static void lireSonMouvement(String chemin) {

		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(chemin).getAbsoluteFile());
			System.out.println("accès reussi");
			/* on fait un clip apart du fichier qui contient les données du son*/
			Clip clip = AudioSystem.getClip();

			// on ouvre le son
			clip.open(audioInputStream);
			// on commence la lecture
			clip.start();
		} catch (Exception e) {
			System.out.println("Error playing sound: " + e.getMessage());
			
		}

	}
	public void rejouer() {
		int i=this.niveausuivant;
		this.vc.setVisible(false);
		Main mn = new Main(i);
		
		
	}

	public static void main(String[] args) {

		Main mn = new Main();

	}

	
}
