import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class tp2 {
    public static void main(String[] args) {
        String config_file = "tp2/configs/app.config";
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream(config_file)) {
            prop.load(fis);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        try (FileInputStream fis = new FileInputStream(prop.getProperty("java") + prop.getProperty("file"))) {
            prop.load(fis);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        int N = Integer.parseInt(prop.getProperty("n"));
        int L = Integer.parseInt(prop.getProperty("l"));
        double v = Double.parseDouble(prop.getProperty("v"));
        int M = Integer.parseInt(prop.getProperty("m"));
        double rc = Double.parseDouble(prop.getProperty("rc"));
        int iterations = Integer.parseInt(prop.getProperty("iter"));

        String csvFile = prop.getProperty("java") + prop.getProperty("input");
        String line;
        String splitter = ";";
        List<Bird> particles = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitter);
                Bird particle =
                        new Bird(Integer.parseInt(data[0]),
                                Double.parseDouble(data[1]),
                                Double.parseDouble(data[2]),
                                0,
                                Double.parseDouble(data[3]),
                                v);
                particles.add(particle);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        OffLattice board = new OffLattice(L, M, rc);

        for(int i=0; i<iterations; i++) {
            board.iterate(particles);
            //TODO: print particles
        }
    }
}
