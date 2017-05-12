package algo;

import metier.*;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */

public abstract class Optimisation {
    
    /**
     * @deprecated 
     */
    protected int decalageVoisin;
    
    /**
     * @deprecated 
     */
    protected int directionsVoisin;
    
    /**
     * Solution en cours
     * Au départ = x0
     */
    protected IEchiquier solutionInitiale;
    
    /**
     * Affichage d'information sur l'état de l'algo en console
     */
    protected boolean verbose = false;
    
    /**
     * Nombre d'itération max
     */
    protected int nmax;

    public Optimisation(int taillePlateau, int typeInitialisation, int decalageVoisin, int directionsVoisin) {
        this.decalageVoisin = decalageVoisin;
        this.directionsVoisin = directionsVoisin;
        this.solutionInitiale = new EchiquierSimple(taillePlateau);
        
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
    }
    
    public abstract void run(int nbIteration);
}
