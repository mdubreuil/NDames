package algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import metier.*;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */

public abstract class Optimisation {
    
    protected int n;
    protected int fitness = 0;
    
    /**
     * Itération courante
     */
    protected int num = 1;
    
    /**
     * Meilleure solution courante et finale
     */
    protected List<Integer> xmin = new ArrayList();  // indices = lignes ; valeurs = colonnes
    
    /**
     * Solution courante
     */
    protected List<Integer> xnum = new ArrayList();  // indices = lignes ; valeurs = colonnes
    
    /**
     * Affichage d'information sur l'état de l'algo en console
     */
    protected boolean verbose = false;
    
    /**
     * Nombre d'itération max
     */
    protected int nmax;

    public Optimisation(int n, int nmax) {
        this.n = n;
        this.nmax = nmax;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    abstract List<List<Integer>> calculerVoisins(List<Integer> reines);
    abstract int calculerConflits(List<Integer> voisin);
    
    public abstract void run(int nbIteration);
    
    public List<Integer> initialisationRandom() {
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
    
    protected void afficherEchiquier(List<Integer> reines) {
        System.out.println("   ---------------------------------");
        for (int ligne = 0; ligne < n; ligne++) {
            System.out.print(ligne + ": |");
            for (int colonne = 0; colonne < n; colonne++) {
                if (colonne == reines.get(ligne)) {
                    System.out.print("_X_|");
                } else {
                    System.out.print("___|");
                }
            }
            System.out.println("\n   ---------------------------------");
        }
    }
    
    protected int calculeConflitsDiagonale(List<Integer> reines, int ligne, int colonne) {
        int conflits = 0;
        int x = 0, y = 0;

        // Diagonale haut-gauche
        for (x = ligne - 1, y = colonne - 1; x >= 0 && y >= 0 && x < n && y < n; x--, y--) {
            if (reines.get(x) == y) conflits++;
        }

        // Diagonale haut-droite
        for (x = ligne - 1, y = colonne + 1; x >= 0 && y >= 0 && x < n && y < n; x--, y++) {
            if (reines.get(x) == y) conflits++;
        }
        
        // Diagonale bas-gauche
        for (x = ligne + 1, y = colonne - 1; x >= 0 && y >= 0 && x < n && y < n; x++, y--) {
            if (reines.get(x) == y) conflits++;
        }
        
        // Diagonale bas-droite
        for (x = ligne + 1, y = colonne + 1; x >= 0 && y >= 0 && x < n && y < n; x++, y++) {
            if (reines.get(x) == y) conflits++;
        }
        
        return conflits;
    }
}
