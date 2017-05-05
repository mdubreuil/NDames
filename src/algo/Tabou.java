package algo;

import java.util.List;
import java.util.Map;
import metier.Dame;
import metier.Echiquier;
import metier.IEchiquier;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */

public class Tabou extends Optimisation
{
    protected int fitness = 0;
    
    public Tabou(int taillePlateau, int typeInitialisation, int decalageVoisin, int directionsVoisin)
    {
        super(taillePlateau, typeInitialisation, decalageVoisin, directionsVoisin);
        this.fitness = this.solutionInitiale.calculeConflits();
    }

    @Override
    public void run(int nbIteration) {
        if (verbose) {
            System.out.println("Solution initiale");
            this.solutionInitiale.afficherEchiquier();
        }
        System.out.println("Nb conflits total : " + fitness);

        int cptIteration = 1;
        
        // Recupère tous les voisins possibles de la solution actuelle
        Map<Dame, List<Dame>> voisinsMap = solutionInitiale.getVoisins();
        System.out.println("Itération n°" + cptIteration);
        System.out.println("\nNb de voisins trouvés : " + voisinsMap.size());

        // Pour chaque voisin, garder le meilleur
        for (Map.Entry<Dame, List<Dame>> voisinList : voisinsMap.entrySet()) {
            Dame origin = voisinList.getKey();    
            Dame firstDame = voisinList.getValue().get(0);
            
            Echiquier voisin = solutionInitiale.getVoisin(origin, firstDame);
            int nbConflits = voisin.calculeConflits();
            
            if (verbose) {
                System.out.println("Voisin n°" + cptIteration);
                voisin.afficherEchiquier();
                System.out.println("Nb conflits total : " + fitness);
            }
            
            if (nbConflits < fitness) {
                fitness = nbConflits;
                solutionInitiale = voisin;
            }
            
            break;
        }
        
//        if (cptIteration > nbIteration) {
//            break;
//        }
    }
}
