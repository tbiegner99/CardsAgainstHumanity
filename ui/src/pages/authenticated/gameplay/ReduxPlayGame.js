import { connect } from 'react-redux';
import GameActionCreator from '../../../actionCreators/GameEventActionCreator';
import PlayGame from './PlayGame';

const mapStateToProps = (state) => {
  const gameStore = state.game.store;
  const roundStore = state.currentRound.store;

  return {
    isCzar: roundStore.isCzar.value,
    gameId: gameStore.gameId.value,
    onChangeRoute: (url) => GameActionCreator.changeUrl(url)
  };
};

const mapDispatchToProps = () => ({});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PlayGame);
