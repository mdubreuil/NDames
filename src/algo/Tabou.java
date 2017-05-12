package algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.util.Pair;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */

/**
 * Modifier la structure de stockage des reines et des voisins :
 * - Stocker d'une part la ligne (ex: indice dans un arrayList), et de l'autre la colonne de la reine,
 *      cela permet de traiter moins de voisins, car on considère de toute façon qu'une solution optimale n'a pas plusieurs reines sur les mêmes lignes ou colonnes.
 *      Ainsi, le temps de calcule sera aussi optimisé et la mémoire aussi.
 * - Valable pour le recuit simulé et le Tabou, mais pas pour le génétique (garder solution actuelle).
 * 
 */
public class Tabou // extends Optimisation
{
    protected int n;
    protected int fitness = 0;
    
    /**
     * Définit le caractère aléatoire du résultat pour une même éxecution
     * - stochartique à true : on prend un échantillon dans le voisinnage, le résultat pourra varier
     * - déterministe (stochastique à false) : on prend tout le voisinnage, le résultat sera toujours le même
     */
    protected boolean stochastique = false;

    /**
     * Liste des solutions tabous
     */
    protected List<Pair<Integer, Integer>> tabous = new ArrayList(); // permutations interdites
    
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

    /**
     * 
     * @param n
     * @param nmax
     */
    public Tabou(int n, int nmax)
    {
        this.n = n;
        this.nmax = nmax;
        
    }
    
    public Tabou(int n, int nmax, boolean stochastique) {
        this(n, nmax);
        this.stochastique = stochastique;
    }

    public void run(int nbIteration) {
        
        // Initialisation
        xnum = initialisationRandom();
        xmin = new ArrayList(xnum);
        fitness = calculerConflits(xmin);
        if (verbose) {
            System.out.println("Solution initiale");
            afficherEchiquier(xnum);
            System.out.println("Nb conflits total : " + fitness);
        }

        List<List<Integer>> C;
        do {
//            System.out.println("Intération " + num);
            
            // Voisins non tabous
            C = calculerVoisins(xnum);
            
            if (!C.isEmpty()) {
                // Choix du voisin avec la meilleur fitness
                List<Integer> bestVoisin = null;
                int fitnessMin = -1;
                
                // Calcul du meilleur voisin
                for(List<Integer> voisin : C) {
                    int fitnessVoisin = calculerConflits(voisin);
                    if (fitnessMin < 0 || fitnessVoisin < fitnessMin) {
                        fitnessMin = fitnessVoisin;
                        bestVoisin = voisin;
                    }
                }

                // Ajout des tabous si notre nouveau choix dégrade la solution actuelle
                int fitnessCourante = calculerConflits(xnum);
                int deltaFitness = fitnessMin - fitnessCourante;
                if (deltaFitness >= 0) {
                    addTabous(xnum, bestVoisin);
                }
                
                // Traitement meilleur résultat
                if (fitnessMin < fitness) {
                    fitness = fitnessMin;
                    xmin = new ArrayList(bestVoisin);
                    if (verbose) {
                        System.out.println("Nouvelle fitness : " + fitness);
                    }
                }
                
                xnum = bestVoisin;
            }
            num++;
        } while (num <= nmax && !C.isEmpty());
        
        // Affichage
        if (verbose) {
            System.out.println("Solution finale");
            afficherEchiquier(xmin);
        }
        System.out.println("Nb conflits total : " + fitness);
        System.out.println("Nombre d'itérations : " + num);
    }

    private void addTabous(List<Integer> origine, List<Integer> voisin) {
        int colonneOrigine = -1, colonneVoisin = -1;
        for (int i = 0; i < n; i++) {
            if (origine.get(i) != voisin.get(i)) {
                colonneOrigine = origine.get(i);
                colonneVoisin = voisin.get(i);
            }
        }
        
        if (verbose) {
            System.out.println("Ajout tabou : " + colonneOrigine + " - " + colonneVoisin);
        }
        
        if (colonneOrigine < 0 || colonneVoisin < 0) {
            System.err.println("Seulement 2 colonnes devraient avoir été interverties : " + colonneOrigine);
            System.exit(colonneOrigine);
        }
        
        tabous.add(new Pair(colonneOrigine, colonneVoisin));
        tabous.add(new Pair(colonneVoisin, colonneOrigine));
    }

    public List<List<Integer>> calculerVoisins(List<Integer> reines) { // Map<Pair<Integer, Integer>, List<Integer>>
        int nbVoisins = 0;
        Random rand = new Random();
        List<List<Integer>> voisins = new ArrayList();
        //Map<Pair<Integer, Integer>, List<Integer>> voisins = new HashMap();

        // Pour chaque ligne
        for (int ligne = 0; ligne < n; ligne++) {
            for (int colonne = 0; colonne < n; colonne++) {
                List<Integer> voisin = new ArrayList(reines);
                if (reines.get(ligne) != colonne) {
                    if (tabous.contains(new Pair(voisin.get(ligne), colonne))) {
                        // Tabou, on ne fait pas l'interversion des colonnes
                        continue;
                    }
                    
                    // Récupération de la ligne associée à la colonne choisie
                    int ligneIntervertible = n + 1; // throws a NullPointerException if reines has not the colonne
                    for (int i = 0; i < n; i++) {
                        if (voisin.get(i) == colonne) {
                            ligneIntervertible = i;
                            break;
                        }
                    }

                    // Interversion
                    voisin.set(ligneIntervertible, voisin.get(ligne));
                    voisin.set(ligne, colonne);

                    // Ajout dans la liste de voisins
                    if (voisins.contains(voisin)) { // Eviter les doublons
                        continue;
                    }
                    voisins.add(voisin);
                    nbVoisins++;
                }
            }
        }
        
        if (verbose) {
            System.out.println("Nb voisins trouvés : " + nbVoisins);
        }

        return voisins;
    }
    
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

    public int calculerConflits(List<Integer> voisin) {
        int nbConflitsTotal = 0;
        
        for (int ligne = 0; ligne < n; ligne++) {
            nbConflitsTotal += this.calculeConflitsDiagonale(voisin, ligne, voisin.get(ligne));
        }

        return nbConflitsTotal;
    }

    public void afficherEchiquier(List<Integer> reines) {
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

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}
