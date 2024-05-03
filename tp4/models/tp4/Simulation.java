package tp4;

import java.util.List;

public class Simulation {

    Particle earth;
    Particle mars;
    Particle ship;
    Particle sun;
    double g;

    Simulation(double mrx, double mry, double mvx, double mvy, double erx, double ery, double evx, double evy) {
        g = 6.693*ten(-20);

        sun = new Sun(g, 1988500*ten(24), 0, 0, 0, 0);
        mars = new Particle(g, 6.4171*ten(23), mrx, mry, mvx, mvy);
        earth = new Particle(g, 5.97219*ten(24), erx, ery, evx, evy);
        ship = initializeShip();

    }

    void iterate(double dt) {
        sun.interact(earth);
        sun.interact(mars);
        sun.interact(ship);
        earth.interact(mars);
        earth.interact(ship);
        mars.interact(ship);

        sun.actualize(dt);
        earth.actualize(dt);
        mars.actualize(dt);
        ship.actualize(dt);
    }

    double ten(double x) {
        return Math.pow(10,x);
    }

    Particle initializeShip () {
        double distanceEarthShip = 1500;
        double vOrbit = 7.12;
        double vShip = 8;


        double d = Math.sqrt(Math.pow(earth.rx, 2) + Math.pow(earth.ry, 2));

        //TODO: aca es rx o -rx??
        double etx = -(earth.ry/d);
        double ety = earth.rx/d;

        double x = earth.rx*distanceEarthShip/d;
        double y = earth.ry*distanceEarthShip/d;

        double vt = earth.vx*etx + earth.vy*ety;
        vt += vOrbit + vShip;

        double vx = vt*etx;
        double vy = vt*ety;

        return new Particle(g, 2*ten(5), earth.rx+x, earth.ry+y, vx, vy);

    }

    @Override
    public String toString() {
        return "sun, " + sun +
                "\nearth," + earth +
                "\nmars," + mars +
                "\nship," + ship + '\n';
    }
}
