import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Bird {
    private int id;
    private double x;
    private double y;
    private double radius;
    private double angle;
    private double neighborsAngleAvg;
    private int angleCounter;
    private double v;

    public Bird(int id, double x, double y, double radius, double angle, double v) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.angleCounter = 1;
        this.neighborsAngleAvg = angle;
        this.v = v;
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
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void addNeighbor(Bird b) {
        this.neighborsAngleAvg += b.getAngle();
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
        this.angle = neighborsAngleAvg/angleCounter;
        this.setY(Math.sin(this.angle)*this.v);
        this.setX(Math.cos(this.angle)*this.v);
        this.angleCounter = 1;
        this.neighborsAngleAvg = this.angle;
    }
}


