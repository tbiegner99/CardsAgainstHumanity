package com.tj.cardsagainsthumanity.client.options.processor.result;

import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;

import java.util.Optional;

public class ProcessorResult {

    private Optional<GameStatus> newState;
    private Status status;

    public ProcessorResult(GameStatus newState, Status status) {
        this.newState = Optional.ofNullable(newState);
        this.status = status;
    }

    public static ProcessorResult noOp() {
        return new ProcessorResult(null, Status.NO_OP);
    }

    public static ProcessorResult success(GameStatus newState) {
        return new ProcessorResult(newState, Status.SUCCESS);
    }

    public static ProcessorResult failure(GameStatus newState) {
        return new ProcessorResult(newState, Status.FAILURE);
    }

    public static ProcessorResult success() {
        return new ProcessorResult(null, Status.SUCCESS);
    }

    public static ProcessorResult failure() {
        return new ProcessorResult(null, Status.FAILURE);
    }

    public boolean hasStateChanged() {
        return newState.isPresent();
    }

    public Optional<GameStatus> getNewState() {
        return newState;
    }

    public boolean isNoOp() {
        return status == Status.NO_OP;
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        NO_OP,
        SUCCESS,
        FAILURE
    }

}
