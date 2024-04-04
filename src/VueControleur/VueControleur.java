package VueControleur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

import modele.*;
import monProjet.Main;

/**
 * Cette classe a deux fonctions : (1) Vue : proposer une représentation
 * graphique de l'application (cases graphiques, etc.) (2) Controleur : écouter
 * les évènements clavier et déclencher le traitement adapté sur le modèle
 * (flèches direction Pacman, etc.))
 *
 */
public class VueControleur extends JFrame implements Observer {
	private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle
						// pour le rafraichissement, permet de communiquer les actions clavier (ou
						// souris)

	private int sizeX; // taille de la grille affichée
	private int sizeY;
	private Main mn;
	// private int niveausuivant=0;

	// icones affichées dans la grille
	private ImageIcon icoHero;
	private ImageIcon icoVide;
	private ImageIcon icoMur;
	private ImageIcon icoBloc;
	private ImageIcon iconCoeur;
	private ImageIcon iconPorte;
	private ImageIcon iconbouton;
	private ImageIcon iconPorteOpen;
	private ImageIcon iconBlocSimple;
	private ImageIcon iconPiege;
	private ImageIcon iconPiegeActive;

	private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée
									// à une icône, suivant ce qui est présent dans le modèle)

	public VueControleur(Jeu _jeu, Main m) {
		sizeX = jeu.SIZE_X;
		sizeY = _jeu.SIZE_Y;
		jeu = _jeu;
		mn = m;

		chargerLesIcones();
		placerLesComposantsGraphiques();
		ajouterEcouteurClavier();

		jeu.addObserver(this);

		mettreAJourAffichage();

	}

	private void ajouterEcouteurClavier() {
		addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un
											// objet qui correspond au controleur dans MVC
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) { // on regarde quelle touche a été pressée

				case KeyEvent.VK_LEFT:
					jeu.deplacerHeros(Direction.gauche);
					break;
				case KeyEvent.VK_RIGHT:
					jeu.deplacerHeros(Direction.droite);
					break;
				case KeyEvent.VK_DOWN:
					jeu.deplacerHeros(Direction.bas);
					break;
				case KeyEvent.VK_UP:
					jeu.deplacerHeros(Direction.haut);
					break;

				}
			}
		});
	}

	private void chargerLesIcones() {
		icoHero = chargerIcone("Images/paceman.png");
		icoVide = chargerIcone("Images/Vide.png");
		icoMur = chargerIcone("Images/Mur.png");
		icoBloc = chargerIcone("Images/Colonne.png");
		iconCoeur = chargerIcone("Images/viande.png");
		iconPorte = chargerIcone("Images/porte.png");
		iconbouton = chargerIcone("Images/button.png");
		iconPorteOpen = chargerIcone("Images/porteOuverte.png");
		iconBlocSimple = chargerIcone("Images/simple.png");
		iconPiege = chargerIcone("Images/piege.png");
		iconPiegeActive = chargerIcone("Images/piegeActive.png");
	}

	private ImageIcon chargerIcone(String urlIcone) {
		BufferedImage image = null;

		try {
			image = ImageIO.read(new File(urlIcone));
		} catch (IOException ex) {
			Logger.getLogger(VueControleur.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}

		return new ImageIcon(image);
	}

	private void placerLesComposantsGraphiques() {
		setTitle("Sokoban");
		setSize(400, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre
		/* je modifier ici le x et y */
		JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases
																				// graphiques et les positionner sous la
																				// forme d'une grille

		tabJLabel = new JLabel[sizeX][sizeY];

		for (int Y = 0; Y < sizeY; Y++) {
			for (int X = 0; X < sizeX; X++) {
				JLabel jlab = new JLabel();
				tabJLabel[X][Y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique
										// à celles-ci (voir mettreAJourAffichage() )
				grilleJLabels.add(jlab);
			}
		}
		add(grilleJLabels);
	}

	/**
	 * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté
	 * de la vue (tabJLabel)
	 */
	private void mettreAJourAffichage() {

// a replacer
		for (int Y = 0; Y < sizeY; Y++) {
			for (int X = 0; X < sizeX; X++) {

				Case c = jeu.getGrille()[X][Y];

				if (c != null) {

					Entite e = c.getEntite();

					if (e != null) {
						if (c.getEntite() instanceof Heros) {
							tabJLabel[X][Y].setIcon(icoHero);
						} else if (c.getEntite() instanceof BlocObjectif) {
							tabJLabel[X][Y].setIcon(icoBloc);
						}
						/* si c'est l'ojectif on met l'icon de l'objectif */
						else if (c.getEntite() instanceof Objectif) {
							tabJLabel[X][Y].setIcon(iconCoeur);

						} else if (c.getEntite() instanceof Boutton) {
							tabJLabel[X][Y].setIcon(iconbouton);

						} else if (c.getEntite() instanceof BlocSimple) {
							tabJLabel[X][Y].setIcon(iconBlocSimple);

						}
					} else {
						if (jeu.getGrille()[X][Y] instanceof Mur) {
							tabJLabel[X][Y].setIcon(icoMur);
						}

						else if (jeu.getGrille()[X][Y] instanceof Vide) {

							tabJLabel[X][Y].setIcon(icoVide);
						} else if (jeu.getGrille()[X][Y] instanceof Porte) {
							if (jeu.getGrille()[X][Y].getActive() == false) {
								tabJLabel[X][Y].setIcon(iconPorte);
							} else {
								tabJLabel[X][Y].setIcon(iconPorteOpen);

							}
						}

						else if (jeu.getGrille()[X][Y] instanceof CaseGlasse) {
							tabJLabel[X][Y].setOpaque(true);
							tabJLabel[X][Y].setBackground(Color.BLUE);

						}

						else if (jeu.getGrille()[X][Y] instanceof Piege) {
							if ((jeu.getGrille()[X][Y].getActive()) == false) {
								tabJLabel[X][Y].setIcon(iconPiege);
							} else {
								tabJLabel[X][Y].setIcon(iconPiegeActive);
							}

						}

					}

				}

			}
		}
	}

	/* message associe à la fin de partie */
	private void finDePartie() {
		JPanel finPanel = new JPanel(new BorderLayout());

		// On Créer les labels pour les étapes et le score
		JLabel etapesLabel = new JLabel("ETAPES : ");
		JLabel scoreLabel = new JLabel("SCORE : ");

		// On Crée les labels pour afficher les valeurs des étapes et du score
		String etapesValue = String.valueOf(this.jeu.getEtapes());
		String scoreValue = String.valueOf(this.jeu.getScore());
		JLabel etapes = new JLabel(etapesValue);
		JLabel score = new JLabel(scoreValue);

		// On Ajoute les labels des étapes et du score à un JPanel pour les placer côte
		// à côte
		JPanel infoPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
		infoPanel.add(etapesLabel);
		infoPanel.add(etapes);
		infoPanel.add(scoreLabel);
		infoPanel.add(score);

		// Ajouter le JPanel des informations au JPanel principal (endPanel)
		finPanel.add(infoPanel, BorderLayout.NORTH);
		String[] lesOptions = { "Play Next Puzzle", "Play Puzzle Again" };

		int result = JOptionPane.showOptionDialog(null, finPanel, "END LEVEL", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, lesOptions, lesOptions[0]);
		mn.change_De_Niveau(result);

		// JOptionPane.showMessageDialog(null, "Bravo Couz vous avez fini le niveau");

	}

	private void ouvertureDelaPorte() {
		for (int Y = 0; Y < sizeY; Y++) {
			for (int X = 0; X < sizeX; X++) {

				Case c = jeu.getGrille()[X][Y];

				if (c != null) {

					if (jeu.getGrille()[X][Y] instanceof Porte) {
						tabJLabel[X][Y].setIcon(iconPorteOpen);
						;
						((Porte) jeu.getGrille()[X][Y]).changerDetat();

					}

				}

			}

		}
	}

	@Override

	public void update(Observable o, Object arg) {
		if (arg == null) {
			System.out.println("entre update" + arg);
			mettreAJourAffichage();

		} else if (arg instanceof String) {
			System.out.println("entre update" + (String) arg);
			switch ((String) arg) {
			case "fin":

				mn.lireSonMouvement("Audios/succes.WAV");
				System.out.println("j'appelle la fonction fin de partie");
				finDePartie();

				break;

			case "boutonAppuier":
				ouvertureDelaPorte();
				break;
			case "piege":
				mn.lireSonMouvement("Audios/piege.WAV");
				break;
			case "bouge":
				mn.lireSonMouvement("Audios/bouge.WAV");
				break;
			case "bonus":
				mn.lireSonMouvement("Audios/bonus.WAV");
				break;

			}
		}

		else {
			System.out.println("erreur" + arg.getClass());

		}
	}

	/*
	 * 
	 * // récupérer le processus graphique pour rafraichir // (normalement, à
	 * l'inverse, a l'appel du modèle depuis le contrôleur, utiliser un autre
	 * processus, voir classe Executor)
	 * 
	 * 
	 * SwingUtilities.invokeLater(new Runnable() {
	 * 
	 * @Override public void run() { mettreAJourAffichage(); } });
	 */

}
