package metier;

import java.util.List;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */

public class Echiquier {
    private boolean estSolution;
    private List<Dame> dames;
    private int nbConflits;
    private int tailleEchiquier;
    private int typeInitialisation; // TODO : replacer par enum

    public Echiquier(int tailleEchiquier, int typeInitialisation) {
        this.tailleEchiquier = tailleEchiquier;
        this.typeInitialisation = typeInitialisation;
    }
    
    public Dame getDame(int x, int y) {
        for (Dame dame : dames) {
            if (dame.getX() == x  && dame.getY() == y) {
                return dame;
            }
        }
        
        return null;
    }
    

    public boolean isEstSolution() {
        return estSolution;
    }

    public List<Dame> getDames() {
        return dames;
    }

    public int getNbConflits() {
        return nbConflits;
    }

    public int getTailleEchiquier() {
        return tailleEchiquier;
    }

    public int getTypeInitialisation() {
        return typeInitialisation;
    }

    public void setDames(List<Dame> dames) {
        this.dames = dames;
    }
    
}
