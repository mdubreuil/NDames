
package metier;

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
}
