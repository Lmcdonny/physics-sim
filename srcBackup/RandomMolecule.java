import org.w3c.dom.css.RGBColor;

import java.awt.*;

public class RandomMolecule extends Molecule {
    /* My plan for this is to have a palette with a gradient the size of the range of masses, so that a smaller mass
    atom will be a cooler color and the larger mass atoms will have a warmer color */
    final double MASSMAX = 6 * Math.pow(10, 6);

    RandomMolecule(int xMax, int yMax) {
        setCoords((Math.random() * xMax), (Math.random() * yMax));
        setMass(4 + Math.random() * MASSMAX);
        setSize(2 + getMass() * Math.pow(10, -6));
        setDensity(1);
        setName(String.format("%.2fu", (float)getMass()) + " (" + getxPos() + ", " + getyPos() + ")");
        double colorPercent = 255 * (1 - (getMass() / ((40) * 6 * Math.pow(10, 6))));
        int r = 230;
        int g = Math.min(255, (int)colorPercent);
        int b = Math.min(255, (int)colorPercent);
        setColor(new Color(r, g, b));
    }
}
