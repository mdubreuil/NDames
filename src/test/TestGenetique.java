package test;

import algo.Genetique;
import algo.Optimisation;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */

public class TestGenetique {
    public static void main(String[] args){
        Optimisation opti = new Genetique(8, 100);
        opti.setVerbose(true);
        opti.run();
    }
}
