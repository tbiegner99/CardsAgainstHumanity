import BaseActionCreator from './BaseActionCreator';
import GameCommands from '../gameSocket/GameCommands';
import WebsocketDatasource from '../datasource/WebsocketDatasource';
import GameActions from '../actions/GameActions';
import Commands from '../utils/Commands';
import Urls from '../utils/Urls';
import GameStates from '../utils/GameStates';

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

  async joinGame(code) {
    const gameResponse = await this.websocketDatasource.sendCommand(GameCommands.JOIN_GAME, {
      code
    });

    console.log(gameResponse);
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

  async createGame() {
    const commandResponse = await this.websocketDatasource.sendCommand(
      GameCommands.CREATE_GAME,
      {}
    );

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
