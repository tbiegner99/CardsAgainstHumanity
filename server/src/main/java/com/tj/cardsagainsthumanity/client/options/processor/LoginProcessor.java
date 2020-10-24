package com.tj.cardsagainsthumanity.client.options.processor;

import com.tj.cardsagainsthumanity.client.io.InputReader;
import com.tj.cardsagainsthumanity.client.io.OutputWriter;
import com.tj.cardsagainsthumanity.client.io.connection.ServerConnection;
import com.tj.cardsagainsthumanity.client.model.GameState;
import com.tj.cardsagainsthumanity.client.options.OptionContext;
import com.tj.cardsagainsthumanity.client.options.OptionProcessor;
import com.tj.cardsagainsthumanity.client.options.processor.result.ProcessorResult;
import com.tj.cardsagainsthumanity.client.options.types.LoginOption;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.BaseResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.LoginCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.LoginInfo;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body.LoginResponseBody;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class LoginProcessor implements OptionProcessor<LoginOption> {
    private final ServerConnection connection;
    private final Integer maxLoginAttempts;
    private InputReader inputReader;
    private OutputWriter outputWriter;


    @Autowired
    public LoginProcessor(@Qualifier("maxLoginAttempts") Integer maxLoginAttempts, InputReader reader, OutputWriter writer, ServerConnection connection) {
        this.inputReader = reader;
        this.outputWriter = writer;
        this.connection = connection;
        this.maxLoginAttempts = maxLoginAttempts;
    }

    @Override
    public ProcessorResult processOption(LoginOption option, OptionContext context) {
        Response<LoginResponseBody> response = login();
        if (!response.isErrorResponse()) {
            return processSuccessfulLogin(response.getBody(), context.getGameState());

        }

        return ProcessorResult.failure(context.getGameState());
    }

    private Response<LoginResponseBody> login() {
        Response<LoginResponseBody> response;
        int retries = 0;
        do {
            response = tryLogin();
            retries++;
            notifyLoginStatus(retries, response);
        } while (shouldRetry(retries, response));

        return response;
    }

    private ProcessorResult processSuccessfulLogin(LoginResponseBody playerInfo, GameState previousState) {
        return ProcessorResult.success(
                GameState.builder(previousState)
                        .setCurrentPlayer(playerInfo.getPlayerId(), playerInfo.getToken())
                        .build()
        );
    }

    private void notifyLoginStatus(int retries, Response loginResponse) {
        if (!loginResponse.isErrorResponse()) {
            outputWriter.writeLine("Logged in successfully!");
        } else if (retries < maxLoginAttempts) {
            outputWriter.writeLine("Login failed. Please try again.");
        } else {
            outputWriter.writeLine("Login failed. Max login attempts exceeded!");
        }
    }

    private boolean shouldRetry(int retries, Response response) {
        return response.isErrorResponse() && retries < maxLoginAttempts;
    }

    private Response tryLogin() {
        LoginCommand command = getLoginCommand();
        LoginInfo info = command.getArguments();
        outputWriter.writeLine("User: " + info.getUsername() + " Password: " + info.getPassword());
        connection.sendCommand(command);
        return connection.waitForResponse(LoginResponse.class, command.getMessageId());
    }


    private LoginCommand getLoginCommand() {
        LoginInfo info = new LoginInfo(getUser(), getPassword());
        return new LoginCommand(info);
    }

    private String getUser() {
        outputWriter.write("Player id: ");
        return inputReader.readLine();
    }

    private String getPassword() {
        outputWriter.write("Password: ");
        return inputReader.readLine();
    }

    public static class LoginResponse extends BaseResponse<LoginResponseBody> {
        public LoginResponse() {
        }
    }

}
