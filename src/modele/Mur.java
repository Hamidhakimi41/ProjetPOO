package modele;

public class Mur extends Case {
    public Mur(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean peutEtreParcouru() {
        return false;
    }

	@Override
	public boolean getActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setActive() {
		// TODO Auto-generated method stub
		
	}


	
}
