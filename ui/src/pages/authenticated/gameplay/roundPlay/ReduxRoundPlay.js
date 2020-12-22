import React from 'react';
import { connect } from 'react-redux';
import GameActionCreator from '../../../../actionCreators/GameEventActionCreator';
import RoundActionCreator from '../../../../actionCreators/RoundActionCreator';
import PlayGame from './RoundPlay';

class LoadGameComponent extends React.Component {
  reloadGameIfNecessary() {
    const { match, gameId } = this.props;
    const urlGameId = parseInt(match.params.gameId, 10);

    if (urlGameId !== gameId) {
      GameActionCreator.loadGame(urlGameId);
    }
  }

  componentDidMount() {
    this.reloadGameIfNecessary();
  }

  componentDidUpdate() {
    this.reloadGameIfNecessary();
  }

  render() {
    return <PlayGame {...this.props} />;
  }
}

const mapStateToProps = (state) => {
  const gameStore = state.game.store;
  const roundStore = state.currentRound.store;
  const roundId = roundStore.roundId.value;

  return {
    handCards: gameStore.handCards.value,
    blackCard: roundStore.blackCard.value,
    gameId: gameStore.gameId.value,
    isCzar: roundStore.isCzar.value,
    waitingForPlays: roundStore.waitingForPlays.value,
    revealedPlays: roundStore.revealedPlays.value,
    numberOfPlays: roundStore.numberOfPlays.value,
    canPlayCard: roundStore.canPlayCard.value,
    myPlay: roundStore.myPlay.value,
    winner: roundStore.winner.value,
    onChangeUrl: (url) => RoundActionCreator.changeUrl(url),
    onEndRound: () => RoundActionCreator.endRound(),
    onRevealNext: () => RoundActionCreator.revealPlay(),
    onChooseWinner: (play) => RoundActionCreator.declareWinner(play),
    onCardsSelected: (cardsSelected) => {
      RoundActionCreator.makePlay(roundId, cardsSelected);
    }
  };
};

const mapDispatchToProps = () => ({});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LoadGameComponent);
