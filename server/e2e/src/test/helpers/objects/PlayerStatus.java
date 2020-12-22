package helpers.objects;

import helpers.HelperActions;

import java.util.ArrayList;
import java.util.List;

public class PlayerStatus {

    private HelperActions czar;
    private List<HelperActions> players;

    public static PlayerStatus findCzar(HelperActions... allPlayers) {
        PlayerStatus status = new PlayerStatus();
        status.players=new ArrayList<>();
        for(HelperActions player : allPlayers) {
            if(player.isCzar()) {
                status.czar=player;
            } else {
                status.players.add(player);
            }
        }
        return status;
    }

    public HelperActions getCzar() {
        return czar;
    }

    public List<HelperActions> getPlayers() {
        return players;
    }
}
