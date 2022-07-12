import java.awt.*;

public class CustomAstroObject extends AstroObject {
    // Scales; Mass 1 : .5 Solar Masses ; distance 1 : 100 Million Miles ; size 1 : 1 million miles
    CustomAstroObject(double x, double y, double mass, double size) {
        setCoords(x, y);
        setMass(mass);
        setSize(size);
    }
    CustomAstroObject(double x, double y, double mass, double size, Vector initialVector) {
        setCoords(x, y);
        setMass(mass);
        setSize(size);
        setVelocityVector(initialVector);
    }


    // Solar mass = 2x10^30 ; size 1 : 1/3 sun ~ 8400 mi ; distance 1 : 10^6 km
    // should be 156 from the sun
    public static AstroObject Earth(double x, double y) {
        AstroObject earth = new CustomAstroObject(x, y, 5.972 * Math.pow(10, 24), 12,
                new Vector(Math.PI/2, 107208000));
        earth.setColor(new Color(84, 86, 204));
        earth.setScale(Math.pow(10, 6));
        earth.setG(6 * Math.pow(10, -41));
        earth.setName("Earth");
        return earth;
    }

    // size; 1 : 25,000 mi
    public static AstroObject Sun(double x, double y) {
        AstroObject sun = new CustomAstroObject(x, y, 2 * Math.pow(10, 33), 36);
        sun.setColor(new Color(227, 150, 34));
        sun.setScale(Math.pow(10, 6));
        sun.setName("Sun");
        sun.setG(6 * Math.pow(10, -41));
        return sun;
    }
}