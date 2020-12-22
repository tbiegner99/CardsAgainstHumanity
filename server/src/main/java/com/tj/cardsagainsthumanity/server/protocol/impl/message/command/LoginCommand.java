package com.tj.cardsagainsthumanity.server.protocol.impl.message.command;

import com.tj.cardsagainsthumanity.server.protocol.impl.ProtocolCommandName;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.LoginInfo;

public class LoginCommand extends BaseCommand<LoginInfo> {

    public LoginCommand() {
        super(ProtocolCommandName.LOGIN, null);
    }

    public LoginCommand(LoginInfo info) {
        super(ProtocolCommandName.LOGIN, info);
    }

    @Override
    public boolean isLoginRequired() {
        return false;
    }

}
