package modele;

import java.awt.Point;

public class Piege extends Case {
	private boolean active;

	public Piege(Jeu _jeu) {
		super(_jeu);
		active = false;
		// TODO Auto-generated constructor stub
	}

	public boolean getActive() {
		return active;
	}

	public void setActive() {
		if (active == false) {
			active = true;
		} else {
			active = false;
		}
	}

	@Override
	public boolean entrerSurLaCase(Entite e) {
		if (!this.getActive()) {
			this.setActive();
			System.out.println("L'état du piège => " + this.getActive());
			if (e.getCase() != null) {
				e.getCase().quitterLaCase();
			}
			this.setEntite(e);
		} else {
			if (e.getCase() != null) {
				e.getCase().quitterLaCase();
			}
			this.setActive();
			this.e = null;

		}
		System.out.println("Le type de l'entité : " + e.getClass());
		return true;
	}

	@Override
	public void quitterLaCase() {
		setActive();
		e = null;
		System.out.println("Sortie de L'état du piège => " + this.getActive());
	}

	@Override
	public boolean peutEtreParcouru() {
		// TODO Auto-generated method stub
		return e == null;
	}

	@Override
	protected void setType(Case vide) {
		super.setType(vide);
		// TODO Auto-generated method stub

	}

}
