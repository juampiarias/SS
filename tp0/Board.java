import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Board {
    float rc;
    int L;
    int M;
    int N;
    List<Cell> cells;

    public Board(int L, int M, int N, float rc, List<Cell> cells) {
        this.L = L;
        this.M = M;
        this.N = N;
        this.rc = rc;
        this.cells = cells;
    }

    public void cellIndexMethod (boolean openBorders) {
        Cell aux;
        int cellCount = M*M;

        if (openBorders) {
            int i;
            for (i=0; i<M*(M-1); i++) {
                aux = cells.get(i);
                aux.findNeighbors(rc);

                aux.findNeighbors(rc, cells.get( (i+1)%cellCount ), L, 0);
                aux.findNeighbors(rc, cells.get( (i+M-1)%cellCount ), L, 0);
                aux.findNeighbors(rc, cells.get( (i+M)%cellCount ), 0, 0);
                if (i%M != M-1)
                    aux.findNeighbors(rc, cells.get( (i+M+1)%cellCount ), 0, 0);
            }

            // last row
            for (i=M*(M-1); i<(M*M)-1; i++) {
                aux = cells.get(i);
                aux.findNeighbors(rc);
                aux.findNeighbors(rc, cells.get( (i+1)%cellCount ), L, 0);
                aux.findNeighbors(rc, cells.get( (i+M-1)%cellCount ), 0, L);
                aux.findNeighbors(rc, cells.get( (i+M)%cellCount ), 0, L);
                if (i%M != M-1)
                    aux.findNeighbors(rc, cells.get( (i+M+1)%cellCount ), 0, L);
            }

            // M*M-1 con +M-1 y +M
            i = (M*M)-1;
            aux = cells.get(i);
            aux.findNeighbors(rc);
            aux.findNeighbors(rc, cells.get( (i+M-1)%cellCount ), 0, L);
            aux.findNeighbors(rc, cells.get( (i+M)%cellCount ), 0, L);
            aux.findNeighbors(rc, cells.get( (i+1)%cellCount ), L, L);
        } else {
            int adjacent;
            for (int i=0; i<M*M; i++) {
                aux = cells.get(i);

                aux.findNeighbors(rc);

                adjacent = i+1;
                if (adjacent < cellCount && i%M!=M-1)
                    aux.findNeighbors(rc, cells.get(adjacent), 0, 0);
                adjacent = i+M-1;
                if (adjacent < cellCount && i%M!=0)
                    aux.findNeighbors(rc, cells.get(adjacent), 0, 0);
                adjacent = i+M;
                if (adjacent < cellCount)
                    aux.findNeighbors(rc, cells.get(adjacent), 0, 0);
                adjacent = i+M+1;
                if (adjacent < cellCount && i%M!=M-1)
                    aux.findNeighbors(rc, cells.get(adjacent), 0, 0);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        cells.forEach(c -> builder.append(c.toString()));
        return builder.toString();
    }

    public static void main(String[] args) {
        float rc = 0.9f;
        int L = 3;
        int M = 3;
        int N = 10;
        List<Cell> cells = new ArrayList<>();
        for (int i=0; i<M*M; i++) {
            cells.add(new Cell(L/(float)M, i));
        }
        String csvFile = "/Users/juaarias/Documents/SS/tp0/particles.csv";
        String line;
        String splitter = ";";
        List<Particle> particles = new ArrayList<>();
        int id = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitter);
                Particle particle = new Particle(id, Float.parseFloat(data[0]), Float.parseFloat(data[1]), Float.parseFloat(data[2]));
                particles.add(particle);
                id += 1;
                int xx = (int)(particle.getX()/(L/(float)M));
                int yy = (int)(particle.getY()/(L/(float)M));
                cells.get(xx + yy*M).addParticle(particle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Board board = new Board(L, M, N, rc, cells);
        board.cellIndexMethod(true);
        System.out.println(board);
    }
}
