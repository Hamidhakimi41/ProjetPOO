package modele;

public class Vide extends Case {

    public Vide(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean peutEtreParcouru() {
        return e == null;
    }

	@Override
	public boolean getActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void setActive() {
		// TODO Auto-generated method stub
		
	}

	



}
