public class Vector {
    private String name;
    private double angle;
    private double magnitude;


    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public double getMagnitude() {
        return magnitude;
    }
    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }
    public double getAngle() {
        return angle;
    }
    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void averageVel(Vector v) {
        double totalMag = getMagnitude();
        double angleNumerator = getMagnitude() * getAngle();
        double magNumerator = getMagnitude() * Math.cos(getAngle());
        double m = v.getMagnitude();
        double a = v.getAngle();
        totalMag += m;
        angleNumerator += m * a;
        magNumerator += m * Math.cos(a);
        double avgMag = magNumerator / 2;
        double avgAngle = angleNumerator / totalMag;
        Vector vector = new Vector(avgAngle, avgMag);
    }
    public static void reverseVel(Molecule m) {
        Vector oldVector = m.getVelocityVector();
        m.setVelocityVector(new Vector(oldVector.angle + Math.PI, oldVector.magnitude * 2));
    }

    @Override
    public String toString() {
        String s = "";
        s += this.name +
            "  |  Angle(Rads): " + (float)this.getAngle() +
            "  |  Magnitude: " + (float)this.getMagnitude();
        return s;
    }

    public Vector(double angle, double magnitude) {
        setAngle(angle);
        setMagnitude(magnitude);
    }
}
