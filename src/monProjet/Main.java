package monProjet;
import java.util.ArrayList;

import VueControleur.VueControleur;
import modele.Jeu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Main {
    public static void main(String[] args) {
    	ArrayList<String> niveaux = new ArrayList<>();
    	String lvl1="Niveaux/FichierNiveau1.txt";
    	
    	int niveauCourant=0;
    	niveaux.add(lvl1);
        Jeu jeu = new Jeu(niveaux.get(niveauCourant));
        
        VueControleur vc = new VueControleur(jeu);
        vc.setVisible(true);

    }
}
