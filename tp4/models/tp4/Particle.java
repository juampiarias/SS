package tp4;

public class Particle {

    final double g;
    final double m;

    double rx;
    double ry;
    double vx;
    double vy;
    double fx;
    double fy;


    Particle(double g, double m, double rx, double ry, double vx, double vy) {
        this.g = g;
        this.m = m;

        this.rx = rx;
        this.ry = ry;
        this.vx = vx;
        this.vy = vy;
        this.fx = 0.0;
        this.fy = 0.0;
    }

    void add_Fx(double fx) {
        this.fx += fx;
    }
    void add_Fy(double fy) {
        this.fy += fy;
    }

     public void interact(Particle p) {

        double dd = Math.pow(p.rx - rx, 2) + Math.pow(p.ry - ry, 2);
        double d = Math.sqrt(dd);

        double Fn = g*p.m*m/dd;
        double Fx = Fn * (p.rx-rx)/d;
        double Fy = Fn * (p.ry-ry)/d;

        add_Fx(Fx);
        add_Fy(Fy);

        p.add_Fx(-Fx);
        p.add_Fy(-Fy);
     }

     public void actualize(double dt) {
        vx += fx*dt/m;
        vy += fy*dt/m;

        rx += vx*dt;
        ry += vy*dt;

        fx = 0.0;
        fy = 0.0;

     }

    @Override
    public String toString() {
        return rx +
                ", " + ry +
                ", " + vx +
                ", " + vy;
    }
}
