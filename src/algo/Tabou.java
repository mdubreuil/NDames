package algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */
public class Tabou extends Optimisation
{
    public class Mouvement {
        private final int colonne1, colonne2;

        public Mouvement(int colonne1, int colonne2) {
            this.colonne1 = colonne1;
            this.colonne2 = colonne2;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }

            final Mouvement other = (Mouvement) obj;
            if (colonne1 == other.colonne1 && colonne2 == other.colonne2) {
                return true;
            } else if (colonne1 == other.colonne2 && colonne2 == other.colonne1) {
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 79 * hash + this.colonne1;
            hash = 79 * hash + this.colonne2;
            return hash;
        }

        @Override
        public String toString() {
            return colonne1 + " - " + colonne2;
        }
    }

    /**
     * Définit le caractère aléatoire du résultat pour une même éxecution
     * - stochartique à true : on prend un échantillon dans le voisinnage, le résultat pourra varier
     * - déterministe (stochastique à false) : on prend tout le voisinnage, le résultat sera toujours le même
     */
    protected boolean stochastique = false; // TODO implémenter stochasticité
    
    /**
     * Si stochastique, taille de l'échantillon des voisins aléatoires à sélectionner
     */
    protected int nechantillon = 10;

    /**
     * Liste des solutions tabous
     */
    protected Queue<Mouvement> tabous = new LinkedList(); // indice colonne1 - indice colonne2
    
    /**
     * Taille de la liste tabou
     */
    protected int nT = 3;

    public Tabou (int n, int nmax) {
        super(n, nmax);
        System.out.println("Tabou - " + n);
    }
    
    public Tabou (int n, int nmax, boolean stochastique) {
        this(n, nmax);
        this.stochastique = stochastique;
    }
    
    public Tabou (int n, int nmax, boolean stochastique, int nechantillon) {
        this(n, nmax, stochastique);
        this.nechantillon = nechantillon;
    }

    @Override
    public void run(int nbIteration)
    {
        // Affichage état initial
        System.out.println("Taille de la liste tabou: " + nT);
        if (stochastique) {
            System.out.println("Méthode stochastique");
            System.out.println("Taille de l'échantillon aléatoire: " + nechantillon);
        } else {
            System.out.println("Méthode déterministe");
        }

        // Initialisation
        this.initialisation();
        Map<Mouvement, List<Integer>> C;

        do {
//            System.out.println("Intération " + num);

            if (stochastique) {
                C = genererVoisinsAleatoires();
            } else {
                // Voisins non tabous
                C = genererVoisins();
            }

            if (!C.isEmpty()) {

                // Choix du voisin avec la meilleur fitness
                int fitnessMin = -1;
                List<Integer> bestVoisin = null;
                Mouvement tabou = null;
                for (Map.Entry<Mouvement, List<Integer>> voisin : C.entrySet()) {
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
                    addTabou(tabou);
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

    private void addTabou(Mouvement tabou) {
        if (tabou == null) {
            System.err.println("Tabou null");
            System.exit(num);
        }

        if (verbose) {
            System.out.println("Ajout tabou : " + tabou.toString());
        }

        tabous.add(tabou);

        if (tabous.size() > nT) {
            Mouvement tabouRemoved = tabous.poll();
            if (verbose) {
                System.out.println("Suppression tabou : " + tabouRemoved.toString());
            }
        }
    }
    
    public Map<Mouvement, List<Integer>> genererVoisins() {
        int nbVoisins = 0;
        Map<Mouvement, List<Integer>> voisins = new HashMap();

        // Pour chaque ligne
        for (int ligne = 0; ligne < n; ligne++) {
            for (int colonne = 0; colonne < n; colonne++) {
                if (xnum.get(ligne) != colonne) {
                    List<Integer> voisin = new ArrayList(xnum);

                    // Exclusion des tabous
                    Mouvement tabou = new Mouvement(xnum.get(ligne), colonne);
                    if (tabous.contains(tabou)) {
                        // Tabou, on ne fait pas l'interversion des colonnes
                        if (verbose) {
                            System.out.println("Tabou rencontré : " + tabou.toString());
                        }
                        
                        continue;
                    }
                    
                    // Récupération de la ligne associée à la colonne choisie
                    int ligneIntervertible = n + 1;
                    for (int i = 0; i < n; i++) {
                        if (xnum.get(i) == colonne) {
                            ligneIntervertible = i;
                            break;
                        }
                    }

                    // Interversion
                    voisin.set(ligneIntervertible, xnum.get(ligne)); // throws a NullPointerException if reines has not the colonne
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
    
    /**
     * Génération aléatoire d'un voisin
     * @return Un voisin aléatoire
     */
    private Map<Mouvement, List<Integer>> genererVoisinsAleatoires() {
        Random rand = new Random();
        Map<Mouvement, List<Integer>> voisins = new HashMap();
        
        while (voisins.size() < nechantillon) { // Peut tourner à l'infini si la liste tabou est trop grande
            int ligne1 = rand.nextInt(n);
            int ligne2;
            do {
                ligne2 = rand.nextInt(n);
            } while (ligne2 == ligne1);
            
            // Exclusion des tabous
            Mouvement echange = new Mouvement(xnum.get(ligne1), xnum.get(ligne2));
            if (tabous.contains(echange) || voisins.containsKey(echange)) {
                continue;
            }

            List<Integer> random = new ArrayList(xnum);
            random.set(ligne1, xnum.get(ligne2));
            random.set(ligne2, xnum.get(ligne1));

            voisins.put(echange, random);
        }
        
        return voisins;
    }   
}
