package tp5;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TP5 {

    public static void main(String[] args) {
        String configFile = "tp5/configs/app.config";
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream(configFile)) {
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int start = Integer.parseInt(prop.getProperty("start_time"));
        int end = Integer.parseInt(prop.getProperty("end_time"));
        double desired = Double.parseDouble(prop.getProperty("desired"));
        double tau = Double.parseDouble(prop.getProperty("tau"));
        boolean desiredCycle = Boolean.parseBoolean(prop.getProperty("desired_cycle"));
        boolean tauCycle = Boolean.parseBoolean(prop.getProperty("tau_cycle"));

        int xSide = 105;
        int ySide = 68;

        int start_time = 25*60*start;
        int end_time = 25*60*end;

        Random radius = new Random();

        BufferedReader localBr, awayBr;
        String homeFile = "tp5/ios/TrackingData_Local.csv";
        String awayFile = "tp5/ios/TrackingData_Visitante.csv";
        String homeLine, awayLine, splitter = ",";

        List<Team> homeTeam = new ArrayList<Team>();
        List<Team> awayTeam = new ArrayList<Team>();
        List<Particle> balls = new ArrayList<Particle>();

        int counter = 0;

        try {
            localBr = new BufferedReader(new FileReader(homeFile));
            awayBr = new BufferedReader(new FileReader(awayFile));

            for (int i=0; i<3; i++) {
                localBr.readLine();
                awayBr.readLine();
            }

            for(int i=0; i<start_time; i++) {
                localBr.readLine();
                awayBr.readLine();
            }

            for (int i=start_time; i<end_time; i++) {
                if ((homeLine = localBr.readLine()) != null &&
                    (awayLine = awayBr.readLine()) != null) {

                    String[] homeData = homeLine.split(splitter);
                    String[] awayData = awayLine.split(splitter);

                    if (!homeData[31].equals("NaN")){
                        counter++;
                        Team homePlayers = new Team();
                        Team awayPlayers = new Team();
                        homeTeam.add(homePlayers);
                        awayTeam.add(awayPlayers);

                        for (int j=3; j<homeData.length-2; j+=2) {
                            if (!homeData[j].equals("NaN")) {
                                // Create Home Player
                                homePlayers.addPlayer(new Particle(
                                        Double.parseDouble(homeData[j])*xSide,
                                        Double.parseDouble(homeData[j+1])*ySide,
                                        radius.nextDouble(0.25, 0.35)
                                ));
                            }
                        }

                        for (int j=3; j<awayData.length-2; j+=2) {
                            if (!awayData[j].equals("NaN")) {
                                // Create Away Player
                                awayPlayers.addPlayer(new Particle(
                                        Double.parseDouble(awayData[j])*xSide,
                                        Double.parseDouble(awayData[j+1])*ySide,
                                        radius.nextDouble(0.25, 0.35)
                                ));
                            }
                        }

                        // Create Ball
                        balls.add(new Particle(
                                Double.parseDouble(homeData[31])*xSide,
                                Double.parseDouble(homeData[32])*ySide,
                                0.0
                        ));
                    }

                } else {
                    break;
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        double initializeX;
        int multiplier;
        if (balls.getFirst().x() - 10 > 0) {
            initializeX = balls.getFirst().x() - 10;
            multiplier = 1;
        } else {
            initializeX = balls.getFirst().x() + 10;
            multiplier = -1;
        }
        if (!desiredCycle && !tauCycle) {
            Loco loco = new Loco(initializeX, balls.getFirst().y(), desired*multiplier, 0, radius.nextDouble(0.25, 0.35), 80, desired, tau);
            Task task = new Task(0, counter, prop.getProperty("java") + prop.getProperty("output") + ".csv", homeTeam, awayTeam, balls, loco);
            task.run();
        } else {

            ExecutorService executor = Executors.newFixedThreadPool(60);

            if (desiredCycle) {
                desired = 0.1;
                Loco loco = new Loco(initializeX, balls.getFirst().y(), desired*multiplier, 0, radius.nextDouble(0.25, 0.35), 80, desired, tau);
                Task task = new Task(0, counter, prop.getProperty("java") + prop.getProperty("output") + "_v" + 0 + ".csv", homeTeam, awayTeam, balls, loco);
                executor.submit(task);
                desired = 0.5;
                double step = 0.5;
                end = (int) ((13.5-0.5)/step);
                for (int i=1; i<=end; i++) {
                    loco = new Loco(initializeX, balls.getFirst().y(), desired*multiplier, 0, radius.nextDouble(0.25, 0.35), 80, desired, tau);
                    task = new Task(i, counter, prop.getProperty("java") + prop.getProperty("output") + "_v" + i + ".csv", homeTeam, awayTeam, balls, loco);
                    desired += step;

                    executor.submit(task);
                }

            } else {
                tau = 0.1;
                double step = 0.1;
                end = (int) ((1-0.1)/step);
                for (int i=0; i<=end; i++) {
                    Loco loco = new Loco(initializeX, balls.getFirst().y(), desired*multiplier, 0, radius.nextDouble(0.25, 0.35), 80, desired, tau);
                    Task task = new Task(i, counter, prop.getProperty("java") + prop.getProperty("output") + "_tau" +i + ".csv", homeTeam, awayTeam, balls, loco);
                    tau += step;

                    executor.submit(task);
                }
            }

            executor.shutdown();
        }

    }
}

class Task implements Runnable {

    int id;
    int time;
    String outputFile;
    Simulation simulation;
    List<Team> home;
    List<Team> away;
    List<Particle> balls;

    Task(int id, int time, String outputFile,
         List<Team> home, List<Team> away, List<Particle> balls, Loco loco) {
        this.id = id;
        this.time = time;
        this.outputFile = outputFile;
        this.home = home;
        this.away = away;
        this.balls = balls;

        this.simulation = new Simulation(loco);
    }

    @Override
    public void run() {
        simulation.initializeLoco(home.getFirst(), away.getFirst(), balls.getFirst());

        try {
            System.out.println("Start sim " + id);
            FileWriter writer = new FileWriter(outputFile);
            for (int i=0; i<time; i++) {
                simulation.iterate(home.get(i), away.get(i), balls.get(i));
                writer.write(simulation.toString());
            }

            writer.flush();
            System.out.println("End sim " + id);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
