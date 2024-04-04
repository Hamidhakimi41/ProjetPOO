/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Jeu extends Observable {

	public static final int SIZE_X = 20;
	public static final int SIZE_Y = 10;

	// listes d'objectif
	private int nombreObjectif = 0;
	private int score=0;
	private int etapes=0;
	private Heros heros;

	HashMap<Case, Point> map = new HashMap<Case, Point>(); // permet de récupérer la position d'une case à
																	// partir de sa référence
	private Case[][] grilleEntites = new Case[SIZE_X][SIZE_Y]; // permet de récupérer une case à partir de ses
																// coordonnées

	public Jeu(String lvl) {
		initialisationNiveau(lvl);
	}

	public Case[][] getGrille() {
		return grilleEntites;
	}

	public Heros getHeros() {
		return heros;
	}

	public void deplacerHeros(Direction d) {
		heros.avancerDirectionChoisie(d);
		setChanged();
		notifyObservers();
	}
	public int getNombreObjectif() {
		return nombreObjectif;
	}

	private void initialisationNiveau(String lvl) {
		/* j'initiase le jeux avec le niveau du fichier */
		String cheminFichier = lvl;
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 20; y++) {

				addCase(new Vide(this), y, x);
			}

		}
		/* on initialise le grille a vide en parcourrant une première fois le fichier */

		try {
			// Ouverture du fichier
			File fichier = new File(cheminFichier);
			FileReader lecteurFichier = new FileReader(fichier);
			BufferedReader lecteurBufferise = new BufferedReader(lecteurFichier);

			int index = 0;

			// Lecture du fichier ligne par ligne
			StringBuilder contenuFichier = new StringBuilder();
			String lignePourPremierLecture;

			while ((lignePourPremierLecture = lecteurBufferise.readLine()) != null) {
				contenuFichier.append(lignePourPremierLecture);
				contenuFichier.append("\n");
				for (int i = 0; i < lignePourPremierLecture.length(); i++) {
					if (grilleEntites != null && index < grilleEntites.length && index < grilleEntites[index].length) {
						if (i == 10) {
							System.out.println("la lette est " + lignePourPremierLecture.charAt(i));

						}

						switch (lignePourPremierLecture.charAt(i)) {
						case 'P'://la porte
							addCase(new Porte(this), i, index);
							break;
						case 'b'://si c'est un bouton
							Boutton b1=new Boutton(this,grilleEntites[i][index]);
							
							break;
						case 'G':
							
							addCase(new CaseGlasse(this), i, index);
							break;
						case 'h':
							addCase(new RailHorizontal(this), i, index);
							break;
						case 'v':
							addCase(new RailVertical(this), i, index);
							break;
						case 'M':
						
							addCase(new Mur(this), i, index);
							break;
						case 'p'://le p de piege
							
							addCase(new Piege(this), i, index);
							break;
						case 'O':
							Objectif o = new Objectif(this, grilleEntites[i][index]);
							nombreObjectif++;
							break;

						case 'H':
							heros = new Heros(this, grilleEntites[i][index]);
							break;
						case 's'://blocsimple
							BlocSimple s = new BlocSimple(this, grilleEntites[i][index]);
							break;
						case 'B'://bloc objectif
							BlocObjectif b = new BlocObjectif(this, grilleEntites[i][index]);
							break;


						}
					}

				}
				index++;

			}

			// Fermeture des lecteurs
			lecteurBufferise.close();
			lecteurFichier.close();

			// Affichage du contenu du fichier
			System.out.println("Contenu du fichier :");
			System.out.println(contenuFichier.toString());

		} catch (IOException e) {
			System.err.println("Erreur de lecture : " + e.getMessage());
		}

		// murs extérieurs horizontaux
		/*
		 * for (int x = 0; x < 20; x++) { addCase(new Mur(this), x, 0); addCase(new
		 * Mur(this), x, 9); }
		 * 
		 * // murs extérieurs verticaux for (int y = 1; y < 9; y++) { addCase(new
		 * Mur(this), 0, y); addCase(new Mur(this), 19, y); }
		 * 
		 * for (int x = 1; x < 19; x++) { for (int y = 1; y < 9; y++) {
		 * 
		 * addCase(new Vide(this), x, y); }
		 * 
		 * }
		 * 
		 * heros = new Heros(this, grilleEntites[4][4]); Bloc b = new Bloc(this,
		 * grilleEntites[6][6]); Objectif o=new Objectif(this, grilleEntites[7][7]);
		 */

	}
	public int getScore() {
		return score;
	}

	public int getEtapes() {
		return etapes;
	}
	private void addCase(Case e, int x, int y) {
		grilleEntites[x][y] = e;
		map.put(e, new Point(x, y));
	}

	/**
	 * Si le déplacement de l'entité est autorisé (pas de mur ou autre entité), il
	 * est réalisé Sinon, rien n'est fait.
	 */
	public boolean deplacerEntite(Entite e, Direction d) {
		boolean retour = true;

		Point pCourant = map.get(e.getCase());

		Point pCible = calculerPointCible(pCourant, d);

		if (contenuDansGrille(pCible)) {
			Entite eCible = caseALaPosition(pCible).getEntite();
			/* si c'est pas null et n'est pas l'objectif */
			
			if (eCible != null && !(eCible instanceof Objectif)) {
				eCible.pousser(d);
			}
			/* entite cible est l'objectif */
			if (eCible instanceof Objectif && e instanceof BlocObjectif) {
				/*si on atteint l'object le score du niveau augmente de 50*/
				score+=50;
				System.out.println("le nb object avant"+nombreObjectif);
				this.nombreObjectif--;
				e.getCase().quitterLaCase();
				caseALaPosition(pCible).entrerSurLaCase(e);
				//caseALaPosition(pCible);				
				if(e.finDePartie(this)) {
				
					setChanged();
					notifyObservers("fin");
				
					
					
				}
				
				//si c'est la fin de partie on notify la vu
				
				
				

			}

			// si l'c'est l'objectif et le bouton
		if(e instanceof Heros && eCible instanceof Boutton) {
			//on chance l'etat de la porte
			setChanged();
			notifyObservers("boutonAppuier");
			System.out.println("notif envoyer");
				
			}
			if(e instanceof Heros || e instanceof Bloc){
				Case caseCourant = e.getCase();
				if (caseCourant instanceof RailVertical && (d == Direction.gauche || d == Direction.droite)){
					return false;
				}
				if (caseCourant instanceof RailHorizontal && (d == Direction.bas || d == Direction.haut)){
					return false;
				}
			}

			// si la case est libérée
		if (caseALaPosition(pCible).peutEtreParcouru()) {
				/*quand l'hero bouge on augment les etapes*/
				etapes++;
				e.getCase().quitterLaCase();
				caseALaPosition(pCible).entrerSurLaCase(e);

		}
		/*si c'est le piege*/
		if (e!=null && caseALaPosition(pCible) instanceof Piege) {
				caseALaPosition(pCible).entrerSurLaCase(e);
				
				
			   } 
		if (e!=null && caseALaPosition(pCible) instanceof CaseGlasse) {
			e.getCase().quitterLaCase();
			((CaseGlasse) caseALaPosition(pCible)).glisser(e,d);
			
			
		   }

			
			
			else {
				retour = false;
			}

		} 
		else {
			retour = false;
		}

		return retour;
	}

	Point calculerPointCible(Point pCourant, Direction d) {
		Point pCible = null;

		switch (d) {
		case haut:
			pCible = new Point(pCourant.x, pCourant.y - 1);
			break;
		case bas:
			pCible = new Point(pCourant.x, pCourant.y + 1);
			break;
		case gauche:
			pCible = new Point(pCourant.x - 1, pCourant.y);
			break;
		case droite:
			pCible = new Point(pCourant.x + 1, pCourant.y);
			break;

		}

		return pCible;
	}

	/**
	 * Indique si p est contenu dans la grille
	 */
public	boolean contenuDansGrille(Point p) {
		return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
	}

	Case caseALaPosition(Point p) {
		Case retour = null;

		if (contenuDansGrille(p)) {
			retour = grilleEntites[p.x][p.y];
		}

		return retour;
	}

}
