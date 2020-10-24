package com.tj.cardsagainsthumanity.client.client;

import com.tj.cardsagainsthumanity.client.io.InputReader;
import com.tj.cardsagainsthumanity.client.io.OutputWriter;
import com.tj.cardsagainsthumanity.client.io.connection.ServerConnection;
import com.tj.cardsagainsthumanity.client.model.GameState;
import com.tj.cardsagainsthumanity.client.options.*;
import com.tj.cardsagainsthumanity.client.options.factory.OptionContextFactory;
import com.tj.cardsagainsthumanity.client.options.factory.OptionSetDriver;
import com.tj.cardsagainsthumanity.client.options.processor.result.ProcessorResult;
import com.tj.cardsagainsthumanity.exceptions.RuntimeInterruptException;
import com.tj.cardsagainsthumanity.exceptions.StateNotAffectedException;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.GameStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Optional;

@Component
public class ProtocolClient extends Thread implements GameStateChangeHandler {
    private OptionManager optionManager;
    private InputReader inputReader;
    private OutputWriter outputWriter;
    private OptionProcessor<Option> optionProcessor;
    private GameState gameState;
    private ServerConnection connection;
    private OptionContextFactory optionContextFactory;
    private OptionSetDriver optionSetDriver;
    private OptionContext currentContext;

    @Autowired
    public ProtocolClient(OptionSetDriver optionSetDriver, OptionContextFactory optionContextFactory, OptionManager optionManager, InputReader inputReader, OutputWriter outputWriter, OptionProcessor<Option> optionProcessor, ServerConnection serverConnection) {
        this.optionManager = optionManager;
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
        this.optionProcessor = optionProcessor;
        this.connection = serverConnection;
        this.optionContextFactory = optionContextFactory;
        this.optionSetDriver = optionSetDriver;
    }

    @Override
    public void run() {

        gameState = GameState.initialState();
        OptionContext initialContext = optionContextFactory.createOptionContext(connection, gameState);
        setCurrentContext(initialContext);
        try {
            printWelcomeMessage();
            connectToServer();
            while (true) {
                try {
                    optionManager.prompt(outputWriter, getCurrentContext());
                    Option opt = optionManager.readOption(inputReader);
                    inputReader.clearInput();
                    ProcessorResult result = processOption(opt, getCurrentContext());
                    if (result.isNoOp()) {
                        continue;
                    }
                    gameState = getStateFromResult(result);
                    OptionContext nextContext = optionContextFactory.createOptionContext(connection, gameState);
                    setCurrentContext(nextContext);
                    OptionSet nextOptions = optionSetDriver.getOptionsFromContext(getCurrentContext());
                    optionManager.resetWithOptions(nextOptions, nextContext);
                } catch (RuntimeInterruptException e) {
                    outputWriter.writeLine("State has changed.");
                } catch (InputMismatchException e) {
                    inputReader.readLine();
                    outputWriter.writeLine("Illegal option provided. Please try again.");
                } catch (ArrayIndexOutOfBoundsException e) {
                    outputWriter.writeLine("Illegal option provided. Please try again.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public OptionContext getCurrentContext() {
        return currentContext;
    }

    public void setCurrentContext(OptionContext currentContext) {
        this.currentContext = currentContext;
    }

    private void printWelcomeMessage() {
    }

    private void connectToServer() throws IOException {
        connection.connect(this);
    }

    private GameState getStateFromResult(ProcessorResult processorResult) {
        Optional<GameState> processResult = Optional.ofNullable(processorResult)
                .filter(result -> result.hasStateChanged())
                .map(result -> result.getNewState().get());

        return processResult.orElse(currentContext.getGameState());
    }

    private ProcessorResult processOption(Option opt, OptionContext currentContext) {
        return optionProcessor.processOption(opt, currentContext);
    }

    @Override
    public void onRoundStateChanged(RoundStatus gameStatus) {
        GameState newGameState = GameState.builder(currentContext.getGameState())
                .setCurrentRound(gameStatus)
                .build();
        OptionContext nextContext = optionContextFactory.createOptionContext(connection, newGameState);
        interruptWithNewContext(nextContext);
    }

    private void interruptWithNewContext(OptionContext nextContext) {
        OptionSet nextOptions = optionSetDriver.getOptionsFromContext(nextContext);
        try {
            optionManager.resetWithOptionsIfChanged(nextOptions, nextContext);
            setCurrentContext(nextContext);
            this.interrupt();
        } catch (StateNotAffectedException e) {
            optionManager.resetWithOptions(nextOptions, nextContext);
            setCurrentContext(nextContext);
            this.interrupt();
        }
    }


    @Override
    public void onGameStateChanged(GameStatus gameStatus) {
        GameState currentGameState = currentContext.getGameState();
        OptionContext nextContext = optionContextFactory.createOptionContextFromGameStatus(connection, currentGameState, gameStatus);
        interruptWithNewContext(nextContext);
    }
}


