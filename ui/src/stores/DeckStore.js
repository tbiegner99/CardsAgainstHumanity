import AbstractReducingStore from './AbstractReducingStore';
import StoreField from './StoreField';
import DeckActions from '../actions/DeckActions';
import DeckActionCreator from '../actionCreators/DeckActionCreator';
import PackageActionCreator from '../actionCreators/PackagesActionCreator';
import PackageActions from '../actions/PackageActions';

class DeckStore extends AbstractReducingStore {
  constructor() {
    super();
    this.data = {
      deckDetails: {},
      packages: new StoreField('packages', null, PackageActionCreator.loadUseablePackages),
      decks: new StoreField('decks', null, DeckActionCreator.loadEditableDecks)
    };
  }

  getDeckDetails(deckId) {
    if (!this.data.deckDetails[deckId]) {
      this.data.deckDetails[deckId] = new StoreField(
        deckId,
        null,
        DeckActionCreator.loadDeckDetails.bind(DeckActionCreator, deckId)
      );
    }
    return this.data.deckDetails[deckId].value;
  }

  setDeckDetails(deckId, details) {
    this.data.deckDetails[deckId].value = details;
  }

  get packages() {
    return this.data.packages;
  }

  get decks() {
    return this.data.decks;
  }

  updateDeckInfo(deckId, deckInfo) {
    if (this.decks.hasValue) {
      const object = this.decks.value.find((obj) => obj.deckId === deckId);
      const updateObject = {
        whiteCardCount: deckInfo.whiteCardCount,
        blackCardCount: deckInfo.blackCardCount
      };
      Object.assign(object, updateObject);
    }
  }

  handleEvent(action) {
    const { type, data } = action;
    switch (type) {
      case PackageActions.DECK_PACKAGES_LOADED:
        this.data.packages.value = action.data;
        break;
      case DeckActions.DECK_DETAILS_LOADED:
        this.setDeckDetails(data.deckId, data);
        this.updateDeckInfo(data.deckId, data);
        break;
      case DeckActions.DECKS_LOADED:
        this.data.decks.value = action.data;
        break;
      default:
        return false;
    }
    return true;
  }
}

export default new DeckStore();
