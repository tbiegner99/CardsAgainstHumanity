package helpers.selectors;

import static helpers.selectors.SelectorsHelper.metaId;

public interface Login {
    String USERNAME_FIELD=metaId("username");
    String PASSWORD_FIELD=metaId("password");
    String REGISTER_LINK=metaId("register-link");
    String AUDIENCE_JOIN_LINK=metaId("audience-join-link");
    String SUBMIT_BUTTON=metaId("login-button");
}
