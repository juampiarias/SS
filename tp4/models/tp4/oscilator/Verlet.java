package tp4.oscilator;

public class Verlet implements Algorithm{

    final double m;
    final double k;
    final double gamma;
    final double dt;

    double r;
    double v;
    double a;
    double rBefore;

    Verlet(double r, double v, double m, double k, double gamma, double dt) {
        this.r = r;
        this.v = v;
        this.m = m;
        this.k = k;
        this.gamma = gamma;
        this.dt = dt;

        this.a = OscillatorForce(r, v, m, k, gamma);
        this.rBefore = EulerPosition(r, v, a, -dt);
    }

    @Override
    public void iterate() {
        double rAfter = 2*r - rBefore + dt*dt*a;
        v = (rAfter - rBefore)/(2*dt);

        rBefore = r;
        r = rAfter;
        a = OscillatorForce(r, v, m, k, gamma);
    }

    @Override
    public double getR() {
        return r;
    }
}
