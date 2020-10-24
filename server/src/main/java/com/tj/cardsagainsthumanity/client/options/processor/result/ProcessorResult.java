package com.tj.cardsagainsthumanity.client.options.processor.result;

import com.tj.cardsagainsthumanity.client.model.GameState;

import java.util.Optional;

public class ProcessorResult {

    private Optional<GameState> newState;
    private Status status;

    public ProcessorResult(GameState newState, Status status) {
        this.newState = Optional.ofNullable(newState);
        this.status = status;
    }

    public static ProcessorResult noOp() {
        return new ProcessorResult(null, Status.NO_OP);
    }

    public static ProcessorResult success(GameState newState) {
        return new ProcessorResult(newState, Status.SUCCESS);
    }

    public static ProcessorResult failure(GameState newState) {
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

    public Optional<GameState> getNewState() {
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
