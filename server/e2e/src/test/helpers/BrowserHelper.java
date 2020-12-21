package helpers;

import helpers.selectors.GamePlay;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class BrowserHelper {

    private static int DEFAULT_POLLING_PERIOD = 500;
    private static int TIMEOUT_PERIOD = 5000;

    private final WebDriver browser;
    private final Actions actions;

    public BrowserHelper(WebDriver browser) {
        this.browser = browser;
        this.actions = new Actions(browser);
    }

    public void waitFor(Function<WebDriver,Boolean> condition) {
        waitFor(
                condition,
                new IllegalStateException("Condition was not met"),DEFAULT_POLLING_PERIOD
                );
    }

    public void waitFor(Function<WebDriver,Boolean> condition,int waitTime) {
        waitFor(
                condition,
                new IllegalStateException("Condition was not met"),waitTime
        );
    }
    public void waitFor(Function<WebDriver,Boolean> condition,String message) {
        waitFor(condition,new IllegalStateException(message),DEFAULT_POLLING_PERIOD);
    }

    public void waitFor(Function<WebDriver,Boolean> condition,String message,int waitTime) {
        waitFor(condition,new IllegalStateException(message),waitTime);
    }

    public void waitFor(Function<WebDriver,Boolean> condition,RuntimeException ex,int waitTime) {
        int timeWaited = 0;
        while (timeWaited < TIMEOUT_PERIOD) {
            if (condition.apply(browser)) {
                return;
            }
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
            }
            timeWaited += DEFAULT_POLLING_PERIOD;
        }
        throw ex;
    }

    public boolean elementExists(String selector) {
        try {
            return browser.findElement(By.cssSelector(selector))!=null;
        }catch (NoSuchElementException ex) {
            return false;
        }
    }

    public WebElement waitForElement(String selector) {
        return this.waitForElement(selector,DEFAULT_POLLING_PERIOD);
    }
    public WebElement waitForElement(String selector,int waitTime) {
        Function<WebDriver, Optional<WebElement>> findElement = (browser) -> {
            try {
                return Optional.of(browser.findElement(By.cssSelector(selector)));
            }catch(NoSuchElementException ex) {
                return Optional.empty();
            }
        };
        RuntimeException ex= new NoSuchElementException("unable to find element with selector '"+selector+"'");
        this.waitFor(findElement.andThen(el->el.isPresent()),ex,waitTime);
        return findElement.apply(browser).get();
    }

    public void clickElement(String selector) {
        this.waitForElement(selector).click();
    }


    public void clearTextElement(String selector) {
        WebElement el = this.waitForElement(selector);
        actions.doubleClick(el);
        el.sendKeys("\b");

    }

    public void enterInput(String selector, String input) {
        this.clearTextElement(selector);
        this.waitForElement(selector).sendKeys(input);
    }

    public void navigateTo(String url) {
        browser.navigate().to(url);
    }

    public void waitForUrl(String url) {
        this.waitFor(browser->browser.getCurrentUrl().equals(url));
    }

    public String getElementText(String selector) {
        return this.waitForElement(selector).getText();
    }
}
