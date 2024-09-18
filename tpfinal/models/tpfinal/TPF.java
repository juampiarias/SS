package tpfinal;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

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
        int guardStart = Integer.parseInt(prop.getProperty("guardStart"));
        int guardEnd = Integer.parseInt(prop.getProperty("guardEnd"));
        double circleRadius = Double.parseDouble(prop.getProperty("circleRadius"));
        double circleExtension = Double.parseDouble(prop.getProperty("circleExtension"));
        boolean animation = Boolean.parseBoolean(prop.getProperty("animation"));

        if (!animation) {
            for (int g = guardStart; g <= guardEnd; g++) {
                System.out.println("Start Sim " + g);
                ExecutorService executor = Executors.newFixedThreadPool(1000);

                List<Callable<TaskResponse>> tasks = new ArrayList<>();

                for (int i = 0; i < simulations; i++) {
                    Task task = new Task(i, time, dt, g, desired, tau, circleRadius, circleExtension);
                    tasks.add(task);
                }

                try {
                    List<Future<TaskResponse>> results = executor.invokeAll(tasks);

                    System.out.println("Parsing Results " + g);
                    List<TaskResponse> taskResponses = new ArrayList<>();
                    for (Future<TaskResponse> future : results) {
                        try {
                            taskResponses.add(future.get());
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("Writing Results " + g);
                    try (FileWriter writer = new FileWriter(prop.getProperty("java") + prop.getProperty("output") + g + ".csv")) {
                        for (TaskResponse taskResponse : taskResponses) {
                            writer.write(taskResponse.toString());
                        }
                        writer.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    executor.shutdown();
                }
            }
        } else {
            Generator generator = new Generator(guardStart, desired, tau, circleRadius, circleExtension);
            List<Person> guards = generator.generateGuards();
            Person fan = generator.generateFan();
            Simulation simulation = new Simulation(guards, fan);
            simulation.initialize(dt);
            boolean finished = false;

            try {
                System.out.println("Start Animation");
                FileWriter fanWriter = new FileWriter(prop.getProperty("java") + prop.getProperty("output") + "_fan" + guardStart + ".csv");
                FileWriter guardWriter = new FileWriter(prop.getProperty("java") + prop.getProperty("output") + "_guard" + guardStart + ".csv");
                for (int i=0; i<time && !finished; i++) {
                    finished = simulation.iterate(dt);
                    guardWriter.write("T"+i+"\n");
                    fanWriter.write("T"+i+"\n");
                    for (Person g : guards){
                        guardWriter.write(g.toString());
                    }
                    fanWriter.write(fan.toString());
                }

                fanWriter.flush();
                guardWriter.flush();
                System.out.println("End Animation");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

class Task implements Callable<TaskResponse> {

    int id;
    int time;
    double dt;

    Generator generator;

    Simulation simulation;
    List<Person> guards;
    Person fan;

    Task(int id, int time, double dt, int guardCount, double desired, double tau, double circleRadius, double circleExtension) {
        this.id = id;
        this.time = time;
        this.dt = dt;

        this.generator = new Generator(guardCount, desired, tau, circleRadius, circleExtension);

        this.guards = generator.generateGuards();
        this.fan = generator.generateFan();

        this.simulation = new Simulation(guards, fan);
    }

    @Override
    public TaskResponse call() throws Exception {
        System.out.println("Start sim " + id);
        simulation.initialize(dt);
        boolean finished = false;

        int i;
        for (i=0; i<time && !finished; i++) {
            finished = simulation.iterate(dt);
        }
        System.out.println("End sim " + id);
        return new TaskResponse(fan.success(), i*dt);
    }
}