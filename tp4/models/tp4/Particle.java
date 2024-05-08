package tp4;

public class Particle {

    final double g;
    final double m;
    final double [] alphas;

    double [] rx;
    double [] ry;

    double fx;
    double fy;


    Particle(double g, double m, double rx, double ry, double vx, double vy) {
        this.g = g;
        this.m = m;
        this.alphas = new double[]{3.0/16, 251.0/360, 1, 11.0/18, 1.0/6, 1.0/60};

        this.rx = new double[]{rx, vx, 0.0, 0.0, 0.0, 0.0};
        this.ry = new double[]{ry, vy, 0.0, 0.0, 0.0, 0.0};

        this.fx = 0.0;
        this.fy = 0.0;
    }

    void setA() {
        rx[2] = fx/m;
        ry[2] = fy/m;
    }

    void add_Fx(double fx) {
        this.fx += fx;
    }
    void add_Fy(double fy) {
        this.fy += fy;
    }

     public void interact(Particle p) {

        double dd = Math.pow(p.rx[0] - rx[0], 2) + Math.pow(p.ry[0] - ry[0], 2);
        double d = Math.sqrt(dd);

        double Fn = g*p.m*m/dd;
        double Fx = Fn * (p.rx[0]-rx[0])/d;
        double Fy = Fn * (p.ry[0]-ry[0])/d;

        add_Fx(Fx);
        add_Fy(Fy);

        p.add_Fx(-Fx);
        p.add_Fy(-Fy);
     }

     public void gear_predict(double dt) {
         double rx0, rx1, rx2, rx3, rx4, rx5;

         rx0 = predict(rx[0], rx[1], rx[2], rx[3], rx[4], rx[5], dt);
         rx1 = predict(rx[1], rx[2], rx[3], rx[4], rx[5], 0.0, dt);
         rx2 = predict(rx[2], rx[3], rx[4], rx[5], 0.0, 0.0, dt);
         rx3 = predict(rx[3], rx[4], rx[5], 0.0, 0.0, 0.0, dt);
         rx4 = predict(rx[4], rx[5], 0.0, 0.0, 0.0, 0.0, dt);
         rx5 = predict(rx[5], 0.0, 0.0, 0.0, 0.0, 0.0, dt);

         double ry0, ry1, ry2, ry3, ry4, ry5;

         ry0 = predict(ry[0], ry[1], ry[2], ry[3], ry[4], ry[5], dt);
         ry1 = predict(ry[1], ry[2], ry[3], ry[4], ry[5], 0.0, dt);
         ry2 = predict(ry[2], ry[3], ry[4], ry[5], 0.0, 0.0, dt);
         ry3 = predict(ry[3], ry[4], ry[5], 0.0, 0.0, 0.0, dt);
         ry4 = predict(ry[4], ry[5], 0.0, 0.0, 0.0, 0.0, dt);
         ry5 = predict(ry[5], 0.0, 0.0, 0.0, 0.0, 0.0, dt);

         rx = new double[]{rx0, rx1, rx2, rx3, rx4, rx5};
         ry = new double[]{ry0, ry1, ry2, ry3, ry4, ry5};
    }

    public void gear_correct(double dt) {
         double ax = fx/m;
         double ay = fy/m;

         double R2x = (ax - rx[2])*dt*dt/2;
         double R2y = (ay - ry[2])*dt*dt/2;

         rx = correct(rx[0], rx[1], rx[2], rx[3], rx[4], rx[5], dt, R2x);
         ry = correct(ry[0], ry[1], ry[2], ry[3], ry[4], ry[5], dt, R2y);

        fx = 0.0;
        fy = 0.0;

     }

    double predict(double t0, double t1, double t2, double t3, double t4, double t5, double dt) {
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

    double[] correct(double p0, double p1, double p2, double p3, double p4, double p5, double dt, double R2) {
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

    @Override
    public String toString() {
        return rx[0] +
                ", " + ry[0] +
                ", " + rx[1] +
                ", " + ry[1];
    }
}
