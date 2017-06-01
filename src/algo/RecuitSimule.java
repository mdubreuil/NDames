
package algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */
public class RecuitSimule extends Optimisation
{
    // Tester avec : n1 = 2 * n, n2 = 2 * n, temp0 = 0.3 * n et gamma = 0.1 
    
    /**
     * Nombre de fois que l'on change de température
     */
    protected int n1;
    
    /**
     * Nombre de déplacements pour une température donnée
     */
    protected int n2;
    
    /**
     * Coefficient pour déterminer la temperature à l'étape i
     */
    protected double coefficient = 0.5;
    
    /**
     * Temperature du recuit simulé
     */
    protected double temperature = 10; // Ne doit jamais valoir 0 !!

    public RecuitSimule(int n, int nmax) {
        super(n, nmax);
        this.n1 = 2 * n;
        this.n2 = 2 * n;
        this.temperature = 0.3 * n;
        this.coefficient = 0.1;
        
        System.out.println("Recuit simulé - " + n);
    }
    
    public RecuitSimule(int n, int nmax, double temperature, double coefficient) {
        this(n, nmax);
        
        if (temperature == 0 || coefficient == 0) {
            throw new IllegalArgumentException("La temperature et le coefficient ne doivent pas être nuls.");
        }

        this.temperature = temperature;
        this.coefficient = coefficient;
    }
    
    public RecuitSimule(int n, int nmax, double temperature, double coefficient, int n1, int n2) {
        this(n, nmax, temperature, coefficient);
        this.n1 = n1;
        this.n2 = n2;
    }

    @Override
    public void run(int nbIteration)
    {
        // Affichage état initial
        System.out.println("Température initiale: " + temperature);
        System.out.println("Coefficient: " + coefficient);
        System.out.println("Changement de température: " + n1);
        System.out.println("Déplacement pour une température donnée: " + n2);
        
        // Initialisation
        this.initialisation();
        Random rand = new Random();
        
        // Lancement algorithme
        while (/*num < nmax && */fmin > 0) {
            for (int k = 0; k < n1; k++) {
                for (int l = 0; l < n2; l++) {
                    /// Choix d'un voisin au hasard
                    List<Integer> voisin = genererVoisinAleatoire();
                    int fitnessVoisin = fitness(voisin);
                    int deltaFitness = fitnessVoisin - fnum;

                    if (deltaFitness <= 0) {
                        xnum = voisin;
                        fnum = fitnessVoisin;
                        if (fnum < fmin) {
                            fmin = fnum;
                            xmin = new ArrayList(xnum);
                        }
                    } else {
                        double p = rand.nextInt(1);
                        double facteur = Math.exp(-deltaFitness/temperature);

                        if (p <= facteur) {
                            xnum = voisin;
                            fnum = fitnessVoisin;
                        }
                        // sinon, on ne change pas de solution courante, on choisira juste un autre voisin
                    }

                    num++;
                }

                temperature *= coefficient;
            }
        }

        // Affichage
        if (verbose) {
            System.out.println("Solution finale");
            afficherEchiquier(xmin);
        }

        System.out.println("Nombre d'itérations : " + num);
        System.out.println("Température finale : " + temperature);
        System.out.println("Nb conflits total : " + fmin);
    }

    protected List<List<Integer>> calculerVoisins(List<Integer> reines) {
        return this.transformationEchange(reines);
    }

    /**
     * Transformation locale : échange de 2 reines
     * @param reines
     * @return Liste de voisins
     */
    private List<List<Integer>> transformationEchange(List<Integer> reines) {
        int nbVoisins = 0;
        List<List<Integer>> voisins = new ArrayList();

        // Pour chaque ligne
        for (int ligne = 0; ligne < n; ligne++) {
            for (int colonne = 0; colonne < n; colonne++) {
                List<Integer> voisin = new ArrayList(reines);
                if (reines.get(ligne) != colonne) {
                    
                    // Transformation locale par échange de 2 reines sur leur colonne
                    // Récupération de la ligne associée à la colonne choisie
                    int ligneIntervertible = n + 1;
                    for (int i = 0; i < n; i++) {
                        if (voisin.get(i) == colonne) {
                            ligneIntervertible = i;
                            break;
                        }
                    }

                    // Interversion
                    voisin.set(ligneIntervertible, voisin.get(ligne)); // throws a NullPointerException if reines has not the colonne
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
    
    /**
     * Génération aléatoire d'un voisin
     * @return Un voisin aléatoire
     */
    private List<Integer> genererVoisinAleatoire() {
        Random rand = new Random();
        int ligne1 = rand.nextInt(n);
        int ligne2;
        do {
            ligne2 = rand.nextInt(n);
        } while (ligne2 == ligne1);
        
        List<Integer> random = new ArrayList(xnum);
        random.set(ligne1, xnum.get(ligne2));
        random.set(ligne2, xnum.get(ligne1));
        
        return random;
    }
}
