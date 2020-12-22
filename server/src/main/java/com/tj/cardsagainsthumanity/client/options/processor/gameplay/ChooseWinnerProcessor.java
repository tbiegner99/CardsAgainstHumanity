package com.tj.cardsagainsthumanity.client.options.processor.gameplay;

import com.tj.cardsagainsthumanity.client.io.connection.ServerConnection;
import com.tj.cardsagainsthumanity.client.options.OptionContext;
import com.tj.cardsagainsthumanity.client.options.OptionProcessor;
import com.tj.cardsagainsthumanity.client.options.processor.result.ProcessorResult;
import com.tj.cardsagainsthumanity.client.options.types.gameplay.ChooseWinnerOption;
import com.tj.cardsagainsthumanity.models.gameStatus.RoundStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundCardPlay;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.ChooseWinnerCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.RoundStatusResponse;
import org.springframework.stereotype.Component;

@Component
public class ChooseWinnerProcessor implements OptionProcessor<ChooseWinnerOption> {
    @Override
    public ProcessorResult processOption(ChooseWinnerOption option, OptionContext context) {
        RoundCardPlay play = option.getPlay();
        ChooseWinnerCommand command = new ChooseWinnerCommand(play);
        ServerConnection connection = context.getConnection();
        RoundStatusResponse response = connection.waitForResponse(RoundStatusResponse.class, command);
        RoundStatus roundStatus = response.getBody();

        return ProcessorResult.success(null);
    }
}
