import uuid from 'uuid';
import GameCommands from '../../gameSocket/GameCommands';

const STATUSES = {
  OK: 200,
  NO_CONTENT: 204
};

const STATUS_MESSAGES = {
  [STATUSES.OK]: 'OK',
  [STATUSES.NO_CONTENT]: 'No Content'
};

const getStatusMessage = (status) => STATUS_MESSAGES[status] || STATUS_MESSAGES[STATUSES.OK];

class CommandSerializer {
  generateCommandId() {
    return uuid.v4();
  }

  serializeJoinGameCommand(data) {
    return {
      messageId: this.generateCommandId(),
      commandName: GameCommands.JOIN_GAME,
      arguments: {
        code: data.code
      }
    };
  }

  serializeCreateGameCommand() {
    return {
      messageId: this.generateCommandId(),
      commandName: GameCommands.CREATE_GAME,
      arguments: {}
    };
  }

  serializeGameStartedCommand(data) {
    const { gameId } = data;
    return {
      messageId: this.generateCommandId(),
      commandName: GameCommands.GAME_STARTED,
      arguments: { gameId }
    };
  }

  serializeCommands(commandName, data) {
    switch (commandName) {
      case GameCommands.CREATE_GAME:
        return this.serializeCreateGameCommand();
      case GameCommands.JOIN_GAME:
        return this.serializeJoinGameCommand(data);
      case GameCommands.GAME_STARTED:
        return this.serializeGameStartedCommand(data);
      default:
        throw new Error(`Unknown command name:${commandName}`);
    }
  }

  serializeCommandPacket(command) {
    return {
      messageType: 'COMMAND',
      body: command
    };
  }

  deserializeGameResponse(responseData) {
    const { code, players } = responseData.body;
    const statusData = this.deserializeGameStatus(responseData);
    return Object.assign({}, statusData, {
      code,
      players
    });
  }

  serializeCommandResponse(command, responseData) {
    const status = responseData.status || STATUSES.OK;

    return {
      messageType: 'RESPONSE',
      body: {
        status,
        statusMessage: responseData.statusMessage || getStatusMessage(status),
        messageId: command.messageId,
        body: responseData.data
      }
    };
  }

  serializeErrorResponse(command, err) {
    return {
      messageType: 'RESPONSE',
      body: {
        status: STATUSES.INTERNAL_ERROR,
        statusMessage: STATUS_MESSAGES[STATUSES.INTERNAL_ERROR],
        messageId: command.messageId,
        body: err.message
      }
    };
  }

  deserializeGameStatus(commandData) {
    const {
      commandName,
      messageId,
      arguments: { gameId, state, round, currentHand, players }
    } = commandData;
    return {
      commandName,
      messageId,
      gameId,
      gameState: state,
      currentHand,
      currentRound: round,
      players
    };
  }

  deserializeCommand(commandName, commandData) {
    switch (commandName) {
      case GameCommands.GAME_STATUS:
        return this.deserializeGameStatus(commandData);
      default:
        throw new Error(`Unknown command name: ${commandName}`);
    }
  }

  deserializeResponse(commandName, responseEvent) {
    const { data } = responseEvent;
    switch (commandName) {
      case GameCommands.GAME_STARTED:
        return {};
      case GameCommands.JOIN_GAME:
      case GameCommands.CREATE_GAME:
        return this.deserializeGameResponse(data.response);
      default:
        throw new Error(`Unknown command name: ${commandName}`);
    }
  }
}

export default new CommandSerializer();
