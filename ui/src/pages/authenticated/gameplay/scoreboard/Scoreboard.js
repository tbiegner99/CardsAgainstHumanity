import React from 'react';
import { connect } from 'react-redux';
import GameActionCreator from '../../../../actionCreators/GameEventActionCreator';

import PlayerBoard, { SortDirection } from '../../../../components/playerBoard/PlayerBoard';

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
    return <PlayerBoard {...this.props} />;
  }
}

const mapStateToProps = (state) => {
  const gameStore = state.game.store;
  const playerData = gameStore.players.value;
  return {
    sortDirection: SortDirection.DESCENDING,
    sortField: 'score',
    title: 'Scoreboard',
    playerData
  };
};

const mapDispatchToProps = () => ({});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LoadGameComponent);
