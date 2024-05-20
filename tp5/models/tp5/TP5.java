package tp5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TP5 {

    public static void main(String[] args) {
        int xSide = 105;
        int ySide = 68;

        int time = 24*60*5; //5 minutes of play

        Random radius = new Random();

        BufferedReader localBr, awayBr;
        String homeFile = "tp5/ios/TrackingData_Local.csv";
        String awayFile = "tp5/ios/TrackingData_Visitante.csv";
        String homeLine, awayLine, splitter = ",";

        List<Team> homeTeam = new ArrayList<Team>();
        List<Team> awayTeam = new ArrayList<Team>();
        List<Particle> balls = new ArrayList<Particle>();

        try {
            localBr = new BufferedReader(new FileReader(homeFile));
            awayBr = new BufferedReader(new FileReader(awayFile));

            for (int i=0; i<3; i++) {
                localBr.readLine();
                awayBr.readLine();
            }

            int i=0;
            while (i<time) {
                if ((homeLine = localBr.readLine()) != null &&
                    (awayLine = awayBr.readLine()) != null) {

                    String[] homeData = homeLine.split(splitter);
                    String[] awayData = awayLine.split(splitter);

                    if (!homeData[31].equals("NaN")){
                        i++;
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

                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Loco loco = new Loco((0.5*xSide) - 10, 0.5*ySide, 10, 0, radius.nextDouble(0.25, 0.35), 80);
        Simulation simulation = new Simulation(loco);
        simulation.initializeLoco(homeTeam.getFirst(), awayTeam.getFirst(), balls.getFirst());

        try {
            FileWriter writer = new FileWriter("tp5/ios/simulation.csv");
            for (int i=0; i<time; i++) {
                simulation.iterate(homeTeam.get(i), awayTeam.get(i), balls.get(i));
                writer.write(simulation.toString());
            }

            writer.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
