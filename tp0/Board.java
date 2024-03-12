import java.util.*;

public class Board {
    float rc;
    int L;
    int M;
    int N;
    List<Cell> cells;

    public Board(int L, int M, int N, float rc) {
        this.L = L;
        this.M = M;
        this.N = N;
        this.rc = rc;
        this.cells = new ArrayList<>();
    }

    public void initialize() {
        for (int i=0; i<M*M; i++) {
            cells.add(new Cell(L/(float)M, i));
        }

        Particle aux;
        Random random = new Random();
        float cellSide = L/(float)M;

        for (int i = 0; i < N; i++) {
            float x = random.nextFloat(0, L);
            float y = random.nextFloat(0, L);
            aux = new Particle(i, x, y, 0);
            int xx = (int)(x/cellSide);
            int yy = (int)(y/cellSide);

            cells.get(xx + yy*M).addParticle(aux);

        }
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
        Board board = new Board(4,4,20,0.9f);
        board.initialize();
        board.cellIndexMethod();
        System.out.println(board);
    }


}
