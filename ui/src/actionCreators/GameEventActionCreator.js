import BaseActionCreator from './BaseActionCreator';
import GameCommands from '../gameSocket/GameCommands';
import WebsocketDatasource from '../datasource/WebsocketDatasource';
import GameActions from '../actions/GameActions';
import Commands from '../utils/Commands';
import Urls from '../utils/Urls';
import GameStates from '../utils/GameStates';
import Cookies from 'js-cookie';

const isStarted = (gameData) => gameData.state === GameStates.GAME_STARTED;

class GameEventActionCreator extends BaseActionCreator {
  constructor(websocketDatasource) {
    super();
    this.websocketDatasource = websocketDatasource;
    this.setupCommandHandlers();
  }

  setupCommandHandlers() {
    this.websocketDatasource.addCommandHandler(
      Commands.GAME_STATUS,
      this.onGameStatusReceived.bind(this)
    );
  }

  loadGame(gameId) {
    return this.loadGameFromIdOrCode(gameId);
  }

  async loadGameFromIdOrCode(gameId, code) {
    const gameResponse = await this.websocketDatasource.sendCommand(GameCommands.LOAD_GAME, {
      gameId,
      code
    });

    this.dispatch({
      type: GameActions.GAME_STATUS,
      data: gameResponse
    });
  }

  async loadGameStatus() {
    const gameResponse = await this.websocketDatasource.sendCommand(GameCommands.GAME_STATUS, {});

    this.dispatch({
      type: GameActions.GAME_STATUS,
      data: gameResponse
    });
  }

  async loadGameFromCode(code) {
    return this.loadGameFromIdOrCode(null, code);
  }

  async audienceJoinGame(code) {
    await this.joinGame(code);
    this.changeUrl(Urls.parameterize(Urls.Audience.GAME, { gameId: code }));
  }

  async joinGame(code) {
    const gameResponse = await this.websocketDatasource.sendCommand(GameCommands.JOIN_GAME, {
      code
    });

    Cookies.set('currentGame', code);

    this.dispatch({
      type: GameActions.GAME_JOINED
    });
    this.dispatch({
      type: GameActions.GAME_STATUS,
      data: gameResponse
    });

    if (isStarted(gameResponse)) {
      const { gameId } = gameResponse;
      this.changeUrl(Urls.parameterize(Urls.PLAY_GAME, { gameId }));
    }
  }

  onGameStatusReceived(commandData) {
    this.dispatch({
      type: GameActions.GAME_STATUS,
      data: commandData
    });
  }

  async createGame(deckId) {
    const commandResponse = await this.websocketDatasource.sendCommand(GameCommands.CREATE_GAME, {
      deckId
    });
    const { code } = commandResponse;

    Cookies.set('currentGame', code);

    this.dispatch({
      type: GameActions.GAME_CREATED,
      data: commandResponse
    });
  }

  async startGame(gameId) {
    await this.websocketDatasource.sendCommand(GameCommands.GAME_STARTED, {
      gameId
    });

    this.dispatch({
      type: GameActions.GAME_STARTED
    });
    this.changeUrl(Urls.parameterize(Urls.PLAY_GAME, { gameId }));
  }

  async leaveGame() {}

  async socketEventReceived() {}
}

export default new GameEventActionCreator(WebsocketDatasource);
