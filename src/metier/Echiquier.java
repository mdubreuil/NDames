package metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * 
 */

public class Echiquier implements IEchiquier {
    private boolean estSolution;
    private Map<Integer,List<Integer>> listeColonne;
    private Map<Integer,List<Integer>> listeLigne;
    private int nbConflits;
    private int tailleEchiquier;
    
    /**
     * @deprecated 
     */
    private List<Dame> dames;

    public Echiquier(int tailleEchiquier) {
        this.tailleEchiquier = tailleEchiquier;
        this.listeColonne = new HashMap();
        this.listeLigne = new HashMap();

//        for (int i = 1; i < tailleEchiquier; i++) {
//            listeColonne.put(i, new ArrayList());
//            listeLigne.put(i, new ArrayList());
//        }
    }
    
    /**
     * @deprecated 
     * @param x
     * @param y
     * @return 
     */
    public Dame getDame(int x, int y) {
        for (Dame dame : dames) {
            if (dame.getX() == x  && dame.getY() == y) {
                return dame;
            }
        }
        return null;
    }

    @Override
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
    
    private int calculeConflitsDiagonale(int colonne, int ligne) {
        int conflits = 0;
        int x = 0, y = 0;

        // Diagonale haut-gauche
        for (x = ligne - 1, y = colonne - 1; x > 0 && y > 0 && x <= tailleEchiquier && y <= tailleEchiquier; x--, y--) {
            if (listeColonne.get(y).size() > 0) {
                for (int i : listeColonne.get(y)) {
                    if (x == i) conflits++;
                }
            }
        }

        // Diagonale haut-droite
        for (x = ligne - 1, y = colonne + 1; x > 0 && y > 0 && x <= tailleEchiquier && y <= tailleEchiquier; x--, y++) {
            if (listeColonne.get(y).size() > 0) {
                for (int i : listeColonne.get(y)) {
                    if (x == i) conflits++;
                }
            }
        }
        
        // Diagonale bas-gauche
        for (x = ligne + 1, y = colonne - 1; x > 0 && y > 0 && x <= tailleEchiquier && y <= tailleEchiquier; x++, y--) {
            if (listeColonne.get(y).size() > 0) {
                for (int i : listeColonne.get(y)) {
                    if (x == i) conflits++;
                }
            }
        }
        
        // Diagonale bas-droite
        for (x = ligne + 1, y = colonne + 1; x > 0 && y > 0 && x <= tailleEchiquier && y <= tailleEchiquier; x++, y++) {
            if (listeColonne.get(y).size() > 0) {
                for (int i : listeColonne.get(y)) {
                    if (x == i) conflits++;
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
    
    @Override
    public void initialisationRandom() {
        int min = 1, max = tailleEchiquier;
        
        for (int colonne = min; colonne <= tailleEchiquier; colonne++) {
            int ligne = min + (int)(Math.random() * ((max - min) + 1));
            
            List<Integer> lignes = listeColonne.getOrDefault((Integer)colonne, new ArrayList());
            List<Integer> colonnes = listeLigne.getOrDefault((Integer)ligne, new ArrayList());
            lignes.add(ligne);
            colonnes.add(colonne);
            
            this.listeColonne.putIfAbsent(colonne, lignes);
            this.listeLigne.putIfAbsent(ligne, colonnes);
        }
    }

    @Override
    public void initialisationOptimisee() {
        List<Dame> lDamesInitiale = new ArrayList(); // Deprecated
        Map<Integer, List<Integer>> lColonne = new HashMap();//this.getListeColonne();
        Map<Integer, List<Integer>> lLigne = new HashMap();//this.getListeLigne();
                
        int nbPair = 2, nbImpair = 1, indiceColonne = 1;
        
        // TODO : A optimiser ?
        while (indiceColonne < tailleEchiquier/2 + 1){
            Dame d = new Dame(indiceColonne,indiceColonne,nbImpair);
            lDamesInitiale.add(d);
            ajoutHashMap(lColonne,lLigne,indiceColonne,nbImpair);
            nbImpair = nbImpair + 2;
            indiceColonne++;
        }
        
        while (indiceColonne <= tailleEchiquier){
            Dame d = new Dame(indiceColonne,indiceColonne,nbPair);
            lDamesInitiale.add(d);
            ajoutHashMap(lColonne,lLigne,indiceColonne,nbPair);
            nbPair = nbPair + 2;
            indiceColonne++;
        }
        
        if((tailleEchiquier % 2 == 1) && (indiceColonne == tailleEchiquier + 1)){
            Dame d = new Dame(tailleEchiquier,tailleEchiquier,tailleEchiquier);
            lDamesInitiale.add(d);
            ajoutHashMap(lColonne,lLigne,indiceColonne,tailleEchiquier);
        }
        
        this.setListeColonne(lColonne);
        this.setListeLigne(lLigne);
        this.setDames(lDamesInitiale);
    }

    @Override
    public void initialisationRandomOptimisee() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void ajoutHashMap(Map<Integer, List<Integer>> listeColonne, Map<Integer, List<Integer>> listeLigne, int indiceColonne, int indiceLigne){
        List<Integer> lIndicesColonnes =  new ArrayList<>();
        List<Integer> lIndicesLignes =  new ArrayList<>();
        
        lIndicesColonnes.add(indiceColonne);
        lIndicesLignes.add(indiceLigne);
        
        listeColonne.put(indiceColonne, lIndicesColonnes);
        listeColonne.put(indiceLigne, lIndicesLignes);
    }

    @Override
    public void afficherEchiquier() {
        System.out.println("   ---------------------------------");
        for (int ligne = 1; ligne <= tailleEchiquier; ligne++) {
            System.out.print(ligne + ": |");
            for (int colonne = 1; colonne <= tailleEchiquier; colonne++) {
                if (listeLigne.get(ligne) != null && listeLigne.get(ligne).contains((Integer)colonne)) {
                    System.out.print("_X_|");
                } else {
                    System.out.print("___|");
                }
            }
            System.out.println("\n   ---------------------------------");
        }
    }
}
