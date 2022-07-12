import java.awt.*;

public class Helium extends AstroObject {
    public Helium(int x, int y) {
        setMass(1.008);
        setSize(1);
        setDensity(1);
        setCoords(x, y);
        setColor(Color.BLUE);
        setName("Hydrogen - (" + getxPos() + ", " + getyPos() + ")");
    }
}
