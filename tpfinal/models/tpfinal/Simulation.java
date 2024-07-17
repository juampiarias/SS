package tpfinal;

import java.util.List;

public class Simulation {

    private final List<Person> guards;
    private final Person fan;

    Simulation(List<Person> guards, Person fan) {
        this.guards = guards;
        this.fan = fan;
    }

    public void initialize(double dt) {
        for (Person guard : guards) {
            guard.calculateDrivingForce(fan);
            fan.calculateDrivingForce(guard);
        }
        for (Person guard : guards) {
            guard.setA();
        }
        fan.setA();
    }

    public boolean iterate(double dt) {
        for (Person guard : guards) {
            if (!guard.dead) {
                if (!fan.dead) {
                    guard.calculateDrivingForce(fan);
                    fan.calculateDrivingForce(guard);
                }
            }
        }
        for (Person guard : guards) {
            if (!guard.dead) {
                guard.gear_predict(dt);
                guard.gear_correct(dt);
            }
        }
        if (!fan.dead && !fan.success()) {
            fan.gear_predict(dt);
            fan.calculateDrivingForce();
            fan.gear_correct(dt);
        }

        return fan.dead;
    }
}
