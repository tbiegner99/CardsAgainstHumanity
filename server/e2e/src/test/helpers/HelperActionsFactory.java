package helpers;

import helpers.constants.Urls;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class HelperActionsFactory {
    public static HelperActions chromeHelper() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        return chromeHelper(options);
    }

    public static HelperActions chromeHelper(Rectangle position) {
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--window-position=" + position.x + "," + position.y)
                .addArguments("--windw-size=" + position.height + "," + position.width);
        return chromeHelper(opts);
    }

    public static HelperActions chromeHelper(ChromeOptions opts) {
        try {
            opts.addArguments("--auto-open-devtools-for-tabs");
            WebDriver browser = new RemoteWebDriver(new URL(Urls.HUB_URL), opts);
            return new HelperActions(browser);
        } catch (Exception e) {
            throw new RuntimeException("Unable to construct chrome browser helper", e);
        }
    }

}
