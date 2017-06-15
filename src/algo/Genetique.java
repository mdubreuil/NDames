package algo;

import algo.util.Echiquier;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */
public class Genetique extends Optimisation {
    
    private List<List<Integer>> population = new ArrayList();
    private List<Double> proportion = new ArrayList();
    private int fitnessTotale = 0;
    private Option optionReproduction = Option.RoueBiaiseeOption1;
    
    private enum Option {
        RoueBiaiseeOption1,
        RoueBiaiseeOption2,
    }

    public Genetique(int n, int nmax) {
        super(n, nmax);
    }

    @Override
    protected void initialisation() {
        System.out.println("Stratégie d'initialisation: Random\n");
        
        if (verbose) {
            System.out.println("Population initiale");
        }

        for (int i = 0; i < n; i++) {
            List<Integer> x = Echiquier.initialisationRandom(n);
            int fitnessX = Echiquier.fitness(x);
            fitnessTotale += fitnessX;

            population.add(x);
            proportion.add(Double.valueOf(fitnessX));
            
            if (verbose) {
                System.out.println("Fitness solution " + i + " : " + fitnessX);
            }
        }
        
        System.out.println("Nb fitness totale: " + fitnessTotale);
        System.out.println();
    }

    @Override
    public void run() {
        // Initialisation
        this.initialisation();
        
        while (fmin != 0 && num < nmax) {
            // Reproduction
            
            // Croisement
            
            // Mutation
            
            num++;
        }
        
        
        // 1. Génération de n solutions aléatoires : sans vérifier l'égalité
        // 2. Reproduction : 
        //      Calcul de la fitness de chaque solution
        //      Attribuer à chaque solution un pourcentage : (somme des fitness - fitness)/somme des fitness totale
        //      Sélection des solutions selon une roulette aléatoire lancé "option" fois selon
        

        
        
    }
    
    private void reproduction(List<List<Integer>> population) {
        
    }
    
    private void croisement(List<List<Integer>> population) {
        
    }
    
    private void mutation(List<List<Integer>> population) {
        
    }
}
