import java.awt.*;

public class RandomAstroObject extends AstroObject {
    // Mass in Solar Masses
    final double MASSMAX = 12;
    final double MASSSCALE = Math.pow(10, 11);

    RandomAstroObject(int xMax, int yMax) {
        setCoords((Math.random() * xMax), (Math.random() * yMax));
        setG(6 * Math.pow(10, -11));
        setMass(Math.random() * MASSMAX * MASSSCALE);
        setSize(10 * (getMass() / (MASSMAX * MASSSCALE)) + 4);
        setDensity(1);
        setName(String.format("%.2fu", (float)getMass()) + " (" + getxPos() + ", " + getyPos() + ")");
        double colorPercent = 255 * (1 - (getMass() / (MASSMAX * MASSSCALE * 40)));
        int r = 230;
        int g = Math.min(255, (int)colorPercent);
        int b = Math.min(255, (int)colorPercent);
        setColor(new Color(r, g, b));
    }
}
