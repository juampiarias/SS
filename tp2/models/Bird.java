import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class Bird {
    private int id;
    private double x;
    private double y;
    private double radius;
    private double angle;
    private double sinAngleAvg;
    private double cosAngleAvg;
    private int angleCounter;
    private double v;
    private double L;
    private double eta;
    private Random randomGenerator;

    public Bird(int id, double x, double y, double radius, double angle, double v, double L, double eta) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.angle = angle;
        this.angleCounter = 1;
        this.sinAngleAvg = 0;
        this.cosAngleAvg = 0;
        this.addAngle(angle);
        this.v = v;
        this.L = L;
        this.eta = eta;
        this.randomGenerator = new Random();
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }

    public double getAngle() {
        return angle;
    }

    public double getV() {
        return v;
    }

    public void setX(double x) {
        if (x<0) {
            this.x = x+L;
        } else {
            this.x = x;
        }
    }

    public void setY(double y) {
        if (y<0) {
            this.y = y+L;
        } else
            this.y = y;
    }

    public void setAngle() {
        this.sinAngleAvg/=this.angleCounter;
        this.cosAngleAvg/=this.angleCounter;

        this.angle = Math.toDegrees(Math.atan2(this.sinAngleAvg, this.cosAngleAvg));
        double sound = randomGenerator.nextDouble(-eta/2, eta/2);
        this.angle = (this.angle + sound)%360;
        if (this.angle < 0) {
            this.angle+=360;
        }
    }

    public void addAngle(double angle) {
        double aux = Math.toRadians(angle);
        this.sinAngleAvg += Math.sin(aux);
        this.cosAngleAvg += Math.cos(aux);
    }

    public void addNeighbor(Bird b) {
        this.addAngle(b.getAngle());
        this.angleCounter += 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bird bird)) return false;
        return getId() == bird.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void isNeighbor(Bird p, double rc, double increaseX, double increaseY) {
        boolean isNeighbor = !this.equals(p) &&
                Math.sqrt(Math.pow(this.x - p.getX() + increaseX, 2) + Math.pow(this.y - p.getY() + increaseY, 2)) < rc + this.radius + p.getRadius();
        if (isNeighbor) {
            this.addNeighbor(p);
            p.addNeighbor(this);
        }
    }

    public void isNeighborCorner(Bird p, double rc, double increaseX, double increaseY) {
        boolean isNeighbor = !this.equals(p) &&
                Math.sqrt(Math.pow(this.x - p.getX() + increaseX, 2) + Math.pow(p.getY() - this.y + increaseY, 2)) < rc + this.radius + p.getRadius();
        if (isNeighbor) {
            this.addNeighbor(p);
            p.addNeighbor(this);
        }
    }
    public void moveBird() {
        this.setAngle();

        double aux = Math.toRadians(this.angle);
        this.setY((this.y + Math.sin(aux)*this.v)%this.L);
        this.setX((this.x + Math.cos(aux)*this.v)%this.L);

        this.sinAngleAvg = 0;
        this.cosAngleAvg = 0;
        this.addAngle(this.angle);
        this.angleCounter = 1;
    }

    @Override
    public String toString() {
        return id +
                ";" + x +
                ";" + y +
                ";" + angle +
                ";" + radius +
                ";" + v + '\n';
    }
}


