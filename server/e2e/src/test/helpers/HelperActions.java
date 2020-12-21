package helpers;

import helpers.constants.Urls;
import helpers.objects.GameInfo;
import helpers.objects.UserData;
import helpers.selectors.*;
import org.openqa.selenium.WebDriver;

import java.util.UUID;

public class HelperActions {

    private final WebDriver browser;
    private final BrowserHelper browserActions;

    private final int DEMO_HOLD_TIME = 20;


    public HelperActions(WebDriver browser) {
        this.browser = browser;
        this.browserActions = new BrowserHelper(browser);
    }

    public WebDriver getBrowser() {
        return browser;
    }

    public BrowserHelper getBrowserActions() {
        return browserActions;
    }

    public void terminate() {
        browser.close();
    }

    public void holdForDemo(int timeSecs) {
        try {
            Thread.sleep(timeSecs * 1000);
        } catch (InterruptedException e) {
        }
    }

    public void holdForDemo() {
        holdForDemo(DEMO_HOLD_TIME);
    }

    public GameInfo createNewGame() {
        browserActions.navigateTo(Urls.HOME);
        browserActions.clickElement(NewGame.NEW_GAME_MENU_ITEM);
        browserActions.clickElement(NewGame.CREATE_BUTTON);
        String gameCode = browserActions.getElementText(NewGame.GAME_CODE);
        String gameId = browserActions.getElementText(NewGame.GAME_ID);

        return new GameInfo(gameCode, gameId);
    }

    public void startGame() {
        browserActions.clickElement(NewGame.START_GAME_BUTTON);
    }

    private UserData generateUserData() {
        String randomId = UUID.randomUUID().toString();
        String email = "test" + randomId + "@example.com";
        String displayName = randomId;
        String firstName = randomId;
        String lastName = randomId;
        String password = "test1234";
        return new UserData(email, displayName, firstName, email, password);
    }


    public UserData registerUser() {
        UserData user = generateUserData();
        browserActions.navigateTo(Urls.LOGIN);
        browserActions.clickElement(Login.REGISTER_LINK);
        browserActions.enterInput(Register.DISPLAY_NAME_FIELD, user.getDisplayName());
        browserActions.enterInput(Register.FIRST_NAME_FIELD, user.getFirstName());
        browserActions.enterInput(Register.LAST_NAME_FIELD, user.getLastName());
        browserActions.enterInput(Register.EMAIL_FIELD, user.getEmail());
        browserActions.enterInput(Register.CONFIRM_EMAIL_FIELD, user.getEmail());
        browserActions.enterInput(Register.PASSWORD_FIELD, user.getPassword());
        browserActions.enterInput(Register.CONFIRM_PASSWORD_FIELD, user.getPassword());
        browserActions.clickElement(Register.SUBMIT_BUTTON);
        return user;
    }

    public void logout() {
        browserActions.navigateTo(Urls.LOGOUT);
        browserActions.waitForUrl(Urls.LOGIN);
    }

    public void login(String username, String password) {
        browserActions.navigateTo(Urls.LOGIN);
        browserActions.enterInput(Login.USERNAME_FIELD, username);
        browserActions.enterInput(Login.PASSWORD_FIELD, password);
        browserActions.clickElement(Login.SUBMIT_BUTTON);

    }


    public void joinGame(GameInfo game) {
        browserActions.navigateTo(Urls.HOME);
        browserActions.clickElement(JoinGame.JOIN_GAME_MENU_ITEM);
        browserActions.enterInput(JoinGame.GAME_COE_INPUT, game.getCode());
        browserActions.clickElement(JoinGame.JOIN_GAME_BUTTON);
    }

    public boolean isCzar() {
        browserActions.waitForElement(GamePlay.GAMEPLAY_PAGE);
        return browserActions.elementExists(GamePlay.CZAR_PAGE);
    }

    public void selectFirstCard() {
        browserActions.clickElement(GamePlay.SELECTABLE_CARD);
    }

    public void makePlay() {
        makePlaySelection();
        browserActions.clickElement(GamePlay.MAKE_PLAY_BUTTON);

    }

    public void cancelPlaySelection() {
        browserActions.clickElement(GamePlay.CANCEL_PLAY_BUTTON);
    }

    public void makePlaySelection() {
        int cardsToSelect = Integer.parseInt(browserActions.getElementText(GamePlay.CARDS_TO_PLAY));
        for (int i = 0; i < cardsToSelect; i++) {
            selectFirstCard();
        }
    }

    public void revealNextPlay() {
        browserActions.clickElement(GamePlay.REVEAL_PLAY_BUTTON);
    }

    public void revealPlays() {
        int playsToReveal = Integer.parseInt(browserActions.getElementText(GamePlay.PLAYS_TO_REVEAL));
        for (int i = 0; i < playsToReveal; i++) {
            revealNextPlay();
        }
    }

    public void pickWinner() {
        revealPlays();
        browserActions.clickElement(GamePlay.CARD_PLAY);
        browserActions.clickElement(GamePlay.CONFIRM_WINNER);
    }


    public void nextRound() {
        browserActions.clickElement(GamePlay.NEXT_ROUND_BUTTON);
    }
}
