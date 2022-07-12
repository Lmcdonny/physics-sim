public class CustomMolecule extends Molecule {
    CustomMolecule(double x, double y, double mass, double size) {
        setCoords(x, y);
        setMass(mass);
        setSize(size);
    }
    CustomMolecule(double x, double y, double mass, double size, Vector initialVector) {
        setCoords(x, y);
        setMass(mass);
        setSize(size);
        setVelocityVector(initialVector);
    }
}
