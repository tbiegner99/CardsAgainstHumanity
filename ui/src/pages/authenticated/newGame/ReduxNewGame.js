import { connect } from 'react-redux';
import UrlActionCreator from '../../../actionCreators/UrlActionCreator';
import GameActionCreator from '../../../actionCreators/GameEventActionCreator';
import NewGame from './NewGame';

const mapStateToProps = (state) => {
  const gameStore = state.game.store;
  const userStore = state.user.store;
  return {
    players: gameStore.players.value,
    gameCode: gameStore.gameCode.value,
    gameId: gameStore.gameId.value,
    decks: userStore.decks.value,
    gameDeck: gameStore.deck.value
  };
};

const mapDispatchToProps = () => ({
  onUrlChange: (url) => UrlActionCreator.changeUrl(url),
  onCreateGame: (deckId) => {
    GameActionCreator.createGame(deckId);
  },
  onStartGame: (gameId) => GameActionCreator.startGame(gameId)
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NewGame);
