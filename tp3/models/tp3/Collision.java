package tp3;


public class Collision {

    private double t;
    private Particle a;
    private Particle b;
    private CollisionType type;

    Collision(double t, Particle a, Particle b, CollisionType type) {
        this.t = t;
        this.a = a;
        this.b = b;
        this.type = type;
    }

    public double getT() {
        return t;
    }

    public CollisionType getType() {
        return type;
    }

    public Particle getA() {
        return a;
    }

    public Particle getB() {
        return b;
    }

    public void setA(Particle a) {
        this.a = a;
    }

    public void setB(Particle b) {
        this.b = b;
    }

    public void setT(double t) {
        this.t = t;
    }

    public void setType(CollisionType type) {
        this.type = type;
    }

}
