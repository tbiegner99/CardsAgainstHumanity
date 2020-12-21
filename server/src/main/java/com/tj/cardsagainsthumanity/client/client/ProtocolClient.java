package com.tj.cardsagainsthumanity.client.client;

import com.tj.cardsagainsthumanity.client.io.InputReader;
import com.tj.cardsagainsthumanity.client.io.OutputWriter;
import com.tj.cardsagainsthumanity.client.io.connection.ServerConnection;
import com.tj.cardsagainsthumanity.client.options.*;
import com.tj.cardsagainsthumanity.client.options.factory.OptionContextFactory;
import com.tj.cardsagainsthumanity.client.options.factory.OptionSetDriver;
import com.tj.cardsagainsthumanity.client.options.processor.result.ProcessorResult;
import com.tj.cardsagainsthumanity.exceptions.RuntimeInterruptException;
import com.tj.cardsagainsthumanity.exceptions.StateNotAffectedException;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.models.gameStatus.PlayerGameStatus;
import com.tj.cardsagainsthumanity.models.gameStatus.RoundStatus;
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
    private GameStatus gameState;
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

        gameState = PlayerGameStatus.empty();
        OptionContext initialContext = optionContextFactory.createOptionContext(connection, Optional.empty(), gameState);
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
                    OptionContext nextContext = optionContextFactory.createOptionContext(connection, currentContext.getPlayer(), gameState);
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

    private GameStatus getStateFromResult(ProcessorResult processorResult) {
        Optional<GameStatus> processResult = Optional.ofNullable(processorResult)
                .filter(result -> result.hasStateChanged())
                .map(result -> result.getNewState().get());

        return processResult.orElse(currentContext.getGameState());
    }

    private ProcessorResult processOption(Option opt, OptionContext currentContext) {
        return optionProcessor.processOption(opt, currentContext);
    }

    @Override
    public void onRoundStateChanged(RoundStatus gameStatus) {

        OptionContext nextContext = optionContextFactory.createOptionContext(connection, currentContext.getPlayer(), currentContext.getGameState());
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
        GameStatus currentGameState = currentContext.getGameState();
        OptionContext nextContext = optionContextFactory.createOptionContext(connection, currentContext.getPlayer(), gameStatus);
        interruptWithNewContext(nextContext);
    }
}


