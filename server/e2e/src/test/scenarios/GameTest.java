package scenarios;

import helpers.HelperActions;
import helpers.HelperActionsFactory;
import helpers.constants.Users;
import helpers.objects.GameInfo;
import helpers.objects.PlayerStatus;
import helpers.selectors.JoinGame;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Rectangle;

public class GameTest {

    HelperActions player1;
    HelperActions player2;
    HelperActions player3;

    @Before
    public void initiate() {
        player1 = HelperActionsFactory.chromeHelper(new Rectangle(0, 0, 1080, 550));
        player2 = HelperActionsFactory.chromeHelper(new Rectangle(0, 0, 1080, 550));
        player3 = HelperActionsFactory.chromeHelper(new Rectangle(550, 0, 1080, 550));

    }

    @After
    public void teardown() {
        player1.terminate();
          player2.terminate();
         player3.terminate();
    }

    @Test
    public void test() throws InterruptedException {
        player1.login(Users.Player1.USERNAME, Users.Player1.PASSWORD);
        player2.login(Users.Player2.USERNAME, Users.Player2.PASSWORD);
        player3.login(Users.Player3.USERNAME, Users.Player3.PASSWORD);
        GameInfo game = player1.createNewGame();
        player2.joinGame(game);
        player3.joinGame(game);
        Assert.assertEquals(player2.getBrowserActions().getElementText(JoinGame.GAME_ID), game.getGameId().toString());
        Assert.assertEquals(player3.getBrowserActions().getElementText(JoinGame.GAME_ID), game.getGameId().toString());
        player1.startGame();
        PlayerStatus roundPlayers=PlayerStatus.findCzar(player1,player2,player3);
        Assert.assertEquals(roundPlayers.getPlayers().size(),2);
        for(HelperActions player : roundPlayers.getPlayers()) {
            player.makePlay();
        }
        roundPlayers.getCzar().getBrowser().navigate().refresh();
        roundPlayers.getCzar().pickWinner();
        player1.holdForDemo(10);
        roundPlayers.getCzar().nextRound();
        player1.holdForDemo(300);
    }
}