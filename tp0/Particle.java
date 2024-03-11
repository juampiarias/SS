public class Particle {
    private int id;
    private float x;
    private float y;
    private float radius;
    private List<Particle> neighbors;

    public Particle(int id, float x, float y, float radius) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.neighbors = new ArrayList();
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

    public void isNeighbor(Particle p, float rc) {
        this.isNeighbor = Math.sqrt(Math.pow(this.x - p.getX(), 2) + Math.pow(this.y - p.getY(), 2)) < rc + this.radius + p.getRadius();
        neighbors/
    }

}
