import java.util.ArrayList;
import java.util.List;

public class tp2 {
    public static void main(String[] args) {
        //TODO: importar config
        int L = 20;
        int M = 13;
        double rc = 1;
        int iterations = 1000;

        OffLattice board = new OffLattice(L, M, rc);

        //TODO: importar particulas
        List<Bird> birdList = new ArrayList<>();

        for(int i=0; i<iterations; i++) {
            board.iterate(birdList);
            //TODO: print particles
        }
    }
}
