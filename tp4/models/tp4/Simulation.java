package tp4;

import java.util.List;

public class Simulation {

    Particle earth;
    Particle mars;
    Particle ship;
    Particle sun;
    double g;

    Simulation(double mrx, double mry, double mvx, double mvy, double erx, double ery, double evx, double evy,
               double distancePlanetShip, double vOrbit, double vShip, boolean backToEarth) {
        g = 6.693*ten(-20);

        sun = new Sun(g, 1988500*ten(24), 0, 0, 0, 0);
        mars = new Particle(g, 6.4171*ten(23), mrx, mry, mvx, mvy);
        earth = new Particle(g, 5.97219*ten(24), erx, ery, evx, evy);
        if (backToEarth) {
            ship = initializeShipBackToEarth(distancePlanetShip, vOrbit, vShip);
        }
        else {
            ship = initializeShip(distancePlanetShip, vOrbit, vShip);
        }
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

        double earthRadius = 6371.01;

        double x = earth.rx[0]*(distanceEarthShip+earthRadius)/d;
        double y = earth.ry[0]*(distanceEarthShip+earthRadius)/d;

        double v = Math.sqrt(Math.pow(earth.rx[1], 2) + Math.pow(earth.ry[1], 2));

        double vtx = v*(-sen);
        double vty = v*(cos);

        double v0x = (vShip+vOrbit)*(-sen);
        double v0y = (vShip+vOrbit)*(cos);

        double vx = vtx+v0x;
        double vy = vty+v0y;

        return new Particle(g, 2*ten(5), earth.rx[0]+x, earth.ry[0]+y, vx, vy);

    }

    Particle initializeShipBackToEarth (double distanceMarsShip, double vOrbit, double vShip) {

        double d = Math.sqrt(Math.pow(mars.rx[0], 2) + Math.pow(mars.ry[0], 2));

        double cos = (mars.rx[0]/d);
        double sen = mars.ry[0]/d;

        double marsRadius = 3389.92;

        double x = mars.rx[0]*(distanceMarsShip+marsRadius)/d;
        double y = mars.ry[0]*(distanceMarsShip+marsRadius)/d;

        double v = Math.sqrt(Math.pow(mars.rx[1], 2) + Math.pow(mars.ry[1], 2));

        double vtx = v*(-sen);
        double vty = v*(cos);

        double v0x = (vShip+vOrbit)*(-sen);
        double v0y = (vShip+vOrbit)*(cos);

        double vx = vtx+v0x;
        double vy = vty+v0y;

        return new Particle(g, 2*ten(5), mars.rx[0]-x, mars.ry[0]-y, vx, vy);

    }

    @Override
    public String toString() {
        return "sun, " + sun +
                "\nearth," + earth +
                "\nmars," + mars +
                "\nship," + ship + '\n';
    }
}
