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
        Optimisation opti = new Tabou(1, 1);
        opti.initialisationPlateau(1, 9);
        opti.afficherEchiquier(9);
    }
}
