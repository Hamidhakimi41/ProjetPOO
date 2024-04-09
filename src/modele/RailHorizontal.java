package modele;

public class RailHorizontal extends Case{


    public RailHorizontal(Jeu _jeu){
        super(_jeu);

    }
    public boolean estDeplaçableHorizontal(Direction d){
        return d == Direction.gauche || d == Direction.droite;
    }
    @Override
    public boolean peutEtreParcouru() {
        return e == null;
    }

    @Override
    public boolean getActive() {
        return false;
    }

    @Override
	public void setActive() {

    }

}
