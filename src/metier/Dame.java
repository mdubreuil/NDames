package metier;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 * @deprecated 
 */

public class Dame {
    private int identifiant;
    private int x, y;

    public Dame(int identifiant, int x, int y) {
        this.identifiant = identifiant;
        this.x = x;
        this.y = y;
    }
    
    public int getIdentifiant() {
        return identifiant;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
