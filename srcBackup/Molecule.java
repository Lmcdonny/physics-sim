import java.awt.*;
import java.util.ArrayList;

public abstract class Molecule {
    // Var declaration
    // Made gravity stronger (should be 6.67 * Math.pow(10, -11))
    // Also Changed setMass to increase the mass automatically
    public double G = 6.67 * Math.pow(10, -6);
    private String name;
    private double size;
    private double mass;
    private double density;
    private double xPos;
    private double yPos;
    private Color color;
    private Vector velocityVector = new Vector(0,0);
    private ArrayList<Vector> vectorList = new ArrayList<>();
    private boolean immobile = false;

    protected Molecule() {
    }

    // Setters and Getters

    public boolean isImmobile() {
        return immobile;
    }

    public void toggleImmobile() {
        immobile = !immobile;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setMass(double mass) {
        this.mass = mass;
    }
    public double getMass() {
        return mass;
    }
    public void setSize(double size) {
        this.size = size;
    }
    public double getSize() {
        return size;
    }
    public void setDensity(double density) {
        this.density = density;
    }
    public double getDensity() {
        return density;
    }
    public void setCoords(double x, double y) {
        this.xPos = x;
        this.yPos = y;
    }
    public double getxPos() {
        return xPos;
    }
    public double getyPos() {
        return yPos;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public Color getColor() {
        return color;
    }
    public Vector getVelocityVector() {
        return velocityVector;
    }
    public void setVelocityVector(Vector velocityVector) {
        this.velocityVector = velocityVector;
    }

    // Calculation functions
    public static double getDistance(Molecule mol1, Molecule mol2) {
        return Math.sqrt(
                Math.pow((mol1.getxPos() - mol2.getxPos()), 2) +
                Math.pow((mol1.getyPos() - mol2.getyPos()), 2));
    }

    public double getAngle(Molecule mol2) {
        Molecule mol1 = this;
        double yDiff = mol2.getyPos() - mol1.getyPos();
        double dist = getDistance(mol1, mol2);
        double angle = Math.asin(yDiff / dist);
        if (mol2.getxPos() - mol1.getxPos() < 0) {
            return 2 * Math.PI - angle;
        } else {
            return angle;
        }
    }

    public double getForce(Molecule mol2) {
        Molecule mol1 = this;
        return (G * mol1.mass * mol2.mass) / Math.pow(getDistance(mol1, mol2), 2);
    }


    // Vector control functions
    public void addVector(Molecule mol2) {
        double magnitude = this.getForce(mol2);
        double angle = this.getAngle(mol2);
        Vector vector = new Vector(angle, magnitude);
        vector.setName("Vector: " + this.name + "--> " + mol2.name);
        this.vectorList.add(vector);
    }

    public void setVelocityVector() {
        /*
        avgMag is based on the angles ; 0 Rad and PI rads cancel out; formula is:
        [v1mag * cos(v1angle) + v2mag * cos(v2angle) + ...] / totalVectors
        avgAngle is based on the magnitude of the angle as well ; formula is:
        [(v1angle * v1mag) + (v2angle * v2mag) + ...] / totalMag
        */
        double totalMag = 0;
        double angleNumerator = 0;
        double magNumerator = 0;
        for (Vector v : vectorList) {
            double m = v.getMagnitude();
            double a = v.getAngle();
            totalMag += m;
            angleNumerator += m * a;
            magNumerator += m * Math.cos(a);
        }
        double avgMag = magNumerator / vectorList.size();
        double avgAngle = angleNumerator / totalMag;
        Vector vector = new Vector(avgAngle, avgMag);
        vector.setName(String.format("Velocity Vector of %s", this.name));
        this.velocityVector = vector;
    }

    public String VectorsListString() {
        ArrayList<Vector> v = this.vectorList;
        String s = this.name + "\n";
        for (Vector vector : v) {
            s += vector.toString().concat("\n");
        }
        s += getVelocityVector().toString().concat("\n");
        return s;
    }

    // Calculate vectors to every other molecule in an arraylist
    public void calculateVectors(ArrayList<Molecule> molecules) {
        for (Molecule m : molecules) {
            if ((this.xPos != m.xPos) || (this.yPos != m.yPos)) {
                this.addVector(m);
            }
        }
        setVelocityVector();
    }
    public Color getGradientColor(double mass) {
        double colorPercent = 255 * (1 - (getMass() / ((20) * 6 * Math.pow(10, 6))));
        int r = 230;
        int g = Math.max(Math.min(255, (int)colorPercent), 0);
        int b = Math.max(Math.min(255, (int)colorPercent), 0);
        return new Color(r, g, b);
    }

    public boolean collision(Molecule mol2) {
        Molecule mol1 = this;
        if (this.xPos != mol2.xPos) {
            if (getDistance(mol1, mol2) <= mol1.size/2 + mol2.size/2) {
                if (mol1.getMass() > mol2.getMass()) {
                    mol1.combine(mol2);
                    setColor(getGradientColor(mol1.mass));
                    mol1.velocityVector.averageVel(mol2.velocityVector);
                    return true;
                }
            }
        }
        return false;
    }
    public void combine(Molecule m) {
        this.setMass(this.getMass() + m.getMass());
        this.setSize(this.getSize() + m.getSize());
        this.setDensity(this.getDensity() + m.getDensity());
    }

    public void emptyVectors() {
        this.vectorList.clear();
    }

    public void updateCoords() {
        // Math.pow in this instance is just a multiplier to speed up their movement
        this.xPos = Math.max(
                Math.min(
                xPos + Math.cos(velocityVector.getAngle()) * velocityVector.getMagnitude() / getMass(), 990),
                10);
        this.yPos = Math.max(
                Math.min(
                yPos + Math.sin(velocityVector.getAngle()) * velocityVector.getMagnitude() / getMass(), 590),
                10);
        emptyVectors();
        vectorList.add(velocityVector);
    }
}
