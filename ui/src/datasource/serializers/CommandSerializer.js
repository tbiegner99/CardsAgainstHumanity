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

  serializeNullCommand(commandName) {
    return {
      messageId: this.generateCommandId(),
      commandName,
      arguments: null
    };
  }

  serializeEmptyCommand(commandName) {
    return {
      messageId: this.generateCommandId(),
      commandName,
      arguments: {}
    };
  }

  serializeCreateGameCommand(data) {
    const { deckId } = data;
    return {
      messageId: this.generateCommandId(),
      commandName: GameCommands.CREATE_GAME,
      arguments: { deckId }
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

  serializeLoadGameCommand(data) {
    const { gameId } = data;
    return {
      messageId: this.generateCommandId(),
      commandName: GameCommands.LOAD_GAME,
      arguments: { gameId }
    };
  }

  serializeRevealCommand() {
    return this.serializeEmptyCommand(GameCommands.REVEAL_PLAY);
  }

  serializePlayCardCommand(data) {
    const { cardsToPlay, roundId } = data;
    return {
      messageId: this.generateCommandId(),
      commandName: GameCommands.PLAY_CARD,
      arguments: { roundId, cardsToPlay }
    };
  }

  serializeChooseWinnerCommand(data) {
    const { cards, id } = data;
    return {
      messageId: this.generateCommandId(),
      commandName: GameCommands.CHOOSE_WINNER,
      arguments: { id, cards }
    };
  }

  serializeCommands(commandName, data) {
    switch (commandName) {
      case GameCommands.CREATE_GAME:
        return this.serializeCreateGameCommand(data);
      case GameCommands.JOIN_GAME:
        return this.serializeJoinGameCommand(data);
      case GameCommands.GAME_STARTED:
        return this.serializeGameStartedCommand(data);
      case GameCommands.LOAD_GAME:
        return this.serializeLoadGameCommand(data);
      case GameCommands.PLAY_CARD:
        return this.serializePlayCardCommand(data);
      case GameCommands.REVEAL_PLAY:
        return this.serializeRevealCommand(data);
      case GameCommands.CHOOSE_WINNER:
        return this.serializeChooseWinnerCommand(data);
      case GameCommands.GAME_STATUS:
        return this.serializeNullCommand(commandName);
      case GameCommands.END_ROUND:
        return this.serializeEmptyCommand(commandName);
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

  deserializeRoundStatus(round) {
    if (!round) return null;
    const {
      allCardsIn,
      blackCard,
      czar,
      czarIsYou,
      id,
      judgementHasStarted,
      numberOfPlays,
      revealedPlays,
      roundOver,
      waitingForPlays,
      winner,
      canPlayCard,
      myPlay
    } = round;
    return {
      id,
      allCardsIn,
      blackCard: this.deserializeBlackCard(blackCard),
      czar: this.deserializePlayer(czar),
      czarIsYou,
      judgementHasStarted,
      numberOfPlays,
      revealedPlays,
      roundOver,
      waitingForPlays,
      winner,
      canPlayCard,
      myPlay
    };
  }

  deserializePlayer(player) {
    return {
      playerId: player.playerId,
      czarIndex: player.czarIndex,
      displayName: player.displayName,
      name: player.name,
      currentCzar: player.currentCzar,
      score: player.score
    };
  }

  deserializeWhiteCard(card) {
    return {
      text: card.text,
      id: card.id
    };
  }

  deserializeBlackCard(card) {
    return {
      text: card.text,
      id: card.id,
      numberOfAnswers: card.numberOfAnswers
    };
  }

  deserializeDeck(deck) {
    return {
      id: deck.id,
      name: deck.name
    };
  }

  deserializeHandCards(handCards) {
    if (!handCards) {
      return null;
    }
    return handCards.map(this.deserializeWhiteCard);
  }

  deserializeGameData(gameData) {
    const { gameId, state, deck, round, players, code, handCards } = gameData;
    return {
      gameId,
      code,
      gameState: state,
      deck: this.deserializeDeck(deck),
      currentHand: this.deserializeHandCards(handCards),
      currentRound: this.deserializeRoundStatus(round),
      players: players.map(this.deserializePlayer)
    };
  }

  deserializeGameResponse(responseData) {
    const { commandName, messageId, body: gameStatus } = responseData;
    return {
      commandName,
      messageId,
      ...this.deserializeGameData(gameStatus)
    };
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

  deserializeGameStatusCommand(commandData) {
    const { commandName, messageId, arguments: gameStatus } = commandData;
    return {
      commandName,
      messageId,
      ...this.deserializeGameData(gameStatus)
    };
  }

  deserializeCommand(commandName, commandData) {
    switch (commandName) {
      case GameCommands.GAME_STATUS:
        return this.deserializeGameStatusCommand(commandData);
      default:
        throw new Error(`Unknown command name: ${commandName}`);
    }
  }

  deserializeResponse(commandName, responseEvent) {
    const { data } = responseEvent;
    switch (commandName) {
      case GameCommands.GAME_STARTED:
        return {};
      case GameCommands.END_ROUND:
      case GameCommands.CHOOSE_WINNER:
      case GameCommands.REVEAL_PLAY:
      case GameCommands.LOAD_GAME:
      case GameCommands.JOIN_GAME:
      case GameCommands.CREATE_GAME:
      case GameCommands.PLAY_CARD:
      case GameCommands.GAME_STATUS:
        return this.deserializeGameResponse(data.response);
      default:
        throw new Error(`Unknown command name: ${commandName}`);
    }
  }
}

export default new CommandSerializer();
