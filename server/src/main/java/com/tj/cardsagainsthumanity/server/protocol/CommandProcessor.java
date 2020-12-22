package com.tj.cardsagainsthumanity.server.protocol;

import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;

public interface CommandProcessor<T extends Command, R extends Response> {
    R processMessage(T messageToProcess, CommandContext context);
}
