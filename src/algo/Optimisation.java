package algo;

import java.util.ArrayList;
import java.util.List;
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

    public Optimisation(int decalageVoisin, int directionsVoisin) {
        this.decalageVoisin = decalageVoisin;
        this.directionsVoisin = directionsVoisin;
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
    
    public void initialisationPlateau(int strategie, int nbDames) {
        List<Dame> lDamesInitiale = new ArrayList<Dame>();
        int nbPair = 2, nbImpair = 1, indiceColonne = 1;
        
        // TODO : A optimiser ?
        while (indiceColonne < nbDames/2 + 1){
            Dame d = new Dame(indiceColonne,indiceColonne,nbImpair);
            lDamesInitiale.add(d);
            nbImpair = nbImpair + 2;
            indiceColonne++;
        }
        
        while (indiceColonne <= nbDames){
            Dame d = new Dame(indiceColonne,indiceColonne,nbPair);
            lDamesInitiale.add(d);
            nbPair = nbPair + 2;
            indiceColonne++;
        }
        
        if((nbDames % 2 == 1) && (indiceColonne == nbDames + 1)){
            Dame d = new Dame(nbDames,nbDames,1);
            lDamesInitiale.add(d);
        }
        
        solutionInitiale = new Echiquier(nbDames,strategie);
        solutionInitiale.setDames(lDamesInitiale);
    }
    
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
