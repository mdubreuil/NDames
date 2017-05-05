package algo;

import metier.*;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */

public abstract class Optimisation {
    protected int decalageVoisin;
    protected int directionsVoisin;
    protected IEchiquier solutionInitiale;
    protected boolean verbose = false;

    public Optimisation(int taillePlateau, int typeInitialisation, int decalageVoisin, int directionsVoisin) {
        this.decalageVoisin = decalageVoisin;
        this.directionsVoisin = directionsVoisin;
        this.solutionInitiale = new Echiquier(taillePlateau);
        
        switch (typeInitialisation) { // TODO : replacer par enum
            case 1:
                solutionInitiale.initialisationOptimisee();
                break;
            case 2:
                solutionInitiale.initialisationRandom();
                break;
            default:
                break;
        }
    }
    
    public int getDecalageVoisin() {
        return decalageVoisin;
    }

    public void setDecalageVoisin(int decalageVoisin) {
        this.decalageVoisin = decalageVoisin;
    }

    public int getDirectionsVoisin() {
        return directionsVoisin;
    }

    public void setDirectionsVoisin(int directionsVoisin) {
        this.directionsVoisin = directionsVoisin;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;

        if (verbose) {
            this.solutionInitiale.afficherEchiquier();
        }
    }
}
