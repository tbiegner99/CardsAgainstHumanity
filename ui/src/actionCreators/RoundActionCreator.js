import BaseActionCreator from './BaseActionCreator';
import GameCommands from '../gameSocket/GameCommands';
import WebsocketDatasource from '../datasource/WebsocketDatasource';
import GameActions from '../actions/GameActions';
import Commands from '../utils/Commands';

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

  async makePlay(roundId, cardsToPlay) {
    const commandResponse = await this.websocketDatasource.sendCommand(GameCommands.PLAY_CARD, {
      roundId,
      cardsToPlay
    });

    this.dispatch({
      type: GameActions.GAME_STATUS,
      data: commandResponse
    });
  }

  async revealPlay(roundId, cardsToPlay) {
    const commandResponse = await this.websocketDatasource.sendCommand(GameCommands.REVEAL_PLAY, {
      roundId,
      cardsToPlay
    });

    this.dispatch({
      type: GameActions.GAME_STATUS,
      data: commandResponse
    });
  }

  async declareWinner(play) {
    const commandResponse = await this.websocketDatasource.sendCommand(
      GameCommands.CHOOSE_WINNER,
      play
    );

    this.dispatch({
      type: GameActions.GAME_STATUS,
      data: commandResponse
    });
  }

  async endRound() {
    const commandResponse = await this.websocketDatasource.sendCommand(GameCommands.END_ROUND);

    this.dispatch({
      type: GameActions.GAME_STATUS,
      data: commandResponse
    });
  }

  nextRound() {}
}

export default new GameEventActionCreator(WebsocketDatasource);
