import { combineReducers } from 'redux';
import UserStore from '../stores/UserStore';
import ApplicationStore from '../stores/ApplicationStore';
import GameStore from '../stores/GameStore';
import DeckStore from '../stores/DeckStore';
import GameRoundStore from '../stores/GameRoundStore';
import PackageCardStore from '../stores/PackageCardStore';

export default combineReducers({
  application: ApplicationStore.reduce,
  user: UserStore.reduce,
  game: GameStore.reduce,
  decks: DeckStore.reduce,
  currentRound: GameRoundStore.reduce,
  packages: PackageCardStore.reduce
});
