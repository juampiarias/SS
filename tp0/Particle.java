public class Particle {
    private int id;
    private float x;
    private float y;
    private float radius;
    private List<Particle> neighbors;

    public Particle(int id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.isSelected = false;
    }

    public Particle(int id, float x, float y, boolean isSelected) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.isSelected = isSelected;
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

    public void isNeighbor(Particle p, float rc) {
        this.isNeighbor = Math.sqrt(Math.pow(this.x - p.getX(), 2) + Math.pow(this.y - p.getY(), 2)) < rc;
    }

}
