package algo;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.Dame;

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
        System.out.println("\n\nItération n°" + cptIteration);
        
        // Recupère tous les voisins possibles de la solution actuelle
        Map<Dame, List<Dame>> voisinsMap = solutionInitiale.getVoisins();

        // Pour chaque voisin, garder le meilleur
        int idVoisin = 1;
        Dame solutionOrigine = null, solutionVoisine = null;
        for (Map.Entry<Dame, List<Dame>> voisinList : voisinsMap.entrySet()) {
            Dame origin = voisinList.getKey();
            System.out.println("Affichage de " + voisinList.getValue().size() + " voisins\n");
            for (Dame voisine : voisinList.getValue()) {
                if (!solutionInitiale.getVoisin(origin, voisine)) {
                    System.err.println("Erreur lors de la création du voisin " + idVoisin);
                }
                int nbConflits = solutionInitiale.calculeConflits();

                // Affichage des résultats d'un voisin
                System.out.println("Voisin n°" + idVoisin);
                if (verbose) solutionInitiale.afficherEchiquier();
                System.out.println("Nb conflits total : " + nbConflits);
                
                // Retour à l'état initial du plateau
                if(!solutionInitiale.reset(origin, voisine)) {
                    System.err.println("Erreur lors du rétablissement du plateau initial");
                }

                if (nbConflits < fitness) {
                    System.out.println("Voisin n°" + idVoisin + " retenu");
                    try {
                        fitness = nbConflits;
                        solutionOrigine = origin.clone();
                        solutionVoisine = voisine.clone();
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(Tabou.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (fitness == 0) break;
                
                idVoisin++;
            }

            break;
        }
        
        

//        if (cptIteration > nbIteration) {
//            break;
//        }
    }
}
