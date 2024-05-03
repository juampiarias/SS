package tp4.oscilator;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Oscillator {

    public static void main(String[] args) {
        String configFile = "tp4/configs/oscillator.config";
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream(configFile)) {
            prop.load(fis);
        } catch (IOException e) {
            System.err.println("Error reading app config file: " + e.getMessage());
            return;
        }

        double m = Double.parseDouble(prop.getProperty("m"));
        double k = Double.parseDouble(prop.getProperty("k"));
        double gamma = Double.parseDouble(prop.getProperty("gamma"));
        double tf = Double.parseDouble(prop.getProperty("tf"));
        double r0 = Double.parseDouble(prop.getProperty("r0"));
        double v0 = -gamma*0.5/m;

        double dt = 0.01;

        for (int i=2; i<7; i++) {
            List<Algorithm> algs = new ArrayList<Algorithm>();
            algs.add(new Gear(r0, v0, m, k, gamma, dt));
            algs.add(new Beeman(r0, v0, m, k, gamma, dt));
            algs.add(new Verlet(r0, v0, m, k, gamma, dt));
            try {
                FileWriter writer = new FileWriter(prop.getProperty("java") + "oscillation_" + i + ".csv");
                writer.write("time;analytic;gear;beeman;verlet\n");

                for (double time = 0.0; time < tf; time += dt) {
                    for (Algorithm alg : algs) {
                        alg.iterate();
                    }
                    writer.write(String.valueOf(time));
                    writer.write(";");
                    writer.write(String.valueOf(AnalyticOscillator(m, k, gamma, time)));

                    for (Algorithm alg : algs) {
                        writer.write(";");
                        writer.write(String.valueOf(alg.getR()));
                    }
                    writer.write("\n");
                }
                writer.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

            dt/=10;

        }
    }


    public static double AnalyticOscillator(double m, double k, double gamma, double t) {
        return Math.exp(-t*gamma/(2*m)) * Math.cos(Math.pow(k/m - (Math.pow(gamma,2)/(4*Math.pow(m,2))), 0.5) * t);
    }
}
