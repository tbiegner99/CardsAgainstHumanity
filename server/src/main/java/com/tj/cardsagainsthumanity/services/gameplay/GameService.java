package com.tj.cardsagainsthumanity.services.gameplay;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.dao.DeckDao;
import com.tj.cardsagainsthumanity.dao.gameplay.GameDriverDao;
import com.tj.cardsagainsthumanity.models.cards.DeckInfo;
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
    private static final Integer DEFAULT_DECK_ID = 1;
    private GameDriverDao gameDriverDao;
    private DeckDao deckDao;

    @Autowired
    public GameService(GameDriverDao gameDriverDao, DeckDao deckDao) {
        this.gameDriverDao = gameDriverDao;
        this.deckDao = deckDao;
    }

    public GameDriver newGame() {
        Game game = createNewGame(DEFAULT_DECK_ID);
        return createDriver(game);
    }

    public GameDriver newGame(Integer deckId) {
        Game game = createNewGame(deckId);
        return createDriver(game);
    }

    private GameDriver createDriver(Game game) {
        GameDriver driver = gameDriverDao.createGameDriver(game);
        gameDriverDao.saveGame(driver);

        return driver;
    }

    private Game createNewGame(Integer deckId) {
        Game ret = new Game();
        DeckInfo deck = deckDao.loadDeckInfo(deckId);
        ret.setDeck(deck);
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

    public GameDriver loadGame(String gameCode) {
        return gameDriverDao.getGameByCode(gameCode);
    }


    public Scoreboard getScore(Integer gameId) {
        return gameDriverDao.getGame(gameId).getScore();
    }

}
