import AbstractReducingStore from './AbstractReducingStore';
import StoreField from './StoreField';
import GameActions from '../actions/GameActions';
import GameStates from '../utils/GameStates';

class GameStore extends AbstractReducingStore {
  constructor() {
    super();
    this.data = {
      gameId: new StoreField('gameId', null),
      gameCode: new StoreField('gameCode', null),
      gameState: new StoreField('gameState', null),
      players: new StoreField('players', []),
      handCards: new StoreField('handCards', null)
    };
  }

  get gameCode() {
    return this.data.gameCode;
  }

  get gameId() {
    return this.data.gameId;
  }

  get gameState() {
    return this.data.gameState;
  }

  get players() {
    return this.data.players;
  }

  get handCards() {
    return this.data.handCards;
  }

  handleEvent(action) {
    switch (action.type) {
      case GameActions.GAME_CREATED:
        this.data.gameCode.value = action.data.code;
        this.data.gameId.value = action.data.gameId;
        this.data.gameState.value = action.data.state;
        this.data.players.value = action.data.players;
        this.data.handCards.value = action.data.currentHand;
        break;
      case GameActions.GAME_STATUS:
        this.data.gameId.value = action.data.gameId;
        this.data.gameState.value = action.data.state;
        this.data.handCards.value = action.data.currentHand;
        if (action.data.players) {
          this.data.players.value = action.data.players;
        }
        break;
      case GameActions.GAME_STARTED:
        this.data.gameState.value = GameStates.GAME_STARTED;
        break;
      default:
        return false;
    }
    return true;
  }
}

export default new GameStore();
