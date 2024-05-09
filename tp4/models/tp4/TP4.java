package tp4;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        double mrx=0, mry=0, mvx=0, mvy=0, erx=0, ery=0, evx=0, evy=0;
        String marsLine;
        String earthLine;
        String splitter = ",";

        String marsCsv = prop.getProperty("marsData");
        String earthCsv = prop.getProperty("earthData");
        String marsFile = prop.getProperty("java") + marsCsv;
        String earthFile = prop.getProperty("java") + earthCsv;
        BufferedReader marsBr, earthBr;
        boolean backToEarth = Boolean.parseBoolean(prop.getProperty("backToEarth"));
        int start = Integer.parseInt(prop.getProperty("startDate"));
        int end = Integer.parseInt(prop.getProperty("endDate"));
        int dt = Integer.parseInt(prop.getProperty("dt"));
        int simYears = Integer.parseInt(prop.getProperty("simYears"));
        int sim = simYears*365*24*60*60/dt;
        int print = 24*60*60/dt;



        try {
            marsBr = new BufferedReader(new FileReader(marsFile));
            earthBr = new BufferedReader(new FileReader(earthFile));
            marsBr.readLine();
            earthBr.readLine();
            for (int i=0; i<start; i++) {
                marsBr.readLine();
                earthBr.readLine();
            }

            ExecutorService executor = Executors.newFixedThreadPool(20);

            for (int i=start; i<end; i++) {
                if ((marsLine = marsBr.readLine()) != null &&
                    (earthLine = earthBr.readLine()) != null) {
                    String[] marsData = marsLine.split(splitter);
                    String[] earthData = earthLine.split(splitter);
                    mrx = Double.parseDouble(marsData[2]);
                    mry = Double.parseDouble(marsData[3]);
                    mvx = Double.parseDouble(marsData[5]);
                    mvy = Double.parseDouble(marsData[6]);
                    erx = Double.parseDouble(earthData[2]);
                    ery = Double.parseDouble(earthData[3]);
                    evx = Double.parseDouble(earthData[5]);
                    evy = Double.parseDouble(earthData[6]);

                    double distanceEarthShip = 1500;
                    double vOrbit = 7.12;
                    double vShip = Double.parseDouble(prop.getProperty("vShip"));

                    Task task = new Task(i, dt, sim, print,(prop.getProperty("java") + prop.getProperty("output") + i + ".csv"),
                                        mrx, mry, mvx, mvy, erx, ery, evx, evy,
                                        distanceEarthShip, vOrbit, vShip, backToEarth);

                    executor.submit(task);

//                    Simulation simulation = new Simulation(mrx, mry, mvx, mvy, erx, ery, evx, evy,
//                            distanceEarthShip, vOrbit, vShip);
//
//                    FileWriter writer = new FileWriter(prop.getProperty("java") + prop.getProperty("output") + i + ".csv");
//                    System.out.println("Simulation" + i);
//                    for (int j = 0, k = 0; j < sim; j++) {
//                        if(j%(print) == 0) {
//                            writer.write("Day ");
//                            writer.write(String.valueOf(k++));
//                            writer.write('\n');
//                            writer.write(simulation.toString());
//                        }
//                        simulation.iterate(dt);
//                    }
//                    writer.flush();

                }
            }

            executor.shutdown();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}

class Task implements Runnable {

    int id;
    double dt;
    double sim;
    double print;
    String outputFile;
    Simulation simulation;

    Task(int id, double dt, double sim, double print, String outputfile,
         double mrx, double mry, double mvx, double mvy,
         double erx, double ery, double evx, double evy,
         double distancePlanetShip, double vOrbit, double vShip, boolean backToEarth) {
        this.id = id;
        this.dt = dt;
        this.sim = sim;
        this.print = print;
        this.outputFile = outputfile;
        this.simulation = new Simulation(mrx, mry, mvx, mvy,
                erx, ery, evx, evy,
                distancePlanetShip, vOrbit, vShip, backToEarth);
    }

    @Override
    public void run() {
        try {
            FileWriter writer = new FileWriter(outputFile);
            System.out.println("Start Simulation" + id);

            for (int j = 0, k = 0; j < sim; j++) {
                if(j%(print) == 0) {
                    writer.write("Day ");
                    writer.write(String.valueOf(k++));
                    writer.write('\n');
                    writer.write(simulation.toString());
                }
                simulation.iterate(dt);
            }
            writer.flush();

            System.out.println("End Simulation" + id);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
