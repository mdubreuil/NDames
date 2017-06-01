package algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */
public abstract class Optimisation {

    /**
     * Taille de l'échiquier
     */
    protected int n;
    
    /**
     * Fitness minimum
     */
    protected int fmin = 0;

    /**
     * Meilleure solution
     */
    protected List<Integer> xmin = new ArrayList();  // indices = lignes ; valeurs = colonnes
    
    /**
     * Itération courante
     */
    protected int num = 0;
    
    /**
     * Fitness courante
     */
    protected int fnum;

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

    public abstract void run(int nbIteration);

    protected void initialisation() {
        System.out.println("Stratégie d'initialisation: Random");

        xnum = initialisationRandom(); // TODO autres types d'initialisation
        xmin = new ArrayList(xnum);
        fnum = fmin = fitness(xmin);
        if (verbose) {
            System.out.println();
            System.out.println("Solution initiale");
            afficherEchiquier(xnum);
        }
        System.out.println("Nb conflits initial: " + fnum);
        System.out.println();
    }

    
    /**
     * Calcul du nombre de conflits pour un plateau donné
     * @param a
     * @return 
     */
    protected int fitness(List<Integer> a) {
        int f = 0;
        for (int i = 0; i < n; i++){
            for (int j = i+1; j < n; j++){
                if (Math.abs(i - j) == Math.abs(a.get(i) - a.get(j)))
                    f++;
            }
        }
        return f;
    }
    
    /**
     * Affichage de l'échiquier
     * @param reines 
     */
    protected void afficherEchiquier(List<Integer> reines) {
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
                if (colonne == reines.get(ligne)) {
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
     * @return 
     */
    private List<Integer> initialisationRandom() {
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
     * @return 
     */
    private List<Integer> initialisationOptimisee() {
        // TODO
        List<Integer> reines = new ArrayList();
        int j = 0;
        for (int i = 0; i < n; i++) {
            reines.add(i, i % 3 + j); // ???
        }
        
        return reines;
    }

    /**
     * @deprecated 
     * @param voisin
     * @return 
     */
    protected int calculerConflits(List<Integer> voisin) {
        int nbConflitsTotal = 0;
        
        for (int ligne = 0; ligne < n; ligne++) {
            nbConflitsTotal += this.calculeConflitsDiagonale(voisin, ligne, voisin.get(ligne));
        }

        return nbConflitsTotal;
    }

    /**
     * @deprecated 
     * @param reines
     * @param ligne
     * @param colonne
     * @return 
     */
    private int calculeConflitsDiagonale(List<Integer> reines, int ligne, int colonne) {
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
