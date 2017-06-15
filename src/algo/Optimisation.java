package algo;

import algo.util.Echiquier;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */
public abstract class Optimisation implements IAlgorithme {

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

    @Override
    public abstract void run();

    protected void initialisation() {
        System.out.println("Stratégie d'initialisation: Random");

        xnum = Echiquier.initialisationRandom(n); // TODO autres types d'initialisation
        xmin = new ArrayList(xnum);
        fnum = fmin = Echiquier.fitness(xmin);
        if (verbose) {
            System.out.println();
            System.out.println("Solution initiale");
            Echiquier.afficherEchiquier(xnum);
        }
        System.out.println("Nb conflits initial: " + fnum);
        System.out.println();
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
