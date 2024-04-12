package tp3;

import java.util.List;

public class Simulation {

    private final Double[][] collisionTimes;
    private final List<Particle> particles;
    private final int N;
    private double currentTime;

    Simulation(int N, List<Particle> particles) {
        // N+2, N particles, vertical wall, horizontal wall
        this.collisionTimes = new Double[N][N+2];
        this.particles = particles;
        this.currentTime = 0.0;
        this.N = N;

        fillCollisionTimes();
    }

    public Collision iterate() {
        Collision collision = findCollision();
        Particle p1 = collision.getA();

        advance(collision.getT()-currentTime);
        currentTime = collision.getT();

        switch (collision.getType()) {
            case Particle -> {
                Particle p2 = collision.getB();
                p1.bounce(p2);
                updateCollisions(p2);
            }
            case VerticalWall -> p1.bounceX();
            case HorizontalWall -> p1.bounceY();
        }

        updateCollisions(p1);
        return collision;
    }

    void fillCollisionTimes () {
        int size = particles.size();
        for (int i= 0; i < size; i++) {
            collisionTimes[i][i] = Double.POSITIVE_INFINITY;
            Particle p1 = particles.get(i);
            for (int j = i + 1; j < size; j++) {
                Particle p2 = particles.get(j);
                double aux = p1.collides(p2);
                collisionTimes[i][j] = aux;
                collisionTimes[j][i] = aux;
            }
            collisionTimes[i][size] = p1.collidesX();
            collisionTimes[i][size+1] = p1.collidesY();
        }

    }

    Collision findCollision () {
        int size = particles.size();
        double time = Double.POSITIVE_INFINITY;
        Collision collision = new Collision(time, null, null, null);

        for (int i= 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if(collisionTimes[i][j] < time) {
                    time = collisionTimes[i][j];
                    collision.setT(time);
                    collision.setA(particles.get(i));
                    collision.setB(particles.get(j));
                    collision.setType(CollisionType.Particle);
                }
            }
            if (collisionTimes[i][size] < time) {
                time = collisionTimes[i][size];
                collision.setT(time);
                collision.setA(particles.get(i));
                collision.setB(null);
                collision.setType(CollisionType.VerticalWall);
            }
            if (collisionTimes[i][size+1] < time) {
                time = collisionTimes[i][size+1];
                collision.setT(time);
                collision.setA(particles.get(i));
                collision.setB(null);
                collision.setType(CollisionType.HorizontalWall);
            }
        }

        return collision;
    }

    void updateCollisions (Particle p) {
        int size = particles.size();
        int aux = p.getId();
        double time;
        for (int i= 0; i < aux; i++) {
            time = p.collides(particles.get(i)) + currentTime;
            collisionTimes[i][aux] = time;
            collisionTimes[aux][i] = time;
        }
        for (int i= aux+1; i < size; i++) {
            time = p.collides(particles.get(i)) + currentTime;
            collisionTimes[i][aux] = time;
            collisionTimes[aux][i] = time;
        }
        collisionTimes[aux][size] = p.collidesX() + currentTime;
        collisionTimes[aux][size+1] = p.collidesY() + currentTime;
    }

    void advance (double dt) {
        for (Particle p: this.particles) {
            p.advance(dt);
        }
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public void printTimes() {
        for (int i=0; i<N; i++) {
            StringBuilder builder = new StringBuilder().append(i).append(": ");
            for (int j=0; j<N+3; j++) {
                if (collisionTimes[i][j] != Double.POSITIVE_INFINITY) {
                    builder.append(j).append(": ").append(collisionTimes[i][j]).append(", ");
                }
            }
            System.out.println(builder.toString());
        }
    }

}
