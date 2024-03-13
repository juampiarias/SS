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

    public void cellIndexMethod () {
        Cell aux;
        int cellSize = M*M;
        int adjacent;
        for (int i=0; i<M*M; i++) {
            aux = cells.get(i);

            aux.findNeighbors(rc);

            adjacent = i+1;
            if (adjacent < cellSize && i%M!=M-1)
                aux.findNeighbors(rc, cells.get(adjacent));
            adjacent = i+M-1;
            if (adjacent < cellSize && i%M!=0)
                aux.findNeighbors(rc, cells.get(adjacent));
            adjacent = i+M;
            if (adjacent < cellSize)
                aux.findNeighbors(rc, cells.get(adjacent));
            adjacent = i+M+1;
            if (adjacent < cellSize && i%M!=M-1)
                aux.findNeighbors(rc, cells.get(adjacent));
        }
    }

    @Override
    public String toString() {
        return "Board{" +
                "rc=" + rc +
                ", L=" + L +
                ", M=" + M +
                ", N=" + N +
                ", cells=" + cells +
                '}';
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
        board.cellIndexMethod();
        System.out.println(board);
    }
}
