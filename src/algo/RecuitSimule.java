
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
    /**
     * Nombre de déplacements pour une température donnée
     */
    protected int deplacement = 10;
    
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
        System.out.println("Recuit simulé - " + n);
    }
    
    public RecuitSimule(int n, int nmax, int temperature, double coefficient) {
        this(n, nmax);
        
        if (temperature == 0 || coefficient == 0) {
            throw new IllegalArgumentException("La temperature et le coefficient ne doivent pas être nuls.");
        }

        this.temperature = temperature;
        this.coefficient = coefficient;
    }
    
    public RecuitSimule(int n, int nmax, int temperature, double coefficient, int deplacement) {
        this(n, nmax, temperature, coefficient);
        this.deplacement = deplacement;
    }

    @Override
    public void run(int nbIteration)
    {
        // Affichage état initial
        System.out.println("Température initiale: " + temperature);
        System.out.println("Coefficient: " + coefficient);
        System.out.println("Déplacement pour une température donnée: " + deplacement);
        
        // Initialisation
        this.initialisation();
        Random rand = new Random();
        
        // Lancement algorithme
        while (num < nmax && fmin > 0) {
            for (int l = 0; l < deplacement; l++) {
                
                // Calcul des voisins
                List<List<Integer>> V = calculerVoisins(xnum);

                /// Choix d'un voisin au hasard
                List<Integer> voisin = V.get(rand.nextInt(V.size()));

                // Réfléchir sur ce code...
                int fitnessVoisin = fitness(voisin);
                int deltaFitness = fitnessVoisin - fnum;
                
                if (deltaFitness <= 0) {
                    if (fitnessVoisin < fmin) {
                        fmin = fitnessVoisin;
                        xmin = voisin;
                    }
                    xnum = new ArrayList(voisin);
                    fnum = fitnessVoisin;
                } else {
                    double p = rand.nextInt(1);
                    double facteur = Math.exp(-deltaFitness/temperature);
                    
                    if (p <= facteur) {
                        xnum = new ArrayList(voisin);
                        fnum = fitnessVoisin;
                    }
                    // sinon, on ne change pas de solution courante, on choisira juste un autre voisin
                }
                
                num++;
            }
            
            temperature *= coefficient;
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
    
    /**
     * Transformation locale par échange de 2 reines
     * @param echiquier
     * @param ligne Ligne de la reine à inverser
     * @param colonne Colonne de la reine à inverser
     * @return Echiquier avec 2 reines inversées
     */
//    public List<Integer> transformation(List<Integer> echiquier, int ligne, int colonne) // TODO permettre plus de paramètrage, déplacer dans optimisation
//    {
//        // Récupération de la ligne associée à la colonne choisie
//        int ligneIntervertible = n + 1;
//        for (int i = 0; i < n; i++) {
//            if (echiquier.get(i) == colonne) {
//                ligneIntervertible = i;
//                break;
//            }
//        }
//
//        // Interversion
//        echiquier.set(ligneIntervertible, echiquier.get(ligne)); // throws a NullPointerException if reines has not the colonne
//        echiquier.set(ligne, colonne);
//        
//        return echiquier;
//    }
//
//    @Override
    public List<List<Integer>> transformationEchange(List<Integer> reines) {
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

    ///// Transformation par insertion-décalage
    public List<List<Integer>> transformationInsertionDecalage(List<Integer> reines) {
        int nbVoisins = 0;
        List<List<Integer>> voisins = new ArrayList();

        // Pour chaque reine
        for (int ligne = 0; ligne < n; ligne++) {
            int colonne = reines.get(ligne);

            // On la déplace à index si index différent de colonne
            for (int index = 0; index < n; index++) {

                if (index != colonne) {
                    System.out.println("reine " + colonne);
                    System.out.println("décalée à " + index);
                    
                    List<Integer> voisin = new ArrayList(reines);
                    voisin.remove(ligne);
                    
                    
                    if (index > voisin.size()) {
                        voisin.add(colonne);
                    } else {
                        voisin.add(index, colonne);
                    }
                    
                    afficherEchiquier(voisin);
                    if (!voisins.contains(voisin)) {
                        voisins.add(voisin);
                    }
                }
            }
            
            
        }

        if (verbose) {
            System.out.println("Nb voisins trouvés : " + nbVoisins);
        }

        return voisins;
    }

    @Override
    List<List<Integer>> calculerVoisins(List<Integer> reines) {
//        return this.transformationEchange(reines);
        return this.transformationInsertionDecalage(reines);
    }
}
