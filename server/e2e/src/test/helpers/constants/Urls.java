package helpers.constants;

import java.util.Optional;

public interface Urls {
    String  HUB_URL ="http:localhost:4444/wd/hub";
    String LOGIN = getBaseUrl()+ "/login";
    String LOGOUT = getBaseUrl()+ "/logout";
    String REGISTRATION = getBaseUrl()+ "/register";

    String BASE_AUTHENTICATED_URL=getBaseUrl()+"/auth";

    String HOME = BASE_AUTHENTICATED_URL+"/home";

    static String getBaseUrl() {
        Optional<String> baseUrl = Optional.ofNullable(System.getenv("UI_URL"));
        return baseUrl.orElse("http://host.docker.internal:8000");
    }
}
