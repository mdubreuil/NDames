package test;

import algo.Optimisation;
import algo.RecuitSimule;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */

public class TestRecuitSimule {
    public static void main(String [] args) {
        
        int n = 100;
        int nmax = (n * 2) * (n * 2);
        Optimisation opti = new RecuitSimule(n, 1000000000);
//        opti.setVerbose(true);
        opti.run();
    }
}
