package tp4.oscilator;

public class Gear implements Algorithm{

    final double m;
    final double k;
    final double gamma;
    final double dt;
    final double [] alphas;

    double [] rs;

    Gear(double r, double v, double m, double k, double gamma, double dt) {
        this.m = m;
        this.k = k;
        this.gamma = gamma;
        this.dt = dt;

        this.alphas = new double[]{3.0/16, 251.0/360, 1, 11.0/18, 1.0/6, 1.0/60};
//        this.rs = new double[]{r, v, 0.0, 0.0, 0.0, 0.0};

        double r2, r3, r4, r5;
        r2 = OscillatorForce(r, v, m, k, gamma);
        r3 = OscillatorForce(v, r2, m, k, gamma);
        r4 = OscillatorForce(r2, r3, m, k, gamma);
        r5 = OscillatorForce(r3, r4, m, k, gamma);

        this.rs = new double[]{r, v, r2, r3, r4, r5};
    }


    @Override
    public void iterate() {
        double r0, r1, r2, r3, r4, r5;

        r0 = predict(rs[0], rs[1], rs[2], rs[3], rs[4], rs[5]);
        r1 = predict(rs[1], rs[2], rs[3], rs[4], rs[5], 0.0);
        r2 = predict(rs[2], rs[3], rs[4], rs[5], 0.0, 0.0);
        r3 = predict(rs[3], rs[4], rs[5], 0.0, 0.0, 0.0);
        r4 = predict(rs[4], rs[5], 0.0, 0.0, 0.0, 0.0);
        r5 = predict(rs[5], 0.0, 0.0, 0.0, 0.0, 0.0);

//        System.out.println(r0);

        double a = OscillatorForce(r0, r1, m, k, gamma);
        double R2 = (a - r2)*dt*dt/2;

        rs = correct(r0, r1, r2, r3, r4, r5, R2);
    }

    @Override
    public double getR() {
//        System.out.println(rs[0]);
        return rs[0];
    }

    double predict(double t0, double t1, double t2, double t3, double t4, double t5) {
        double tt1, tt2, tt3, tt4, tt5;
        double fact = 1;

        tt1 = t1*dt;
        fact *= 2;
        tt2 = t2*Math.pow(dt, 2)/fact;
        fact *= 3;
        tt3 = t3*Math.pow(dt, 3)/fact;
        fact *= 4;
        tt4 = t4*Math.pow(dt, 4)/fact;
        fact *= 5;
        tt5 = t5*Math.pow(dt, 5)/fact;

        return t0 + tt1 + tt2 + tt3 + tt4 + tt5;
    }

    double[] correct(double p0, double p1, double p2, double p3, double p4, double p5, double R2) {
        double r0, r1, r2, r3, r4, r5;
        double fact = 1;

        r0 = p0 + alphas[0]*R2;
        r1 = p1 + alphas[1]*R2/dt;
        fact *= 2;
        r2 = p2 + alphas[2]*R2*fact/Math.pow(dt, 2);
        fact *= 3;
        r3 = p3 + alphas[3]*R2*fact/Math.pow(dt, 3);
        fact *= 4;
        r4 = p4 + alphas[4]*R2*fact/Math.pow(dt, 4);
        fact *= 5;
        r5 = p5 + alphas[5]*R2*fact/Math.pow(dt, 5);

        return new double[]{r0, r1, r2, r3, r4, r5};
    }
}
