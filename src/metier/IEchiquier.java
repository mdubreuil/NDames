
package metier;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Mélanie DUBREUIL
 * @author Ophélie EOUZAN
 */
public interface IEchiquier extends Cloneable {

    public void initialisationRandom();
    public void initialisationOptimisee();
    public void initialisationRandomOptimisee();

    /**
     * Calcule le nombre de conflits présents sur l'instance de l'échiquier
     * @return int
     */
    public int calculeConflits();
    
    public void afficherEchiquier();
    
    public Map<Dame, List<Dame>> getVoisins();
    
    public Echiquier getVoisin(Dame origine, Dame voisine);
}
