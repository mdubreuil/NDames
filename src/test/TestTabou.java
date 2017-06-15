package test;

import algo.Optimisation;
import algo.Tabou;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */

public class TestTabou {
    public static void main(String[] args){
        Optimisation opti = new Tabou(20, 100000, true);
        //Optimisation opti = new Tabou(500, 100000); // Itérations = 2, durée = 68 minutes, conflits > 600
        opti.setVerbose(true);
        opti.run();
    }
}
