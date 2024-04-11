package tp3;

public class Particle {

    private final int id;
    private double rx;
    private double ry;
    private double vx;
    private double vy;
    private final double mass;
    private final double radius;
    private final double L;
    private final double centralR;

    Particle (int id, double rx, double ry, double vx, double vy, double mass, double radius, double L, double centralR) {
        this.id = id;
        this.rx = rx;
        this.ry = ry;
        this.vx = vx;
        this.vy = vy;
        this.mass = mass;
        this.radius = radius;
        this.L = L;
        this.centralR = centralR;
    }

    // Time collision with vertical wall
    public double collidesX () {
        if (vx < 0)
            return (radius - rx) / vx;
        if (vx > 0)
            return (L -radius - rx) / vx;

        return Double.POSITIVE_INFINITY;
    }

    // Time collision with horizontal wall
    public double collidesY () {
        if (vy < 0)
            return (radius - ry) / vy;
        if (vy > 0)
            return (L - radius - ry) / vy;

        return Double.POSITIVE_INFINITY;
    }

    // Time collision with central sphere
    public double collidesSphere () {
        double centerX = L/2, centerY = L/2;
        double dx = rx - centerX, dy = ry - centerY;
        double dvx = vx, dvy = vy;

        double dvr = (dvx * dx) + (dvy * dy);

        if (dvr >= 0) {
            return Double.POSITIVE_INFINITY;
        }

        double drr = (dx * dx) + (dy * dy);
        double dvv = (dvx * dvx) + (dvy * dvy);
        double sigmaSquared = (radius + centralR) * (radius + centralR);
        double d = (dvr * dvr) - ((dvv)*(drr - sigmaSquared));

        if (d < 0) {
            return Double.POSITIVE_INFINITY;
        } else {
            return -(dvr + Math.sqrt(d)) / dvv;
        }

    }

    // Time collision with particle
    public double collides (Particle p) {
        double dx = rx - p.rx, dy = ry - p.ry;
        double dvx = vx - p.vx, dvy = vy - p.vy;

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

    // simulate collision with vertical wall
    public void bounceX () {
        vx = -vx;
    }

    // simulate collision with horizontal wall
    public void bounceY () {
        vy = -vy;
    }

    // simulate collision with central sphere
    public void bounceSphere () {
        double alpha = Math.atan(vy/vx);
        double vn = vx*Math.cos(alpha) + vy*Math.sin(alpha);
        double vt = vy*Math.cos(alpha) - vx*Math.sin(alpha);
        vn = -vn;
        vx = vn*Math.cos(alpha) - vt*Math.sin(alpha);
        vy = vn*Math.sin(alpha) + vt*Math.cos(alpha);
    }

    // simulate collision with particle p
    public void bounce (Particle p) {
        double dx = rx - p.rx, dy = ry - p.ry;
        double dvx = vx - p.vx, dvy = vy - p.vy;

        double dvr = (dvx * dx) + (dvy * dy);

        double sigma = radius + p.radius;

        double J = (2*mass*p.mass*dvr)/(sigma*(mass+p.mass));
        double Jx = (J*dx)/sigma, Jy = (J*dy)/sigma;

        this.vx -= (Jx/mass);
        this.vy -= (Jy/mass);
        p.vx += (Jx/p.mass);
        p.vy += (Jy/p.mass);
    }

    // simulate time passing
    public void advance (double dt) {
        this.rx += this.vx * dt;
        this.ry += this.vy * dt;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + ";" + rx + ";" + ry + ";" + vx + ";" + vy + ";" + mass + ";" + radius + "\n";
    }
}
