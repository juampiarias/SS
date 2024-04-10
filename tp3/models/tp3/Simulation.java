package tp3;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    public static void main(String[] args) {

        int N = 200;
        double L = 0.1;
        int iterations = 10;

        // N+3, N particles, vertical wall, horizontal wall, central sphere
        Double[][] collisionTimes = new Double[N][N+3];

        //init particles
        List<Particle> particles = new ArrayList<>();

        fillCollisionTimes(particles, collisionTimes);

        for (int i = 0; i < iterations; i++) {
            Collision collision = findCollision(particles, collisionTimes);
            Particle p1 = collision.getA();
            double time = collision.getT();

            advance(particles, collision);

            switch (collision.getType()) {
                case Particle -> {
                    Particle p2 = collision.getB();
                    p2.bounce(p1, time);
                    updateCollisions(particles, collisionTimes, p2);
                }
                case VerticalWall -> p1.bounceX(time);
                case HorizontalWall -> p1.bounceY(time);
                case CentralSphere -> p1.bounceSphere(time);
            }

            updateCollisions(particles, collisionTimes, p1);


        }


    }

    static void fillCollisionTimes (List<Particle> particles, Double[][] collisionTimes) {
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
            collisionTimes[i][size+2] = p1.collidesSphere();
        }

    }

    static Collision findCollision (List<Particle> particles, Double[][] collisionTimes) {
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
            if (time < collisionTimes[i][size]) {
                time = collisionTimes[i][size];
                collision.setT(time);
                collision.setA(particles.get(i));
                collision.setB(null);
                collision.setType(CollisionType.VerticalWall);
            }
            if (time < collisionTimes[i][size+1]) {
                time = collisionTimes[i][size];
                collision.setT(time);
                collision.setA(particles.get(i));
                collision.setB(null);
                collision.setType(CollisionType.HorizontalWall);
            }
            if (time < collisionTimes[i][size+2]) {
                time = collisionTimes[i][size];
                collision.setT(time);
                collision.setA(particles.get(i));
                collision.setB(null);
                collision.setType(CollisionType.CentralSphere);
            }


        }

        return collision;
    }

    static void updateCollisions (List<Particle> particles, Double[][] collisionTimes, Particle p) {
        int size = particles.size();
        int aux = p.getId();
        double time;
        for (int i= 0; i < aux; i++) {
            time = p.collides(particles.get(i));
            collisionTimes[i][aux] = time;
            collisionTimes[aux][i] = time;
        }
        for (int i= aux+1; i < size; i++) {
            time = p.collides(particles.get(i));
            collisionTimes[i][aux] = time;
            collisionTimes[aux][i] = time;
        }
        collisionTimes[aux][size] = p.collidesX();
        collisionTimes[aux][size+1] = p.collidesY();
        collisionTimes[aux][size+2] = p.collidesSphere();
    }

    static void advance (List<Particle> particles, Collision collision) {
        double time = collision.getT();
        Particle p1 = collision.getA();
        if (collision.getType() == CollisionType.Particle) {
            Particle p2 = collision.getB();
            for (int i=0; i<particles.size(); i++) {
                if (i!=p1.getId() && i!= p2.getId()) {
                    particles.get(i).advance(time);
                }
            }

        } else {
            int aux = p1.getId();
            for (int i= 0; i < aux; i++) {
                particles.get(i).advance(time);
            }
            for (int i= aux+1; i < particles.size(); i++) {
                particles.get(i).advance(time);
            }
        }
    }


}
