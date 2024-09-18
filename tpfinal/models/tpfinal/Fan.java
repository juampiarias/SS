package tpfinal;

public class Fan extends Person{

    double Ag = 8;
    double Bg = 4;
    double Ac = 8;
    double Bc = 1;
    Vector target;
    boolean success;

    public Fan(int id, double x, double y, double vx, double vy, double radius, double mass, double desired, double tau) {
        super(id, x, y, vx, vy, radius, mass, desired, tau);
        target = new Vector(0, 0);
        success = false;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public void calculateDrivingForce () {
        //target center
        Vector vector = new Vector(0-rx[0], 0-ry[0]);
        double distance = vector.magnitude();
        vector = vector.normalize();
        double scalar = Ac*Math.exp(-distance/Bc);
        vector = vector.scalar(scalar);
        target = target.add(vector);

        if (distance < 2*radius) {
            this.success = true;
        }

        vector = new Vector(target.x-rx[0], target.y-ry[0]);
        Vector normal = vector.normalize();

        vector = normal.scalar(desired);
        vector = vector.subtract(new Vector(rx[1], ry[1]));
        vector = vector.scalar(mass/tau);

        forces = forces.add(vector);
        target = new Vector(0,0);
    }

    @Override
    public void calculateDrivingForce (Person p) {
        Vector vector = new Vector(rx[0]-p.rx[0], ry[0]-p.ry[0]);
        double distance = vector.magnitude();
        vector = vector.normalize();

        double scalar = Ag*Math.exp(-distance/Bg);

        vector = vector.scalar(scalar);
        if (!success) {
            target = target.add(vector);
        }

        if (distance < radius+p.radius){
            this.dead = true;
            p.dead = true;
        }
    }
}
