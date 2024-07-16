package tpfinal;

public class Guard extends Person{
    public Guard(int id, double x, double y, double vx, double vy, double radius, double mass, double desired, double tau) {
        super(id, x, y, vx, vy, radius, mass, desired, tau);
    }

    @Override
    public void calculateDrivingForce (Person p) {
        Vector vector = new Vector(p.rx[0]-rx[0], p.ry[0]-ry[0]);
        Vector normal = vector.normalize();

        vector = normal.scalar(desired);
        vector = vector.subtract(new Vector(rx[1], ry[1]));
        vector = vector.scalar(mass/tau);

        forces = forces.add(vector);
    }
}
