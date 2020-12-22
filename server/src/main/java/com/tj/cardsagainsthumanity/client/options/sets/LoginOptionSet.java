package com.tj.cardsagainsthumanity.client.options.sets;

import com.tj.cardsagainsthumanity.client.options.types.LoginOption;
import com.tj.cardsagainsthumanity.client.options.types.common.ExitOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("initialOptions")
public class LoginOptionSet extends BaseOptionSet {

    @Autowired
    public LoginOptionSet(LoginOption loginOption, ExitOption exitOption) {
        super(loginOption, exitOption);
    }

}
