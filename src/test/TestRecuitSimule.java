package test;

import algo.Optimisation;
import algo.RecuitSimule;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */

public class TestRecuitSimule {
    public static void main(String [] args){

        Optimisation opti = new RecuitSimule(5, 1000000, 10, 0.5, 15);
        opti.setVerbose(true);
        opti.run(2);
    }
}
