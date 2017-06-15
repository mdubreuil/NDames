
package algo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */
public class Echiquier
{
    /**
     * Calcul du nombre de conflits pour un plateau donné
     * @param echiquier Tableau de dames
     * @return 
     */
    public static int fitness(List<Integer> echiquier) {
        int f = 0, n = echiquier.size();
        for (int i = 0; i < n; i++){
            for (int j = i+1; j < n; j++){
                if (Math.abs(i - j) == Math.abs(echiquier.get(i) - echiquier.get(j)))
                    f++;
            }
        }
        return f;
    }

    /**
     * Affichage de l'échiquier
     * @param echiquier Tableau de dames
     */
    public static void afficherEchiquier(List<Integer> echiquier) {
        int n = echiquier.size();

        // Generate border
        String trait = "    ";
        for (int i = 0; i < n; i++) {
            trait += "----";
        }

        // Display
        System.out.println(trait);
        for (int ligne = 0; ligne < n; ligne++) {
            System.out.print(ligne + ": |");
            for (int colonne = 0; colonne < n; colonne++) {
                if (colonne == echiquier.get(ligne)) {
                    System.out.print("_X_|");
                } else {
                    System.out.print("___|");
                }
            }
            System.out.println("\n" + trait);
        }
    }
    
    /**
     * Initialisation random (optimisée)
     * @param n Taille du plateau
     * @return 
     */
    public static List<Integer> initialisationRandom(int n) {
        Random rand = new Random();
        List<Integer> reines = new ArrayList();
        List<Integer> indicesUsed = new ArrayList();
        for (int i = 0; i < n; i++) {
            int randomValue;
            do {
                randomValue = rand.nextInt(n);
            } while (indicesUsed.contains(randomValue));

            reines.add(randomValue);
            indicesUsed.add(randomValue);
        }
        
        return reines;
    }
    
    /**
     * Initialisation optimisée
     * @param n Taille du plateau
     * @return 
     */
    public static List<Integer> initialisationOptimisee(int n) {
        // TODO
        List<Integer> reines = new ArrayList();
        int j = 0;
        for (int i = 0; i < n; i++) {
            reines.add(i, i % 3 + j); // ???
        }
        
        return reines;
    }
}
