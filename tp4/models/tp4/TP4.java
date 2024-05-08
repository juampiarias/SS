package tp4;

import java.io.*;
import java.util.Properties;

public class TP4 {

    public static void main(String[] args) {
        String configFile = "tp4/configs/app.config";
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

//        int N = Integer.parseInt(prop.getProperty("n"));
//        double L = Double.parseDouble(prop.getProperty("l"));
//        double v = Double.parseDouble(prop.getProperty("v"));
//        double centralR = Double.parseDouble(prop.getProperty("centralR"));
//        double centralMass = Double.parseDouble(prop.getProperty("centralMass"));
//        double r = Double.parseDouble(prop.getProperty("r"));
//        double mass = Double.parseDouble(prop.getProperty("mass"));
//        double iterations = Double.parseDouble(prop.getProperty("iter"));

        double mrx=0, mry=0, mvx=0, mvy=0, erx=0, ery=0, evx=0, evy=0;
        String line;
        String splitter = ",";
        int day = 50;

        String csvFile = prop.getProperty("java") + "data/mars.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            for(int i=0; i<day; i++) {
                br.readLine();
            }
            if ((line = br.readLine()) != null) {
                String[] data = line.split(splitter);
                mrx = Double.parseDouble(data[2]);
                mry = Double.parseDouble(data[3]);
                mvx = Double.parseDouble(data[5]);
                mvy = Double.parseDouble(data[6]);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        csvFile = prop.getProperty("java") + "data/earth.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            for(int i=0; i<day; i++) {
                br.readLine();
            }
            if ((line = br.readLine()) != null) {
                String[] data = line.split(splitter);
                erx = Double.parseDouble(data[2]);
                ery = Double.parseDouble(data[3]);
                evx = Double.parseDouble(data[5]);
                evy = Double.parseDouble(data[6]);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        double distanceEarthShip = 1500;
        double vOrbit = 7.12;
        double vShip = 8;

        Simulation simulation = new Simulation(mrx, mry, mvx, mvy, erx, ery, evx, evy,
                                                distanceEarthShip, vOrbit, vShip);

        double dt = 60;
        int j=0;

        try {
            FileWriter writer = new FileWriter(prop.getProperty("java") + prop.getProperty("output"));

            for (int i = 0; i < 4*365*24*60; i++) {
                if(i%(60*24) == 0) {
                    writer.write("Day ");
                    writer.write(String.valueOf(j++));
                    writer.write('\n');
                    writer.write(simulation.toString());
                    System.out.println(j);
                }
                simulation.iterate(dt);
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
