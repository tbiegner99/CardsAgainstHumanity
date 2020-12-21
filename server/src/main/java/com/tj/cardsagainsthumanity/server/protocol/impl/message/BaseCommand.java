package com.tj.cardsagainsthumanity.server.protocol.impl.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tj.cardsagainsthumanity.server.protocol.impl.ProtocolCommandName;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.*;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.ChooseWinnerCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.EndRoundCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.PlayCardCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.RevealPlayCommand;
import com.tj.cardsagainsthumanity.server.protocol.message.Command;
import com.tj.cardsagainsthumanity.server.protocol.message.Message;

import java.util.Objects;
import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "commandName")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "LOGIN", value = LoginCommand.class),
        @JsonSubTypes.Type(name = "CREATE_GAME", value = CreateGameCommand.class),
        @JsonSubTypes.Type(name = "JOIN", value = JoinGameCommand.class),
        @JsonSubTypes.Type(name = "START_GAME", value = StartGameCommand.class),
        @JsonSubTypes.Type(name = "GAME_STATUS", value = GameStatusCommand.class),
        @JsonSubTypes.Type(name = "ROUND_STATUS", value = RoundStatusCommand.class),
        @JsonSubTypes.Type(name = "PLAY_CARD", value = PlayCardCommand.class),
        @JsonSubTypes.Type(name = "REVEAL", value = RevealPlayCommand.class),
        @JsonSubTypes.Type(name = "CHOOSE_WINNER", value = ChooseWinnerCommand.class),
        @JsonSubTypes.Type(name = "END_ROUND", value = EndRoundCommand.class),
        @JsonSubTypes.Type(name = "LOAD_GAME", value = LoadGameCommand.class)
})
public abstract class BaseCommand<A> implements Command<A> {

    private String messageId;
    private ProtocolCommandName commandName;
    private A arguments;

    public BaseCommand() {
        setMessageId(UUID.randomUUID().toString());
    }

    public BaseCommand(ProtocolCommandName commandName, A arguments) {
        this();
        setCommandName(commandName);
        setArguments(arguments);
    }


    @Override
    public String getCommandName() {
        return commandName.name();
    }

    public void setCommandName(ProtocolCommandName commandName) {
        this.commandName = commandName;
    }

    @Override
    public A getArguments() {
        return arguments;
    }

    public void setArguments(A arguments) {
        this.arguments = arguments;
    }

    @Override
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String commandId) {
        this.messageId = commandId;
    }

    @Override
    @JsonIgnore
    public Message.Type getMessageType() {
        return Type.COMMAND;
    }

    @Override
    public boolean equals(Object o) {
        BaseCommand<?> that = (BaseCommand<?>) o;
        return getCommandName() == that.getCommandName() &&
                Objects.equals(getArguments(), that.getArguments());
    }


    @Override
    public String toString() {
        return "BaseCommand{" +
                "messageId='" + messageId + '\'' +
                ", commandName=" + commandName +
                ", arguments=" + arguments +
                '}';
    }
}
