package metier;

/**
 *
 * @author Mélanie DUBREUIL, Ophélie EOUZAN - POLYTECH LYON - 4APP
 */

public class Dame implements Cloneable {
    private final int x, y;

    public Dame(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    @Override
    public Dame clone() throws CloneNotSupportedException {
        return (Dame) super.clone();
    }
}
