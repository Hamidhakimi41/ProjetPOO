package modele;

public class Porte extends Case {
	private boolean etat;

	public Porte(Jeu _jeu) {
		
		super(_jeu);
		etat=false;
		// TODO Auto-generated constructor stub
	}

	
	public void changerDetat() {
		if(etat==false) {
			etat=true;
			
		}
		else {
			etat=false;
		}
	}
	@Override
	public boolean peutEtreParcouru() {
		
		// TODO Auto-generated method stub
		return etat;
	}


	@Override
	/*renvoi etats de la porte qui seras traiter dans la vue*/
	public boolean getActive() {
		// TODO Auto-generated method stub
		return etat;
		}


	@Override
	protected void setActive() {
		// TODO Auto-generated method stub
		
	}


	}
