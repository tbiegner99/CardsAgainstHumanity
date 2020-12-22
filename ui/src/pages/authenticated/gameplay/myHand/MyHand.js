import React from 'react';
import { connect } from 'react-redux';
import GameActionCreator from '../../../../actionCreators/GameEventActionCreator';
import HandView from './HandView';

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
    return <HandView {...this.props} />;
  }
}

const mapStateToProps = (state) => {
  const gameStore = state.game.store;
  const roundStore = state.currentRound.store;

  return {
    handCards: gameStore.handCards.value,
    blackCard: roundStore.blackCard.value,
    gameId: gameStore.gameId.value,
    onChangeRoute: (url) => GameActionCreator.changeUrl(url)
  };
};

const mapDispatchToProps = () => ({});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LoadGameComponent);
