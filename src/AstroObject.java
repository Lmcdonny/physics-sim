import java.awt.*;
import java.util.ArrayList;

public abstract class AstroObject {
    // Var declaration
    // Made gravity stronger (should be 6.67 * Math.pow(10, -11))
    // Also Changed setMass to increase the mass automatically
//    public double G = 6.67 * Math.pow(10, -11);
    private double scale = 1;
    private double G;
    private String name;
    private double size;
    private double mass;
    private double density;
    private double xPos;
    private double yPos;
    private Color color;
    private Vector velocityVector = new Vector(0,0);
    private ArrayList<Vector> vectorList = new ArrayList<>();

    protected AstroObject() {
    }

    // Setters and Getters
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
        this.vectorList.add(velocityVector);
    }
    public void setScale(double scale) {
        this.scale = scale;
    }
    public double getScale() {
        return scale;
    }

    public double getG() {
        return G;
    }
    public void setG(double g) {
        G = g;
    }

    // Calculation functions
    public static double getDistance(AstroObject a1, AstroObject a2) {
        return Math.sqrt(
                Math.pow((a1.getxPos() - a2.getxPos()), 2) +
                Math.pow((a1.getyPos() - a2.getyPos()), 2));
    }

    public double getAngle(AstroObject a2) {
        AstroObject a1 = this;
        double yDiff = a2.getyPos() - a1.getyPos();
        double dist = getDistance(a1, a2);
        double angle = Math.asin(yDiff / dist);
        if (a2.getxPos() < a1.getxPos()) {
            return Math.PI - angle;
        } else {
            return angle;
        }
    }

    public static double getForce(AstroObject a1, AstroObject a2) {
        return (a1.G * a1.mass * a2.mass) / Math.pow(getDistance(a1, a2), 2);
    }


    // Vector control functions
    public void addVector(AstroObject a) {
        double magnitude = getForce(this ,a);
        double angle = this.getAngle(a);
        Vector vector = new Vector(angle, magnitude / this.getMass());
        vector.setName("Vector: " + getName() + "--> " + a.name);
        this.vectorList.add(vector);
    }

    public void setVelocityVector() {
        Vector v1 = velocityVector;
        double avgCos = 0;
        double avgSin = 0;
        double avgMag = 0;
        double avgAngle = 0;
        for (Vector v : vectorList) {
            if (v != velocityVector) {
                avgSin += v.getMagnitude() * Math.sin(v.getAngle());
                avgCos += v.getMagnitude() * Math.cos(v.getAngle());
                avgMag += v.getMagnitude();
                avgAngle += v.getAngle();
            }
        }
        avgAngle /= (vectorList.size() - 1);
        if (avgCos < 0 && Math.cos(avgAngle) > 0) {
            avgAngle += Math.PI;
        }
        if (avgSin < 0 && Math.sin(avgAngle) > 0) {
            avgAngle += Math.PI;
        }
        avgMag /= (vectorList.size() - 1);
        Vector v = new Vector(avgAngle, avgMag);
        v1 = Vector.avgVector(v1, v);
        this.velocityVector = v1;
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
    public void calculateVectors(ArrayList<AstroObject> astroObjects) {
        for (AstroObject m : astroObjects) {
            if (this != m) {
                this.addVector(m);
            }
        }
        setVelocityVector();
    }
    public void getGradientColor() {
        double colorPercent = 255 * (1 - this.mass / (40 * 12 * Math.pow(10, 11)));
        int r = 230;
        int g = Math.max(Math.min(255, (int)colorPercent), 0);
        int b = Math.max(Math.min(255, (int)colorPercent), 0);
        this.setColor(new Color(r, g, b));
    }

    public boolean collision(AstroObject a2) {
        AstroObject a1 = this;
        if (a1 != a2) {
            if (getDistance(a1, a2) <= a1.getSize()/2 + a2.getSize()/2) {
                if (a1.getMass() > a2.getMass()) {
                    a1.combine(a2);
                    a1.getGradientColor();
                    a1.velocityVector = Vector.avgVector(a1.velocityVector, a2.velocityVector);
                    return true;
                }
            }
        }
        return false;
    }
    public void combine(AstroObject a) {
        this.setMass(this.getMass() + a.getMass());
        this.setSize(this.getSize() + a.getSize());
        this.setDensity(this.getDensity() + a.getDensity());
    }

    public void emptyVectors() {
        this.vectorList.clear();
    }

    public void updateCoords() {
        // Math.pow in this instance is just a multiplier to speed up their movement
        this.xPos = Math.max(Math.min(
                (this.xPos + Math.cos(velocityVector.getAngle()) * velocityVector.getMagnitude()),
                980 * this.scale),
                20 * this.scale);
        this.yPos = Math.max(Math.min(
                (this.yPos + Math.sin(velocityVector.getAngle()) * velocityVector.getMagnitude()),
                580 * this.scale),
                20 * this.scale);
        emptyVectors();
        vectorList.add(velocityVector);
    }
}
