package tpfinal;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

///*
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
        int guards = Integer.parseInt(prop.getProperty("guards"));
        int fans = Integer.parseInt(prop.getProperty("fans"));
        double circleRadius = Double.parseDouble(prop.getProperty("circleRadius"));
        double circleExtension = Double.parseDouble(prop.getProperty("circleExtension"));

        ExecutorService executor = Executors.newFixedThreadPool(60);

        List<Callable<TaskResponse>> tasks = new ArrayList<>();

        for (int i = 0; i < simulations; i++) {
            Task task = new Task(i, time, dt, guards, fans, desired, tau, circleRadius, circleExtension);
            tasks.add(task);
        }

        try{
            List<Future<TaskResponse>> results = executor.invokeAll(tasks);

            List<TaskResponse> taskResponses = new ArrayList<>();
            for (Future<TaskResponse> future : results) {
                try {
                    taskResponses.add(future.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            try (FileWriter writer = new FileWriter(prop.getProperty("java") + prop.getProperty("output") + ".csv"))
            {
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
}

class Task implements Callable<TaskResponse> {

    int id;
    int time;
    double dt;

    Generator generator;

    Simulation simulation;
    List<Person> guards;
    Person fan;

    Task(int id, int time, double dt, int guardCount, int fanCount, double desired, double tau, double circleRadius, double circleExtension) {
        this.id = id;
        this.time = time;
        this.dt = dt;

        this.generator = new Generator(guardCount, fanCount, desired, tau, circleRadius, circleExtension);

        this.guards = generator.generateGuards();
        this.fan = generator.generateFans().getFirst();

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
//*/

/*
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
        int guards = Integer.parseInt(prop.getProperty("guards"));
        int fans = Integer.parseInt(prop.getProperty("fans"));
        double circleRadius = Double.parseDouble(prop.getProperty("circleRadius"));
        double circleExtension = Double.parseDouble(prop.getProperty("circleExtension"));

        ExecutorService executor = Executors.newFixedThreadPool(60);
        Generator generator = new Generator(guards, fans, desired, tau, circleRadius, circleExtension);

        for (int i = 0; i < simulations; i++) {
            Task task = new Task(i, prop.getProperty("java") + prop.getProperty("output"),
                    time, dt, generator);
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

    Generator generator;

    Simulation simulation;
    List<Person> guards;
    List<Person> fans;

    Task(int id, String outputFile, int time, double dt, Generator generator) {
        this.id = id;
        this.outputFile = outputFile;

        this.time = time;
        this.dt = dt;

        this.generator = generator;

        this.guards = generator.generateGuards();
        this.fans = generator.generateFans();

        this.simulation = new Simulation(guards, fans.getFirst());
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
 */