package algo;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */

public class Tabou extends Optimisation {
    public Tabou(int taillePlateau, int typeInitialisation, int decalageVoisin, int directionsVoisin) {
        super(taillePlateau, typeInitialisation, decalageVoisin, directionsVoisin);
    }

    @Override
    public void run(int nbIteration) {
        if (verbose) {
            this.solutionInitiale.afficherEchiquier();
        }
        
        System.out.println("Nb conflits total : " + this.solutionInitiale.calculeConflits());
    }
}
