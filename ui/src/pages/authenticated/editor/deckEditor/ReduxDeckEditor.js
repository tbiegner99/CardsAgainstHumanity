import { connect } from 'react-redux';
import DeckActionCreator from '../../../../actionCreators/DeckActionCreator';
import UrlActionCreator from '../../../../actionCreators/UrlActionCreator';
import Urls from '../../../../utils/Urls';
import DeckEditor from './DeckEditor';

const mapStateToProps = (state) => {
  const decks = state.decks.store.decks.value;
  return {
    decks
  };
};

const mapDispatchToProps = () => ({
  onUrlChange: (url) => UrlActionCreator.changeUrl(url),
  onCreateDeck: (data) => DeckActionCreator.createDeck(data),
  onDeckDelete: (deck) => DeckActionCreator.deleteDeck(deck.deckId),
  onDeckEdit: (deck) =>
    UrlActionCreator.changeUrl(Urls.parameterize(Urls.EDIT_DECK, { deckId: deck.deckId }))
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DeckEditor);
