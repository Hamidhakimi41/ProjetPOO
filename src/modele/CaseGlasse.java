package modele;

import java.awt.Point;

public class CaseGlasse extends Case {
	public CaseGlasse(Jeu _jeu) {
		super(_jeu);
	}


	public boolean glisser(Entite e, Direction d) {
		Point pCourant = jeu.map.get(e.getCase());
		Point pCible = jeu.calculerPointCible(pCourant, d);

		
        while (jeu.contenuDansGrille(pCible)){
        	
            Case caseSuivante = jeu.caseALaPosition(pCible);
            if (caseSuivante != null && caseSuivante.peutEtreParcouru()){
                if (caseSuivante instanceof CaseGlasse){
                    pCourant = pCible;
                    pCible = jeu.calculerPointCible(pCourant,d);
                }else {
                    break;
                }
            }else {
                break;
            }
        }
        if (jeu.contenuDansGrille(pCible)){
            Case caseSuivante = jeu.caseALaPosition(pCible);
            if (caseSuivante != null && caseSuivante.peutEtreParcouru()){
                caseSuivante.entrerSurLaCase(e);
                return true;
            }
        }
        return false;
    }


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
	public void setActive() {
		// TODO Auto-generated method stub

	}
}
