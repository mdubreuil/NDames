package algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
 * TODO: Amélioration : sous-classe Tabou avec méthodes add(int x, int y), contains(int x, int y), ...
 */
public class Tabou extends Optimisation
{
    
    
    /**
     * Définit le caractère aléatoire du résultat pour une même éxecution
     * - stochartique à true : on prend un échantillon dans le voisinnage, le résultat pourra varier
     * - déterministe (stochastique à false) : on prend tout le voisinnage, le résultat sera toujours le même
     */
    protected boolean stochastique = false;
    
    /**
     * Si stochastique, taille de l'échantillon des voisins aléatoires à sélectionner
     */
    protected int nechantillon = 10;

    /**
     * Liste des solutions tabous
     */
//    protected List<Pair<Integer, Integer>> tabous = new ArrayList(); // permutations interdites
    
    protected Queue<Pair<Integer, Integer>> tabous = new LinkedList(); // colonne1-colonne2
    
    /**
     * Taille de la liste tabou
     */
    protected int nT = 3;

    /**
     * 
     * @param n
     * @param nmax
     */
    public Tabou(int n, int nmax) {
        super(n, nmax);
        System.out.println("Tabou - " + n);
    }
    
    public Tabou(int n, int nmax, boolean stochastique) {
        this(n, nmax);
        this.stochastique = stochastique;
    }
    
    public Tabou(int n, int nmax, boolean stochastique, int nechantillon) {
        this(n, nmax, stochastique);
        this.nechantillon = nechantillon;
    }

    @Override
    public void run(int nbIteration)
    {
        // Affichage état initial
        if (stochastique) {
            System.out.println("Méthode stochastique");
        } else {
            System.out.println("Méthode déterministe");
        }

        // Initialisation
        this.initialisation();
        Map<Pair<Integer, Integer>, List<Integer>> C;

        do {
//            System.out.println("Intération " + num);
            
            // Voisins non tabous
            C = calculerVoisins2(xnum);
            
//            if (stochastique) {
//                C = voisinsAleatoires(C);
//            }
            
            if (!C.isEmpty()) {
                // Choix du voisin avec la meilleur fitness
                List<Integer> bestVoisin = null;
                int fitnessMin = -1;
                
                // Calcul du meilleur voisin
                Pair<Integer, Integer> tabou = null;
                for (Map.Entry<Pair<Integer, Integer>, List<Integer>> voisin : C.entrySet()) {
                    int fitnessVoisin = fitness(voisin.getValue());
                    if (fitnessMin < 0 || fitnessVoisin < fitnessMin) {
                        fitnessMin = fitnessVoisin;
                        bestVoisin = voisin.getValue();
                        tabou = voisin.getKey();
                    }
                }

                // Ajout des tabous si notre nouveau choix dégrade la solution actuelle
                int deltaFitness = fitnessMin - fnum;
                if (deltaFitness >= 0) {
                    this.addTabou(tabou);
                }
                
                // Traitement meilleur résultat
                if (fitnessMin < fmin) {
                    fmin = fitnessMin;
                    xmin = new ArrayList(bestVoisin);
                    if (verbose) {
                        System.out.println("Nouvelle fitness : " + fmin);
                    }
                }
                
                // Actualisation solution courante
                xnum = bestVoisin;
                fnum = fitnessMin;
            }
            num++;
            
            //if (num%100 == 0) System.out.println("Itération " + num); // A enlever
            
        } while (num < nmax && !C.isEmpty() && fmin != 0); // Si on ne met pas la dernière condition, l'algo continue jusqu'à nmax : est-ce un problème d'exclusion des tabous ?
        
        // Affichage
        if (verbose) {
            System.out.println("Solution finale");
            afficherEchiquier(xmin);
        }
        
        System.out.println("Nombre d'itérations : " + num);
        System.out.println("Nb conflits total : " + fmin);
    }

    private void addTabou(Pair<Integer, Integer> tabou) {
        if (tabou == null) {
            System.err.println("Tabou null");
            System.exit(num);
        }

        int colonneOrigine = tabou.getKey();
        int colonneVoisin = tabou.getValue();

        if (verbose) {
            System.out.println("Ajout tabou : " + colonneOrigine + " - " + colonneVoisin);
        }
        
        tabous.add(tabou);
        
        if (tabous.size() > nT) {
            Pair<Integer, Integer> tabouRemoved = tabous.poll();
            if (verbose) {
                System.out.println("Suppression tabou : " + tabouRemoved.getKey() + " - " + tabouRemoved.getValue());
            }
        }
    }
    
    public Map<Pair<Integer, Integer>, List<Integer>> calculerVoisins2(List<Integer> reines) {
        int nbVoisins = 0;
        Map<Pair<Integer, Integer>, List<Integer>> voisins = new HashMap();

        // Pour chaque ligne
        for (int ligne = 0; ligne < n; ligne++) {
            for (int colonne = 0; colonne < n; colonne++) {

                if (reines.get(ligne) != colonne) {
                    List<Integer> voisin = new ArrayList(reines);
                    int oldColonne = voisin.get(ligne);
                    Pair<Integer, Integer> tabou = new Pair(oldColonne, colonne);
                    
                    // Exclusion des tabous
                    if (tabous.contains(tabou) || tabous.contains(new Pair(tabou.getValue(), tabou.getKey()))) { // old colonne, new colonne
                        // Tabou, on ne fait pas l'interversion des colonnes
                        
                        if (verbose) {
                            System.out.println("Tabou rencontré : " + tabou.getKey() + " - " + tabou.getValue());
                        }
                        
                        continue;
                    }
                    
                    // Récupération de la ligne associée à la colonne choisie
                    int ligneIntervertible = n + 1;
                    for (int i = 0; i < n; i++) {
                        if (voisin.get(i) == colonne) {
                            ligneIntervertible = i;
                            break;
                        }
                    }

                    // Interversion
                    voisin.set(ligneIntervertible, oldColonne); // throws a NullPointerException if reines has not the colonne
                    voisin.set(ligne, colonne);

                    // Ajout dans la liste de voisins
                    if (voisins.containsValue(voisin)) { // Eviter les doublons
                        continue;
                    }
                    voisins.put(tabou, voisin);
                    nbVoisins++;
                }
            }
        }
        
        if (verbose) {
            System.out.println("Nb voisins trouvés : " + nbVoisins);
        }

        return voisins;
    }

    @Override
    public List<List<Integer>> calculerVoisins(List<Integer> reines) {
//        int nbVoisins = 0;
        List<List<Integer>> voisins = new ArrayList();
//
//        // Pour chaque ligne
//        for (int ligne = 0; ligne < n; ligne++) {
//            for (int colonne = 0; colonne < n; colonne++) {
//                List<Integer> voisin = new ArrayList(reines);
//                if (reines.get(ligne) != colonne) {
//                    // Exclusion des tabous
//                    if (tabous.contains(new Pair(voisin.get(ligne), colonne))) {
//                        // Tabou, on ne fait pas l'interversion des colonnes
//                        continue;
//                    }
//                    
//                    // Récupération de la ligne associée à la colonne choisie
//                    int ligneIntervertible = n + 1;
//                    for (int i = 0; i < n; i++) {
//                        if (voisin.get(i) == colonne) {
//                            ligneIntervertible = i;
//                            break;
//                        }
//                    }
//
//                    // Interversion
//                    voisin.set(ligneIntervertible, voisin.get(ligne)); // throws a NullPointerException if reines has not the colonne
//                    voisin.set(ligne, colonne);
//
//                    // Ajout dans la liste de voisins
//                    if (voisins.contains(voisin)) { // Eviter les doublons
//                        continue;
//                    }
//                    voisins.add(voisin);
//                    nbVoisins++;
//                }
//            }
//        }
//        
//        if (verbose) {
//            System.out.println("Nb voisins trouvés : " + nbVoisins);
//        }
//
        return voisins;
    }

//    private List<List<Integer>> voisinsAleatoires(List<List<Integer>> C) {
//        Random rand = new Random();
//        List<List<Integer>> echantillon = new ArrayList(C);
//        int max = C.size();
//        
//        while (echantillon.size() < nechantillon && echantillon.size() < max) {
//            echantillon.remove(C.get(rand.nextInt(echantillon.size())));
//        }
//        
//        if (verbose) {
//            System.out.println("Echantillon de taille " + echantillon.size());
//        }
//
//        return echantillon;
//    }
//    
//        private Map<Pair<Integer, Integer>, List<Integer>> voisinsAleatoires(Map<Pair<Integer, Integer>, List<Integer>> C) {
//        Random rand = new Random();
//        Map<Pair<Integer, Integer>, List<Integer>>  echantillon = new HashMap(C);
//        int max = C.size();
//        
//        while (echantillon.size() < nechantillon && echantillon.size() < max) {
//            echantillon.remove(C.keySet()..get(rand.nextInt(echantillon.size())));
//        }
//        
//        if (verbose) {
//            System.out.println("Echantillon de taille " + echantillon.size());
//        }
//
//        return echantillon;
//    }
}
