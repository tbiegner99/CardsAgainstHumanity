package scenarios;

import helpers.HelperActions;
import helpers.HelperActionsFactory;
import helpers.constants.Urls;
import helpers.constants.Users;
import helpers.objects.GameInfo;
import helpers.objects.PlayerStatus;
import helpers.objects.UserData;
import helpers.selectors.JoinGame;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RegistrationTest {

    HelperActions player1;
    HelperActions player2;
    HelperActions player3;

    @Before
    public void initiate() {
        player1 = HelperActionsFactory.chromeHelper();
    }

    @After
    public void teardown() {
        player1.terminate();
    }


    @Test
    public void test() throws InterruptedException {
        UserData userData = player1.registerUser();
        player1.holdForDemo(5);
        player1.getBrowserActions().waitForUrl(Urls.HOME);
        player1.logout();
        player1.getBrowserActions().waitForUrl(Urls.LOGIN);
        player1.holdForDemo(5);
        player1.login(userData.getEmail(),userData.getPassword());
        player1.holdForDemo(5);
    }
}