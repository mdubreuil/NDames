package algo;

import metier.Echiquier;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */

public abstract class Optimisation {
    private int decalageVoisin;
    private int directionsVoisin;
    private Echiquier solutionInitiale;

    public Optimisation(int decalageVoisin, int directionsVoisin) {
        this.decalageVoisin = decalageVoisin;
        this.directionsVoisin = directionsVoisin;
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
    
    protected void initialisationPlateau(int strategie, int nbDames) {
        
    }
    
}
