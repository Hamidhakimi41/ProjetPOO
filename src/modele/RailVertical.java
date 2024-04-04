package modele;

public class RailVertical extends Case{



    public RailVertical(Jeu _jeu){
        super(_jeu);
    }
    public boolean estDeplaçableVertical(Direction d){
        return d == Direction.bas || d == Direction.haut;
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
    protected void setActive() {

    }
}
