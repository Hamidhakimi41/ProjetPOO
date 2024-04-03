package modele;

public class BlocSimple extends Bloc {

	public BlocSimple(Jeu _jeu, Case c) {
		super(_jeu, c);
		// TODO Auto-generated constructor stub
	}
	public boolean pousser(Direction d) {
        return jeu.deplacerEntite(this, d);
    }

}
