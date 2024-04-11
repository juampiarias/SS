package tp3;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Generator {

    public static void main(String[] args) {

        String configFile = "tp3/configs/app.config";
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream(configFile)) {
            prop.load(fis);
        } catch (IOException e) {
            System.err.println("Error reading app config file: " + e.getMessage());
            return;
        }

        try (FileInputStream fis = new FileInputStream(prop.getProperty("java") + prop.getProperty("file"))) {
            prop.load(fis);
        } catch (IOException ex) {
            System.err.println("Error reading config file: " + ex.getMessage());
            return;
        }

        int N = Integer.parseInt(prop.getProperty("n"));
        double L = Double.parseDouble(prop.getProperty("l"));
        double v = Double.parseDouble(prop.getProperty("v"));
        double centralR = Double.parseDouble(prop.getProperty("centralR"));
        double r = Double.parseDouble(prop.getProperty("r"));
        double mass = Double.parseDouble(prop.getProperty("mass"));
        double iterations = Double.parseDouble(prop.getProperty("iter"));
        String csvFile = prop.getProperty("java") + prop.getProperty("input");

        List<GenericParticle> particles = generate(L, centralR, N, v, mass, r);

        try (FileWriter writer = new FileWriter(csvFile)){
            writer.write("id;rx;ry;vx;vy;mass;radius\n");
            int i = 0;
            for (GenericParticle p : particles) {
                writer.write(p.toString());
            }
            writer.flush();
        } catch (IOException ex) {
            System.err.println("Error writing input file: " + ex.getMessage());
        }
    }

    public static List<GenericParticle> generate (double L, double centralR, int N,
                                                 double v, double mass, double radius) {
        List<GenericParticle> particles = new ArrayList<>();
        GenericParticle central = new GenericParticle(N+1,L/2, L/2, 0, 0, mass, centralR);
        particles.add(central);
        Random rand = new Random();
        double minLim = 0+radius;
        double maxLim = L-radius;
        int i = 0;

        while (particles.size() < N+1) {
            double angle = rand.nextDouble(0, 2*Math.PI);
            double vx = Math.cos(angle)*v;
            double vy = Math.sin(angle)*v;
            double rx = rand.nextDouble(minLim, maxLim);
            double ry = rand.nextDouble(minLim, maxLim);
            GenericParticle p = new GenericParticle(i, rx, ry, vx, vy, mass, radius);
            if (!particles.contains(p)) {
                particles.add(p);
                i++;
            }
        }

        particles.remove(central);

        return particles;
    }


    public static class GenericParticle {
        int id;
        double rx;
        double ry;
        double vx;
        double vy;
        double mass;
        double radius;

        GenericParticle(int id, double rx, double ry, double vx, double vy, double mass, double radius) {
            this.id = id;
            this.rx = rx;
            this.ry = ry;
            this.vx = vx;
            this.vy = vy;
            this.mass = mass;
            this.radius = radius;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GenericParticle that = (GenericParticle) o;
            double d = Math.sqrt(Math.pow(that.rx - this.rx, 2) + Math.pow(that.ry - this.ry, 2));
            return d < this.radius + that.radius;
        }

        @Override
        public String toString() {
            return id + ";" + rx + ";" + ry + ";" + vx + ";" + vy + ";" + mass + ";" + radius + "\n";
        }
    }
}
