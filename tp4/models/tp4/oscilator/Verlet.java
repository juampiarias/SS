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

    // X-1  X0  X1  X2?
    //      V0  V1? x2-x0/2dt
    //      A0  A1?

    @Override
    public void iterate() {
        double rAfter = 2*r - rBefore + dt*dt*a;
//        double vAfter = EulerVelocity(v, a, dt);
        double vAfter = (rAfter - r)/dt;

        rBefore = r;
        r = rAfter;
        v = vAfter;
        a = OscillatorForce(r, v, m, k, gamma);
    }

    @Override
    public double getR() {
        return r;
    }
}
