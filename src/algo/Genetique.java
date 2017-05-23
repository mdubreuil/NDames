package algo;

import java.util.List;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */
public class Genetique extends Optimisation {

    public Genetique(int n, int nmax) {
        super(n, nmax);
    }

    @Override
    public void run(int nbIteration) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int calculerConflits(List<Integer> voisin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    List<List<Integer>> calculerVoisins(List<Integer> reines) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
