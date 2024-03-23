import java.util.ArrayList;
import java.util.List;

public class OffLattice {
    double rc;
    double L;
    int M;

    public OffLattice(double L, int M, double rc) {
        this.L = L;
        this.M = M;
        this.rc = rc;
    }

    private void cellCompare (List<Bird> cell1, double rc) {
        Bird p;
        for (int i=0; i<cell1.size(); i++) {
            p = cell1.get(i);
            for (int j=i+1; j<cell1.size(); j++) {
                p.isNeighbor(cell1.get(j), rc, 0, 0);
            }
        }
    }

    private void cellCompare (List<Bird> cell1, List<Bird> cell2, double rc, double increaseX, double increaseY) {
        cell1.forEach( p1 -> {
            cell2.forEach( p2 -> {
                p1.isNeighbor(p2, rc, increaseX, increaseY);
            });
        });
    }

    private void cellCompareCorner (List<Bird> cell1, List<Bird> cell2, double rc, double increaseX, double increaseY) {
        cell1.forEach( p1 -> {
            cell2.forEach( p2 -> {
                p1.isNeighborCorner(p2, rc, increaseX, increaseY);
            });
        });
    }

    public void cellIndexMethod (List<List<Bird>> cells) {
        List<Bird> l1;
        int cellCount = M*M;
        int i;
        for (i=0; i<M*(M-1); i++) {
            l1 = cells.get(i);
            cellCompare(l1, rc);

            if (i%M == 0) {
                cellCompare(l1, cells.get(i+1), rc, 0, 0);
                cellCompare(l1, cells.get(i+M-1), rc, L, 0);
                cellCompare(l1, cells.get(i+M), rc, 0, 0);
                cellCompare(l1, cells.get(i+M+1), rc, 0, 0);
            } else if (i%M == M-1) {
                cellCompare(cells.get(i+1), l1, rc, L, 0);
                cellCompare(l1, cells.get(i+M-1), rc, 0, 0);
                cellCompare(l1, cells.get(i+M), rc, 0, 0);
            } else {
                cellCompare(l1, cells.get(i+1), rc, 0, 0);
                cellCompare(l1, cells.get(i+M-1), rc, 0, 0);
                cellCompare(l1, cells.get(i+M), rc, 0, 0);
                cellCompare(l1, cells.get(i+M+1), rc, 0, 0);
            }
        }

        //M*(M-1)
        i = M*(M-1);
        l1 = cells.get(i);
        cellCompare(l1, rc);
        cellCompare(l1, cells.get(i+1), rc, 0, 0);
        cellCompare(l1, cells.get(i+M-1), rc, L, 0);
        cellCompare(cells.get((i+M)%cellCount), l1,  rc, 0, L);
        cellCompare(cells.get((i+M+1)%cellCount), l1, rc, 0, L);

        //caso especial
        cellCompareCorner(l1, cells.get(M-1), rc, L, L);

        //Last Row without corners
        for (i = (M*(M-1))+1; i<cellCount-1; i++) {
            l1 = cells.get(i);
            cellCompare(l1, rc);
            cellCompare(l1, cells.get(i+1), rc, 0, 0);
            cellCompare(cells.get((i+M-1)%cellCount), l1, rc, 0, L);
            cellCompare(cells.get((i+M)%cellCount), l1,  rc, 0, L);
            cellCompare(cells.get((i+M+1)%cellCount), l1, rc, 0, L);
        }

        i=(M*M)-1;
        l1 = cells.get(i);
        cellCompare(l1, rc);
        cellCompare(cells.get((i+1)%cellCount), l1, rc, L, L);
        cellCompare(cells.get((i+M-1)%cellCount), l1, rc, 0, L);
        cellCompare(cells.get((i+M)%cellCount), l1,  rc, 0, L);
    }

    public List<List<Bird>> setCells (List<Bird> particles) {
        List<List<Bird>> cells = new ArrayList<>();
        for (int i=0; i<M*M; i++) {
            cells.add(new ArrayList<>());
        }

        particles.forEach( p -> {
            int xx = (int) (p.getX()/(L/(float)M));
            int yy = (int) (p.getY()/(L/(float)M));
            cells.get(xx + yy*M).add(p);
        });

        return cells;
    }

    public void moveBirds (List<Bird> particles) {
        particles.forEach(p -> p.moveBird());
    }

    public void iterate (List<Bird> particles) {
        List<List<Bird>> cells = setCells(particles);
        cellIndexMethod(cells);
        moveBirds(particles);
    }
}
