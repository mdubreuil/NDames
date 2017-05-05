package metier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */

public class Echiquier {
    private boolean estSolution;
    private List<Dame> dames;
    private Map<Integer,List<Integer>> listeColonne;
    private Map<Integer,List<Integer>> listeLigne;
    private int nbConflits;
    private int tailleEchiquier;
    private int typeInitialisation; // TODO : replacer par enum

    public Echiquier(int tailleEchiquier, int typeInitialisation) {
        this.tailleEchiquier = tailleEchiquier;
        this.typeInitialisation = typeInitialisation;
        this.listeColonne = new HashMap<>();
        this.listeLigne = new HashMap<>();
    }
    
    public Dame getDame(int x, int y) {
        for (Dame dame : dames) {
            if (dame.getX() == x  && dame.getY() == y) {
                return dame;
            }
        }
        
        return null;
    }
    
    public int calculeConflits() {
        int nbConflitsTotal = 0;
        
        for (Map.Entry<Integer, List<Integer>> entry : listeColonne.entrySet()) {
            if (entry.getValue().size() > 0) {
                int colonne = entry.getKey();
                for (Integer ligne : entry.getValue()) {
                    // Dame (colonne, ligne)
                    int conflits = 0;
                    
                    // 1. Conflits de ligne
                    conflits += listeLigne.get(ligne).size() - 1;
                    
                    // 2. Conflits de colonne
                    conflits += listeColonne.get(colonne).size() - 1;
                    
                    // 3. Conflits de diagonale
                    conflits += this.calculeConflitsDiagonale(colonne, ligne);
                    
                    nbConflitsTotal += conflits;
                }
            }
        }

        return nbConflitsTotal;
    }
    
    public int calculeConflitsDiagonale(int colonne, int ligne) {
        int conflits = 0;
        int x = 0, y = 0;

        // Diagonale haut-gauche
        for (x = ligne, y = colonne; x > 0 && y > 0 && x <= tailleEchiquier && y <= tailleEchiquier; x--, y--) {
            if (listeColonne.get(y).size() > 0) {
                for (int i : listeColonne.get(y)) {
                    if (y == i) conflits++;
                }
            }
        }

        // Diagonale haut-droite
        for (x = ligne, y = colonne; x > 0 && y > 0 && x <= tailleEchiquier && y <= tailleEchiquier; x--, y++) {
            if (listeColonne.get(y).size() > 0) {
                for (int i : listeColonne.get(y)) {
                    if (y == i) conflits++;
                }
            }
        }
        
        // Diagonale bas-gauche
        for (x = ligne, y = colonne; x > 0 && y > 0 && x <= tailleEchiquier && y <= tailleEchiquier; x++, y--) {
            if (listeColonne.get(y).size() > 0) {
                for (int i : listeColonne.get(y)) {
                    if (y == i) conflits++;
                }
            }
        }
        
        // Diagonale bas-droite
        for (x = ligne, y = colonne; x > 0 && y > 0 && x <= tailleEchiquier && y <= tailleEchiquier; x++, y++) {
            if (listeColonne.get(y).size() > 0) {
                for (int i : listeColonne.get(y)) {
                    if (y == i) conflits++;
                }
            }
        }
        
        return conflits;
    }
    
    public boolean isEstSolution() {
        return estSolution;
    }

    public List<Dame> getDames() {
        return dames;
    }

    public int getNbConflits() {
        return nbConflits;
    }

    public int getTailleEchiquier() {
        return tailleEchiquier;
    }

    public int getTypeInitialisation() {
        return typeInitialisation;
    }

    public void setDames(List<Dame> dames) {
        this.dames = dames;
    }
    
}
