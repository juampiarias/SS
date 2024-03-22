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

    private void cellCompare (List<Particle> cell1, List<Particle> cell2, float rc, float increaseX, float increaseY) {
        cell1.forEach( p1 -> {
            cell2.forEach( p2 -> {
                p1.isNeighbor(p2, rc, increaseX, increaseY);
            });
        });
    }

    private void cellCompareCorner (List<Particle> cell1, List<Particle> cell2, float rc, float increaseX, float increaseY) {
        cell1.forEach( p1 -> {
            cell2.forEach( p2 -> {
                p1.isNeighborCorner(p2, rc, increaseX, increaseY);
            });
        });
    }

    public void cellIndexMethod (boolean openBorders) {
        Cell aux;
        List<Particle> l1;
        int cellCount = M*M;

        if (openBorders) {
            int i;
            for (i=0; i<M*(M-1); i++) {
                aux = cells.get(i);
                aux.findNeighbors(rc);

                l1 = aux.getParticles();
                if (i%M == 0) {
                    cellCompare(l1, cells.get(i+1).getParticles(), rc, 0, 0);
                    cellCompare(l1, cells.get(i+M-1).getParticles(), rc, L, 0);
                    cellCompare(l1, cells.get(i+M).getParticles(), rc, 0, 0);
                    cellCompare(l1, cells.get(i+M+1).getParticles(), rc, 0, 0);
                } else if (i%M == M-1) {
                    cellCompare(cells.get(i+1).getParticles(), l1, rc, L, 0);
                    cellCompare(l1, cells.get(i+M-1).getParticles(), rc, 0, 0);
                    cellCompare(l1, cells.get(i+M).getParticles(), rc, 0, 0);
                } else {
                    cellCompare(l1, cells.get(i+1).getParticles(), rc, 0, 0);
                    cellCompare(l1, cells.get(i+M-1).getParticles(), rc, 0, 0);
                    cellCompare(l1, cells.get(i+M).getParticles(), rc, 0, 0);
                    cellCompare(l1, cells.get(i+M+1).getParticles(), rc, 0, 0);
                }
            }

            //M*(M-1)
            i = M*(M-1);
            aux = cells.get(i);
            aux.findNeighbors(rc);
            l1 = aux.getParticles();
            cellCompare(l1, cells.get(i+1).getParticles(), rc, 0, 0);
            cellCompare(l1, cells.get(i+M-1).getParticles(), rc, L, 0);
            cellCompare(cells.get((i+M)%cellCount).getParticles(), l1,  rc, 0, L);
            cellCompare(cells.get((i+M+1)%cellCount).getParticles(), l1, rc, 0, L);
            //caso especial

            cellCompareCorner(l1, cells.get(M-1).getParticles(), rc, L, L);

            //Last Row without corners
            for (i = (M*(M-1))+1; i<cellCount-1; i++) {
                aux = cells.get(i);
                aux.findNeighbors(rc);
                l1 = aux.getParticles();
                cellCompare(l1, cells.get(i+1).getParticles(), rc, 0, 0);
                cellCompare(cells.get((i+M-1)%cellCount).getParticles(), l1, rc, 0, L);
                cellCompare(cells.get((i+M)%cellCount).getParticles(), l1,  rc, 0, L);
                cellCompare(cells.get((i+M+1)%cellCount).getParticles(), l1, rc, 0, L);
            }

            i=(M*M)-1;
            aux = cells.get(i);
            aux.findNeighbors(rc);
            l1 = aux.getParticles();
            cellCompare(cells.get((i+1)%cellCount).getParticles(), l1, rc, L, L);
            cellCompare(cells.get((i+M-1)%cellCount).getParticles(), l1, rc, 0, L);
            cellCompare(cells.get((i+M)%cellCount).getParticles(), l1,  rc, 0, L);

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
        float rc = 1f;
        int L = 20;
        int M = 13;
        int N = 300;
        List<Cell> cells = new ArrayList<>();
        for (int i=0; i<M*M; i++) {
            cells.add(new Cell(L/(float)M, i));
        }
//        String csvFile = "/Users/juaarias/Documents/SS/tp1/particles.csv";
        String csvFile = "/home/gelewaut/itba/SS/tp1/python/300.csv";
        String line;
        String splitter = ";";
        List<Particle> particles = new ArrayList<>();
        int id = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitter);
                Particle particle = new Particle(Integer.parseInt(data[0]), Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3]));
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
        long startTime = System.nanoTime();
        board.cellIndexMethod(true);
        long endTime = System.nanoTime();
        double durationSeconds = (endTime - startTime) / 1e9;
//        System.out.println(durationSeconds);
        System.out.println(board);
    }
}
