package algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import metier.*;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */

public abstract class Optimisation {
    private int decalageVoisin;
    private int directionsVoisin;
    private Echiquier solutionInitiale;

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

    /**
     * @deprecated 
     * @param nbDames 
     */
    public void afficherEchiquier(int nbDames){
        
        System.out.println("-------------------");
        
        for (int i = 1; i <= nbDames; i++) {
            System.out.print(i + ": |");
            for (int y = 1; y <= nbDames; y++) {
                Dame dame = solutionInitiale.getDame(i, y);
                
                if (dame != null) {
                    System.out.print(dame.getIdentifiant() + "|");
                } else {
                    System.out.print(" |");
                }
            }
            System.out.println("\n-------------------");
        }
    }
}
