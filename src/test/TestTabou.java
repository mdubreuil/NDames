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
        int tailleEquichier = 8;
        int decalageVoisinnage = 1;
        int directionVoisinnage = 1; // Correspond aux 8 directions environnantes
        int typeInitialisation = 1; // Initialisation opti = 1 ; Initialisation random = 2 ;
        
        Optimisation opti = new Tabou(tailleEquichier, typeInitialisation, decalageVoisinnage, directionVoisinnage);
        opti.afficherEchiquier(9);
    }
}
