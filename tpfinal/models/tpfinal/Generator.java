package tpfinal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {

    int guardAmount;

    double desired;
    double tau;

    double circleRadius;
    double circleExtension;

    Generator(int guardAmount, double desired, double tau, double circleRadius, double circleExtension) {
        this.guardAmount = guardAmount;
        this.desired = desired;
        this.tau = tau;
        this.circleRadius = circleRadius;
        this.circleExtension = circleExtension;
    }

    public List<Person> generateGuards() {
        List<Person> guards = new ArrayList<Person>();
        double angle = 360.0/guardAmount;
        double aux = 0.0;

        for (int i = 0; i < guardAmount; i++) {
            double x = Math.cos(Math.toRadians(aux))*circleRadius;
            double y = Math.sin(Math.toRadians(aux))*circleRadius;
            Vector velocity = new Vector(0.0-x, 0.0-y);
            velocity = velocity.normalize();

            Person guard = new Guard(i, x, y,
                    velocity.x*desired, velocity.y*desired,
                    0.25, 80, desired, tau);
            guards.add(guard);
            aux+=angle;
        }
        return guards;
    }

    public Person generateFan() {
        Random rand = new Random();

        double x = rand.nextDouble(-circleRadius-circleExtension, circleRadius+circleExtension);
        int sign = rand.nextBoolean() ? -1 : 1;
        double y = Math.sqrt(Math.pow(circleRadius+circleExtension, 2) - Math.pow(x, 2)) * sign;
        Vector velocity = new Vector(0.0-x, 0.0-y);
        velocity = velocity.normalize();
        return new Fan(0, x, y,
                velocity.x*desired, velocity.y*desired,
                0.25, 80, desired, tau);
    }
}
