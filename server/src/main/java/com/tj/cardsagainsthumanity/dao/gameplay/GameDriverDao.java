package com.tj.cardsagainsthumanity.dao.gameplay;

import com.tj.cardsagainsthumanity.core.cache.BasicCache;
import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.factory.GameDriverFactory;
import com.tj.cardsagainsthumanity.models.gameplay.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.function.Supplier;

@Repository
public class GameDriverDao {
    private BasicCache<Integer, GameDriver> gameCache;
    private BasicCache<String, GameDriver> gameCodeCache;
    private GameDriverFactory factory;
    private GameDao gameDao;

    @Autowired
    public GameDriverDao(GameDriverFactory driverFactory, GameDao gameDao) {
        this.gameCache = new BasicCache<>();
        this.gameCodeCache = new BasicCache<>();
        this.gameDao = gameDao;
        this.factory = driverFactory;
    }

    public void clearGameCache() {
        gameCache.clear();
        gameCodeCache.clear();
    }

    public int getCacheSize() {
        return gameCache.size();
    }

    public GameDriver getGameByCode(String code) {
        Supplier<GameDriver> loadFromDatabase = () -> loadGameDriverByCode(code);

        return gameCodeCache.getItem(code)
                .orElseGet(loadFromDatabase);
    }

    public GameDriver getGame(Integer gameId) {
        Supplier<GameDriver> loadFromDatabase = () -> loadGameDriver(gameId);
        return gameCache.getItem(gameId)
                .orElseGet(loadFromDatabase);
    }

    public GameDriver loadGameDriverByCode(String gameCode) {
        return createGameDriver(gameDao.getGameByCode(gameCode));
    }

    public GameDriver loadGameDriver(Integer gameId) {
        return createGameDriver(gameDao.getGame(gameId));
    }

    public GameDriver createGameDriver(Game game) {
        GameDriver driver = factory.createGameDriver(game);
        addGameDriverToCache(driver);
        return driver;
    }

    public void saveGame(GameDriver driver) {
        gameDao.saveGame(driver.getGame());
        addGameDriverToCache(driver);
    }

    void addGameDriverToCache(GameDriver driver) {
        gameCodeCache.putItem(driver.getCode(), driver);
        gameCache.putItem(driver.getGameId(), driver);
    }

    public boolean isInCache(Integer gameId) {
        return gameCache.getItem(gameId).isPresent();
    }

    public boolean isInCache(String code) {
        return gameCodeCache.getItem(code).isPresent();
    }

    public void remove(GameDriver gameDriver) {
        gameCache.removeItem(gameDriver.getGameId());
        gameCodeCache.removeItem(gameDriver.getCode());
    }
}
