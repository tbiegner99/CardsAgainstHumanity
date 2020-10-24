import BaseActionCreator from './BaseActionCreator';
import GameCommands from '../gameSocket/GameCommands';
import WebsocketDatasource from '../datasource/WebsocketDatasource';
import GameActions from '../actions/GameActions';
import Commands from '../utils/Commands';
import Urls from '../utils/Urls';
import GameStates from '../utils/GameStates';

class GameEventActionCreator extends BaseActionCreator {
  constructor(websocketDatasource) {
    super();
    this.websocketDatasource = websocketDatasource;
    this.setupCommandHandlers();
  }

  setupCommandHandlers() {
    this.websocketDatasource.addCommandHandler(
      Commands.ROUND_STATUS,
      this.onRoundStatusReceived.bind(this)
    );
  }

  onRoundStatusReceived() {}

  makePlay() {}

  declareWinner() {}

  endRound() {}

  nextRound() {}
}

export default new GameEventActionCreator(WebsocketDatasource);
