package tp3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TP3 {

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
        String line;
        String splitter = ";";
        List<Particle> particles = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitter);
                Particle particle =
                        new Particle(Integer.parseInt(data[0]),
                                Double.parseDouble(data[1]),
                                Double.parseDouble(data[2]),
                                Double.parseDouble(data[3]),
                                Double.parseDouble(data[4]),
                                Double.parseDouble(data[5]),
                                Double.parseDouble(data[6]),
                                L,
                                centralR);
                particles.add(particle);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        Simulation simulation = new Simulation(N, particles);

        try {
            FileWriter writer = new FileWriter(prop.getProperty("java") + prop.getProperty("output"));
            writer.write("t" + simulation.getCurrentTime() + '\n');
            for (Particle particle : particles) {
                writer.write(particle.toString());
            }

            for (int i = 0; i < iterations; i++) {
                Collision collision = simulation.iterate();
                writer.write("t" + simulation.getCurrentTime() + ' ');
                Particle p1 = collision.getA();
                switch (collision.getType()) {
                    case Particle -> {
                        Particle p2 = collision.getB();
                        writer.write(p1.getId() + " " + p2.getId() + '\n');
                    }
                    case VerticalWall -> {
                        writer.write(p1.getId() + " " + (N) + '\n');
                    }
                    case HorizontalWall -> {
                        writer.write(p1.getId() + " " + (N+1) + '\n');
                    }
                    case CentralSphere -> {
                        writer.write(p1.getId() + " " + (N+2) + '\n');
                    }
                }
//                System.out.println(simulation.getCurrentTime());
                for (Particle particle : particles) {
                    writer.write(particle.toString());
                }
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
