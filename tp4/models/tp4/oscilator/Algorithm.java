package tp4.oscilator;

public interface Algorithm {

    public void iterate();
    public double getR();

    default double OscillatorForce(double r, double v, double m, double k, double gamma) {
        return (-k*r -gamma*v)/m;
    }

    default double EulerPosition(double r, double v, double a, double dt) {
        return r + dt*v + dt*dt*a/2;
    }

    default double EulerVelocity(double v, double a, double dt) {
        return v + dt*a;
    }
}
