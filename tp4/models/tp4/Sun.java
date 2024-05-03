package tp4;

public class Sun extends Particle{

    Sun(double g, double m, double rx, double ry, double vx, double vy) {
        super(g, m, rx, ry, vx, vy);
    }

    @Override
    public void actualize(double dt) {
        fx = 0.0;
        fy = 0.0;
    }
}
