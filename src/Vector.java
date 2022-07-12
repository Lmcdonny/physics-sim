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

    public static Vector avgVector(Vector v1, Vector v2) {
        double cos = Math.cos(v1.angle) * v1.getMagnitude() + v2.magnitude * Math.cos(v2.getAngle());
        double sin = Math.sin(v1.angle) * v1.getMagnitude() + v2.magnitude * Math.sin(v2.getAngle());
        double mag = (v1.getMagnitude() + v2.getMagnitude()) / 2;
        double angle = (v1.getAngle() + v2.getAngle()) / 2;
        if (Math.cos(angle) < 0 && cos > 0) {
            angle = Math.PI + angle;
        }
        if (sin < 0 && Math.sin(angle) > 0) {
            angle += Math.PI;
        }
        return new Vector(angle, mag);
    }

    public static void reverseVel(AstroObject a) {
        Vector oldVector = a.getVelocityVector();
        a.setVelocityVector(new Vector(oldVector.angle + Math.PI, oldVector.magnitude * 2));
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
