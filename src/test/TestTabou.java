package test;

import algo.Tabou;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */

public class TestTabou {
    public static void main(String[] args){
        Tabou opti = new Tabou(200, 100000, true);
        opti.setnT(20);
        opti.run();
    }
}
