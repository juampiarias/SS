import java.util.ArrayList;
import java.util.List;

public class Cell {

    private final List<Particle> particles;
    private float size;
    final int id;

    public Cell (float size, int id) {
        this.particles = new ArrayList<>();
        this.size = size;
        this.id = id;
    }

    public void addParticle(Particle p) {
        particles.add(p);
    }

    public void findNeighbors (float rc) {
        //find neighbors in own cell
        Particle p;
        for (int i=0; i<this.particles.size(); i++) {
            p = this.particles.get(i);
            for (int j=i+1; j<this.particles.size(); j++) {
                p.isNeighbor(this.particles.get(j), rc, 0, 0);
            }
        }
    }

    public List<Particle> getParticles () {
        return this.particles;
    }

    public void findNeighbors (float rc, Cell adjacent, float increaseX, float increaseY) {
        this.particles.forEach(p -> adjacent.findNeighbors(rc, p, increaseX, increaseY));
    }

    public void findNeighbors (float rc, Particle p, float increaseX, float increaseY) {
        this.particles.forEach(e -> e.isNeighbor(p, rc, increaseX, increaseY));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        particles.forEach(p -> builder.append(p.toString()));
        return builder.toString();
    }
}
