package tp5;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private final List<Particle> players;

    public Team() {
        this.players = new ArrayList<>();
    }

    public void addPlayer(Particle p) {
        this.players.add(p);
    }

    public List<Particle> getPlayers() {
        return players;
    }
}
