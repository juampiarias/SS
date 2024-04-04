import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class tp2 {
    public static void main(String[] args) {
//        /*
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
        double L = Double.parseDouble(prop.getProperty("l"));
        double v = Double.parseDouble(prop.getProperty("v"));
        int M = Integer.parseInt(prop.getProperty("m"));
        double rc = Double.parseDouble(prop.getProperty("rc"));
        double eta = Double.parseDouble(prop.getProperty("eta"));
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
                                v,
                                L,
                                eta);
                particles.add(particle);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        OffLattice board = new OffLattice(L, M, rc);

        try {
            FileWriter writer = new FileWriter(prop.getProperty("java") + prop.getProperty("output"));
            FileWriter vaWriter = new FileWriter(prop.getProperty("java") + "va_" + prop.getProperty("output"));
            double sinSum = 0;
            double cosSum = 0;
            double aux = 0;

            for(int i=0; i<iterations; i++) {
                writer.write("t" + i + '\n');
                for (Bird b: particles) {
                    aux = Math.toRadians(b.getAngle());
                    sinSum += Math.sin(aux);
                    cosSum += Math.cos(aux);
                    writer.write(b.toString());
                }
                sinSum/= particles.size();
                cosSum/= particles.size();
                aux = Math.sqrt(Math.pow(sinSum,2) + Math.pow(cosSum,2));
                vaWriter.write(aux + "\n");
//                System.out.println(aux);
//                System.out.println(Math.toDegrees(Math.atan2(sinSum,cosSum)));
                board.iterate(particles);
                sinSum = 0;
                cosSum = 0;
            }
            writer.flush();
            vaWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
//         */
    }
}
