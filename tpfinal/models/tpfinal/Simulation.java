package tpfinal;

import java.util.List;

public class Simulation {

    private final List<Person> guards;
    private final List<Person> fans;

    Simulation(List<Person> guards, List<Person> fans) {
        this.guards = guards;
        this.fans = fans;
    }

    public void initialize(double dt) {
        for (Person guard : guards) {
            for (Person fan : fans) {
                guard.calculateDrivingForce(fan);
                fan.calculateDrivingForce(guard);
            }
        }
        for (Person guard : guards) {
            guard.setA();
        }
        for (Person fan : fans) {
            fan.setA();
        }
    }

    public boolean iterate(double dt) {
        boolean finished = true;
        for (Person guard : guards) {
            if (!guard.dead) {
                for (Person fan : fans) {
                    if (!fan.dead) {
                        guard.calculateDrivingForce(fan);
                        fan.calculateDrivingForce(guard);
                    }
                }
            }
        }
        for (Person guard : guards) {
            if (!guard.dead) {
                guard.gear_predict(dt);
                guard.gear_correct(dt);
            }
        }
        for (Person fan : fans) {
            if (!fan.dead) {
                fan.gear_predict(dt);
                fan.calculateDrivingForce();
                fan.gear_correct(dt);
            }
            finished = finished && fan.dead;
        }

        return finished;
    }

    public List<Person> getFans() {
        return fans;
    }

    public List<Person> getGuards() {
        return guards;
    }
}
