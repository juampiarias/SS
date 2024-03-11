import java.util.ArrayList;
import java.util.List;

public class Cell {

    final List<Particle> particles;
    float size;
    final int id;

    public Cell (float size, int id) {
        particles = new ArrayList<>();
        this.size = size;
        this.id = id;
    }

    public void addParticle(Particle p) {
        particles.add(p);
    }

    public void findNeighbors (Particle p, float rc) {
        particles.forEach(e -> e.isNeighbor(p, rc));
    }

}
