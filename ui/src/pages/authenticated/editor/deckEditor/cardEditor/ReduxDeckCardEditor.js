import { connect } from 'react-redux';
import DeckActionCreator from '../../../../../actionCreators/DeckActionCreator';
import PackageActionCreator from '../../../../../actionCreators/PackagesActionCreator';
import UrlActionCreator from '../../../../../actionCreators/UrlActionCreator';
import DeckCardEditor from './DeckCardEditor';

const mapStateToProps = (state, ownProps) => {
  const { deckId } = ownProps.match.params;
  const deck = state.decks.store.getDeckDetails(deckId);
  const packages = state.decks.store.packages.value;
  const packageDetails = state.packages.store.getLoadedPackageDetails();
  return {
    deckId,
    deck,
    packages,
    packageDetails
  };
};

const mapDispatchToProps = () => ({
  onUrlChange: (url) => UrlActionCreator.changeUrl(url),
  onRemoveCard: (deckId, cardType, cardId) =>
    DeckActionCreator.removeCard(deckId, cardType, cardId),
  onAddCard: (deckId, packageId, type, cardId) =>
    DeckActionCreator.addCard(deckId, packageId, type, cardId),
  onAddPackage: (deckId, pack) => DeckActionCreator.addPackage(deckId, pack.id),
  onRemovePackage: (deckId, pack) => DeckActionCreator.removePackageFromDeck(deckId, pack.id),
  onLoadPackageDetails: (pack) => PackageActionCreator.loadPackageDetails(pack.id)
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DeckCardEditor);
