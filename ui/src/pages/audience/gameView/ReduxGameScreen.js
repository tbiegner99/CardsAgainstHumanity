import React from 'react';
import { connect } from 'react-redux';
import { orderByCzarStartingWithCurrent } from '../../../utils/SortFunctions';
import GameActionCreator from '../../../actionCreators/GameEventActionCreator';
import GameScreen from './GameScreen';

class LoadGameComponent extends React.Component {
  reloadGameIfNecessary() {
    const { match, gameCode } = this.props;
    const urlGameCode = match.params.gameId;

    if (urlGameCode !== gameCode) {
      GameActionCreator.joinGame(urlGameCode);
    }
  }

  componentDidMount() {
    this.reloadGameIfNecessary();
  }

  componentDidUpdate() {
    this.reloadGameIfNecessary();
  }

  render() {
    return <GameScreen {...this.props} />;
  }
}

const mapStateToProps = (state) => {
  const gameStore = state.game.store;
  const roundStore = state.currentRound.store;
  const players = gameStore.players.value;
  const czarOrder = orderByCzarStartingWithCurrent(players);
  return {
    czarOrder,
    blackCard: roundStore.blackCard.value,
    gameCode: gameStore.gameCode.value,
    waitingForPlays: roundStore.waitingForPlays.value,
    revealedPlays: roundStore.revealedPlays.value,
    numberOfPlays: roundStore.numberOfPlays.value,
    winner: roundStore.winner.value
  };
};

const mapDispatchToProps = () => ({});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LoadGameComponent);
