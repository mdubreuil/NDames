
package metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */
public class EchiquierSimple implements IEchiquier {
    
    protected int n;
    protected List<Integer> reines = new ArrayList(); // indices = lignes ; valeurs = colonnes

    public EchiquierSimple(int n) {
        this.n = n;
    }

    @Override
    public void initialisationRandom() {
        Random rand = new Random();
        List<Integer> indicesUsed = new ArrayList();
        for (int i = 0; i < n; i++) {
            int randomValue;
            do {
                randomValue = rand.nextInt(n);
            } while (indicesUsed.contains(randomValue));

            reines.add(randomValue);
            indicesUsed.add(randomValue);
        }
    }

    @Override
    public void initialisationOptimisee() {
        this.initialisationRandom();
    }

    @Override
    public void initialisationRandomOptimisee() {
        this.initialisationRandom();
    }

    @Override
    public int calculerConflits() {
        return calculerConflits(reines);
    }
    
    @Override
    public int calculerConflits(List<Integer> voisin) {
        int nbConflitsTotal = 0;
        
        for (int ligne = 0; ligne < n; ligne++) {
            nbConflitsTotal += this.calculeConflitsDiagonale(ligne, voisin.get(ligne));
        }

        return nbConflitsTotal;
    }

    @Override
    public void afficherEchiquier() {
//        System.out.println("   ---------------------------------");
        for (int ligne = 0; ligne < n; ligne++) {
            System.out.print(ligne + ": |");
            for (int colonne = 0; colonne < n; colonne++) {
                if (colonne == reines.get(ligne)) {
                    System.out.print("_X_|");
                } else {
                    System.out.print("___|");
                }
            }
//            System.out.println("\n   ---------------------------------");
        }
    }

    @Override
    public /*Map<Pair<Integer, Integer>,*/ List<List<Integer>> calculerVoisins() {
        int nbVoisins = 0;
        Random rand = new Random();
        //Map<Pair<Integer, Integer>, List<Integer>> voisins = new HashMap();
        List<List<Integer>> voisins = new ArrayList();

        for (int ligne = 0; ligne < n; ligne++) {

            List<Integer> voisin = new ArrayList(reines);
            List<Integer> used = new ArrayList();
            int randomColonne, colonne = voisin.get(ligne);
            
            do {
                randomColonne = rand.nextInt(n);
            } while (used.contains(randomColonne));
            used.add(randomColonne);
            
            int randomLigne = n + 1; // throws a NullPointerException if reines has not the colonne
            for (int i = 0; i < n; i++) {
                if (voisin.get(i) == randomColonne) {
                    randomLigne = i;
                    break;
                }
            }

            voisin.set(randomLigne, colonne);
            voisin.set(ligne, randomColonne);
            
            if (voisins.contains(voisin)) { // Eviter les doublons
                continue;
            }

            voisins.add(/*new Pair(ligne, colonne), */voisin);
            nbVoisins++;
        }
        
        System.out.println("Nb voisins trouvés : " + nbVoisins);
        
        return voisins;
    }

    @Override
    public void getVoisin(Dame origine, Dame voisine) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reset(Dame origine, Dame voisine) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private int calculeConflitsDiagonale(int ligne, int colonne) {
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

    // Getters & setters
    public List<Integer> getReines() {
        return reines;
    }
}
