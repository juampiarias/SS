package tp4;

import java.util.List;

public class Simulation {

    Particle earth;
    Particle mars;
    Particle ship;
    Particle sun;
    double g;

    Simulation(double mrx, double mry, double mvx, double mvy, double erx, double ery, double evx, double evy,
               double distanceEarthShip, double vOrbit, double vShip) {
        g = 6.693*ten(-20);

        sun = new Sun(g, 1988500*ten(24), 0, 0, 0, 0);
        mars = new Particle(g, 6.4171*ten(23), mrx, mry, mvx, mvy);
        earth = new Particle(g, 5.97219*ten(24), erx, ery, evx, evy);
        ship = initializeShip(distanceEarthShip, vOrbit, vShip);

        sun.interact(earth);
        sun.interact(mars);
        sun.interact(ship);
        earth.interact(mars);
        earth.interact(ship);
        mars.interact(ship);

        sun.setA();
        earth.setA();
        mars.setA();
        ship.setA();

    }

    void iterate(double dt) {
        sun.gear_predict(dt);
        earth.gear_predict(dt);
        mars.gear_predict(dt);
        ship.gear_predict(dt);

        sun.interact(earth);
        sun.interact(mars);
        sun.interact(ship);
        earth.interact(mars);
        earth.interact(ship);
        mars.interact(ship);

        sun.gear_correct(dt);
        earth.gear_correct(dt);
        mars.gear_correct(dt);
        ship.gear_correct(dt);
    }

    double ten(double x) {
        return Math.pow(10,x);
    }

    Particle initializeShip (double distanceEarthShip, double vOrbit, double vShip) {

        double d = Math.sqrt(Math.pow(earth.rx[0], 2) + Math.pow(earth.ry[0], 2));

        double cos = (earth.rx[0]/d);
        double sen = earth.ry[0]/d;

        double x = earth.rx[0]*distanceEarthShip/d;
        double y = earth.ry[0]*distanceEarthShip/d;

        double vt = earth.rx[1]*(-sen) + earth.ry[1]*cos;
        vt += vOrbit + vShip;

        double vx = vt+(-sen);
        double vy = vt+(cos);

        return new Particle(g, 2*ten(5), earth.rx[0]+x, earth.ry[0]+y, vx, vy);

    }

    @Override
    public String toString() {
        return "sun, " + sun +
                "\nearth," + earth +
                "\nmars," + mars +
                "\nship," + ship + '\n';
    }
}
