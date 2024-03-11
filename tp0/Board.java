import java.util.ArrayList;
import java.util.List;
import java.util.Random

public class Board {
    float rc;
    int L;
    int M;
    int N;
    List<Cell> cells;
    int selected;


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
        this.selected = random.nextInt(1,N);
        for (int i = 0; i < N; i++) {
            float x = random.nextFloat(0, L);
            float y = random.nextFloat(0, L);
            aux = new Particle(i+1, x, y, i==selected);

        }
    }

    public void cellIndexMethod () {


    }

    public static void main(String[] args) {
        //crear n particulas y asignar a celdas

        //random 1-n

        //buscar neighbors de n

        //imprimir neighbors de n

    }


}
