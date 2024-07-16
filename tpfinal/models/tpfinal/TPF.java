package tpfinal;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TPF {

    public static void main(String[] args) {
        String configFile = "tpfinal/configs/app.config";
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream(configFile)) {
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        double desired = Double.parseDouble(prop.getProperty("desired"));
        double tau = Double.parseDouble(prop.getProperty("tau"));
        int time = Integer.parseInt(prop.getProperty("time"));
        double dt = Double.parseDouble(prop.getProperty("dt"));
        int simulations = Integer.parseInt(prop.getProperty("simulations"));

        ExecutorService executor = Executors.newFixedThreadPool(60);

        for (int i = 0; i < simulations; i++) {
            Task task = new Task(i, prop.getProperty("java") + prop.getProperty("output"),
                    time, dt, desired, tau);
            executor.execute(task);
        }

        executor.shutdown();
    }
}

class Task implements Runnable {

    int id;
    String outputFile;

    int time;
    double dt;
    double desired;
    double tau;

    Simulation simulation;
    List<Person> guards;
    List<Person> fans;

    Task(int id, String outputFile, int time, double dt, double desired, double tau) {
        this.id = id;
        this.outputFile = outputFile;

        this.time = time;
        this.dt = dt;
        this.desired = desired;
        this.tau = tau;

        this.guards = new ArrayList<Person>();
        this.fans = new ArrayList<Person>();

        //fill guards and fans
        //TODO: GENERATORS
        Person guard = new Guard(1,-10,-10, -desired,0, 0.25, 80, desired, tau);
        guards.add(guard);

        Person fan = new Fan(1,-20,0, desired,0, 0.25, 80, desired, tau);
        fans.add(fan);

        this.simulation = new Simulation(guards, fans);
    }

    @Override
    public void run() {
        simulation.initialize(dt);

        boolean finished = false;

        try {
            System.out.println("Start sim " + id);
            FileWriter fanWriter = new FileWriter(outputFile + "_fan" + id + ".csv");
            FileWriter guardWriter = new FileWriter(outputFile + "_guard" + id + ".csv");
            for (int i=0; i<time && !finished; i++) {
                finished = simulation.iterate(dt);
                guardWriter.write("T"+i+"\n");
                fanWriter.write("T"+i+"\n");
                for (Person g : guards){
                    guardWriter.write(g.toString());
                }
                for (Person f : fans){
                    fanWriter.write(f.toString());
                }
            }

            fanWriter.flush();
            guardWriter.flush();
            System.out.println("End sim " + id);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
