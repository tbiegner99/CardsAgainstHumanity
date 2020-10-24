package com.tj.cardsagainsthumanity.models.gameplay.game;

import com.tj.cardsagainsthumanity.models.gameplay.Game;

import javax.persistence.Id;
import java.util.Collection;

public class AudienceMember {
    @Id
    private String memberId;

    private Integer gameId;

    private Game game;

    private Collection<Voter> votes;

}
