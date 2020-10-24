package com.tj.cardsagainsthumanity.services.gameplay;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.dao.gameplay.GameDriverDao;
import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.models.gameplay.game.Scoreboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class GameService {
    private GameDriverDao gameDriverDao;

    @Autowired
    public GameService(GameDriverDao gameDriverDao) {
        this.gameDriverDao = gameDriverDao;
    }

    public GameDriver newGame() {
        Game game = createNewGame();
        GameDriver driver = gameDriverDao.createGameDriver(game);
        gameDriverDao.saveGame(driver);

        return driver;
    }

    private Game createNewGame() {
        Game ret = new Game();
        ret.setCode(generateGameCode());
        return ret;
    }

    private String generateGameCode() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 4).toUpperCase();
    }

    public GameDriver startGame(Integer gameId) {
        GameDriver driver = gameDriverDao.getGame(gameId);
        driver.start();
        return driver;
    }


    public GameDriver joinGame(Player player, String gameCode) {
        GameDriver driver = gameDriverDao.getGameByCode(gameCode);
        driver.addPlayer(player);
        return driver;
    }


    public Scoreboard getScore(Integer gameId) {
        return gameDriverDao.getGame(gameId).getScore();
    }

}
