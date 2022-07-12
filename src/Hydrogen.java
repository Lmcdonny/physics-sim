import java.awt.*;

public class Hydrogen extends AstroObject {
    public Hydrogen(int x, int y) {
        setMass(4.002);
        setSize(1);
        setDensity(1);
        setCoords(x, y);
        setColor(Color.RED);
        setName("Hydrogen - (" + getxPos() + ", " + getyPos() + ")");
    }
}
