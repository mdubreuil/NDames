package metier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<Integer, List<Integer>> getListeColonne() {
        return listeColonne;
    }

    public Map<Integer, List<Integer>> getListeLigne() {
        return listeLigne;
    }

    public void setListeColonne(Map<Integer, List<Integer>> listeColonne) {
        this.listeColonne = listeColonne;
    }

    public void setListeLigne(Map<Integer, List<Integer>> listeLigne) {
        this.listeLigne = listeLigne;
    }
    
}
