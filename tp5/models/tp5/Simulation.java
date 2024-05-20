package tp5;

public class Simulation {

    Loco loco;

    double dt = 1.0/240;

    Simulation(Loco loco) {
        this.loco = loco;
    }

    public void initializeLoco(Team home, Team away, Particle ball) {
//        for (Particle p : home.getPlayers()) {
//            loco.calculateSocialForce(p);
//        }
//        for (Particle p : away.getPlayers()) {
//            loco.calculateSocialForce(p);
//        }
        loco.calculateDrivingForce(ball);
        loco.setA();
    }

    public void iterate(Team home, Team away, Particle ball) {
        for (int i=0; i<10; i++) {
            loco.gear_predict(dt);
//            for (Particle p : home.getPlayers()) {
//                loco.calculateSocialForce(p);
//            }
//            for (Particle p : away.getPlayers()) {
//                loco.calculateSocialForce(p);
//            }
            loco.calculateDrivingForce(ball);
            loco.gear_correct(dt);
        }
    }

    @Override
    public String toString() {
        return loco.toString();
    }

}
