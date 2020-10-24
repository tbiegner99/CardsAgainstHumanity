import { connect } from 'react-redux';
import GameActionCreator from '../../../actionCreators/GameEventActionCreator';
import PlayGame from './PlayGame';
import GameStates from '../../../utils/GameStates';

const mapStateToProps = (state) => {
  const gameStore = state.game.store;
  const roundStore = state.currentRound.store;

  return {
    handCards: gameStore.handCards.value,
    blackCard: roundStore.blackCard.value,
    isCzar: false
  };
};

const mapDispatchToProps = () => ({});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PlayGame);
