import React from 'react';
import { connect } from 'react-redux';
import GameActionCreator from '../../../../actionCreators/GameEventActionCreator';
import { orderByCzarStartingWithCurrent } from '../../../../utils/SortFunctions';
import PlayerBoard, {
  DataFields,
  DataHeaders
} from '../../../../components/playerBoard/PlayerBoard';

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
    sortField: null,
    header: DataHeaders.CZAR_ORDER,
    dataField: DataFields.CZAR_INDEX,
    title: 'Czar Order',
    playerData: orderByCzarStartingWithCurrent(playerData)
  };
};

const mapDispatchToProps = () => ({});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LoadGameComponent);
