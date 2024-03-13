import java.util.*;

public class Particle {
    private int id;
    private float x;
    private float y;
    private float radius;
    private Set<Particle> neighbors;

    public Particle(int id, float x, float y, float radius) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.neighbors = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public void addNeighbor(Particle p) {
        this.neighbors.add(p);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Particle particle)) return false;
        return getId() == particle.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return id + ";" + this.getNeighbors() + '\n';
    }

    public String getNeighbors() {
        StringBuilder toReturn = new StringBuilder("[");
        neighbors.forEach(n -> toReturn.append(n.id).append(","));
        if (!neighbors.isEmpty())
            toReturn.deleteCharAt(toReturn.lastIndexOf(","));
        toReturn.append("]");
        return toReturn.toString();
    }

    public void isNeighbor(Particle p, float rc, float increaseX, float increaseY) {
        boolean isNeighbor = !this.equals(p) &&
                (Math.sqrt(Math.pow(this.x - p.getX(), 2) + Math.pow(this.y - p.getY(), 2)) < rc + this.radius + p.getRadius() ||
                Math.sqrt(Math.pow(this.x - p.getX() + increaseX, 2) + Math.pow(this.y - p.getY() + increaseY, 2)) < rc + this.radius + p.getRadius());
        if (isNeighbor) {
            this.addNeighbor(p);
            p.addNeighbor(this);
        }
    }

}
