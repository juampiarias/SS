package tp4.oscilator;

public class Beeman implements Algorithm{

    final double m;
    final double k;
    final double gamma;
    final double dt;

    double r;
    double v;
    double a;
    double rBefore;
    double vBefore;
    double aBefore;

    public Beeman(double r, double v, double m, double k, double gamma, double dt) {
        this.m = m;
        this.k = k;
        this.gamma = gamma;
        this.dt = dt;

        this.r = r;
        this.v = v;
        this.a = OscillatorForce(r, v, m, k, gamma);
        this.rBefore = EulerPosition(r, v, a, -dt);
        this.vBefore = EulerVelocity(v, a, -dt);
        this.aBefore = OscillatorForce(rBefore, vBefore, m, k, gamma)/m;
    }

    @Override
    public void iterate() {
        double rAfter = r + v*dt + a*dt*dt*2/3 - aBefore*dt*dt/6;
        double vAfter = v + 1.5*a*dt - 0.5*aBefore*dt;
        double aAfter = OscillatorForce(rAfter, vAfter, m, k, gamma);
        vAfter = v + aAfter*dt/3 + a*dt*5/6 - aBefore*dt/6;

        rBefore = r;
        vBefore = v;
        aBefore = a;
        r = rAfter;
        v = vAfter;
        a = aAfter;
    }

    @Override
    public double getR() {
        return r;
    }
}
