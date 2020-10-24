import { connect } from 'react-redux';
import UrlActionCreator from '../../../actionCreators/UrlActionCreator';
import GameActionCreator from '../../../actionCreators/GameEventActionCreator';
import NewGame from './NewGame';

const mapStateToProps = (state) => ({
  players: state.game.store.players.value,
  gameCode: state.game.store.gameCode.value,
  gameId: state.game.store.gameId.value
});

const mapDispatchToProps = () => ({
  onUrlChange: (url) => UrlActionCreator.changeUrl(url),
  onCreateGame: () => GameActionCreator.createGame(),
  onStartGame: (gameId) => GameActionCreator.startGame(gameId)
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NewGame);
