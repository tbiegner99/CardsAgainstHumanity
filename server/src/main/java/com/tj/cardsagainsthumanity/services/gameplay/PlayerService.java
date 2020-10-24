package com.tj.cardsagainsthumanity.services.gameplay;

import com.tj.cardsagainsthumanity.dao.gameplay.PlayerDao;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PlayerService {
    private PlayerDao dao;

    @Autowired
    public PlayerService(PlayerDao dao) {
        this.dao = dao;
    }

    public Player getPlayerByEmail(String email) {
            return dao.getPlayerByEmail(email);
    }

    public Player getPlayerById(Integer id) {
        return dao.getPlayerById(id);
    }

    public Player createPlayer(Player player) {
        return dao.savePlayer(player);
    }
}
