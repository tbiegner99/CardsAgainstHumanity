package helpers.selectors;

import static helpers.selectors.SelectorsHelper.metaId;

public interface Register {
    String DISPLAY_NAME_FIELD=metaId("displayName");
    String FIRST_NAME_FIELD=metaId("firstName");
    String LAST_NAME_FIELD=metaId("lastName");
    String EMAIL_FIELD=metaId("username");
    String CONFIRM_EMAIL_FIELD=metaId("match-username");
    String PASSWORD_FIELD=metaId("password");
    String CONFIRM_PASSWORD_FIELD=metaId("match-password");
    String SUBMIT_BUTTON=metaId("register-button");
}
