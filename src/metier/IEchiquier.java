
package metier;

import java.util.List;
import java.util.Map;
import javafx.util.Pair;

/**
 *
 * @author Mélanie DUBREUIL
 * @author Ophélie EOUZAN
 */
public interface IEchiquier {

    public void initialisationRandom();
    public void initialisationOptimisee();
    public void initialisationRandomOptimisee();

    /**
     * Calcule le nombre de conflits présents sur l'instance de l'échiquier
     * @return int
     */
    public int calculeConflits();
    
    public void afficherEchiquier();
    
    public Map<Pair<Integer, Integer>, List<Integer>> getVoisins();
    
    public void getVoisin(Dame origine, Dame voisine);
    
    public void reset(Dame origine, Dame voisine);
}
