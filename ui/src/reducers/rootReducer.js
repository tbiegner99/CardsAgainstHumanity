import { combineReducers } from 'redux';
import UserStore from '../stores/UserStore';
import ApplicationStore from '../stores/ApplicationStore';
import GameStore from '../stores/GameStore';
import GameRoundStore from '../stores/GameRoundStore';

export default combineReducers({
  application: ApplicationStore.reduce,
  user: UserStore.reduce,
  game: GameStore.reduce,
  currentRound: GameRoundStore.reduce
});
