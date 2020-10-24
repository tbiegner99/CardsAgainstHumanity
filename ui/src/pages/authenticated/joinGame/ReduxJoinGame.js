import { connect } from 'react-redux';
import GameActionCreator from '../../../actionCreators/GameEventActionCreator';
import JoinGame from './JoinGame';
import GameStates from '../../../utils/GameStates';

const mapStateToProps = (state) => {
  const gameStore = state.game.store;
  return {
    players: gameStore.players.value,
    gameStarted: gameStore.gameState.value === GameStates.GAME_STARTED,
    gameId: gameStore.gameId.value
  };
};

const mapDispatchToProps = () => ({
  onJoinGame: ({ code }) => GameActionCreator.joinGame(code)
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(JoinGame);
