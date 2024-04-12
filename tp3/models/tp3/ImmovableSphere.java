package tp3;

public class ImmovableSphere extends Particle{

    ImmovableSphere(int id, double rx, double ry, double vx, double vy, double mass, double radius, double L) {
        super(id, rx, ry, vx, vy, mass, radius, L);
    }

    @Override
    public double collides (Particle p) {
        double dx = p.rx - rx, dy = p.ry - ry;
        double dvx = p.vx, dvy = p.vy;

        double dvr = (dvx * dx) + (dvy * dy);

        if (dvr >= 0) {
            return Double.POSITIVE_INFINITY;
        }

        double drr = (dx * dx) + (dy * dy);
        double dvv = (dvx * dvx) + (dvy * dvy);
        double sigmaSquared = (radius + p.radius) * (radius + p.radius);
        double d = (dvr * dvr) - ((dvv)*(drr - sigmaSquared));

        if (d < 0) {
            return Double.POSITIVE_INFINITY;
        } else {
            return -(dvr + Math.sqrt(d)) / dvv;
        }
    }


    @Override
    public void bounce (Particle p) {
        double dx = p.rx - rx, dy = p.ry - ry;
        double dvx = p.vx, dvy = p.vy;

        double dvr = (dvx * dx) + (dvy * dy);

        double sigma = p.radius + radius;

        double J = (2*p.mass*dvr)/(sigma);
        double Jx = (J*dx)/sigma, Jy = (J*dy)/sigma;

        p.vx -= (Jx/p.mass);
        p.vy -= (Jy/p.mass);
    }

    @Override
    // simulate time passing
    public void advance (double dt) {
        return;
    }
}
