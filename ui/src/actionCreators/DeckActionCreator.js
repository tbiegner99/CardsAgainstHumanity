import autoBind from 'auto-bind-inheritance';
import BaseActionCreator from './BaseActionCreator';
import DeckDatasource from '../datasource/DeckDatasource';
import DeckActions from '../actions/DeckActions';

class DeckActionCreator extends BaseActionCreator {
  constructor(datasource) {
    super();
    this.datasource = datasource;
    autoBind(this);
  }

  async loadDeckDetails(deckId) {
    try {
      const decks = await this.datasource.loadDeckDetails(deckId);
      this.dispatch({
        type: DeckActions.DECK_DETAILS_LOADED,
        data: decks
      });
    } catch (err) {
      this.dispatch({
        type: DeckActions.Errors.LOAD_DECK_DETAILS_FAILURE
      });
      console.error(err);
    }
  }

  async loadEditableDecks() {
    try {
      const decks = await this.datasource.loadEditableDecks();
      this.dispatch({
        type: DeckActions.DECKS_LOADED,
        data: decks
      });
    } catch (err) {
      this.dispatch({
        type: DeckActions.Errors.LOAD_DECK_FAILURE
      });
      console.error(err);
    }
  }

  async createDeck(data) {
    try {
      const packageData = await this.datasource.createDeck(data);
      this.dispatch({
        type: DeckActions.DECK_CREATED,
        data: packageData
      });
      await this.loadEditableDecks();
    } catch (err) {
      this.dispatch({
        type: DeckActions.Errors.DECK_CREATE_FAILURE
      });
      console.error(err);
    }
  }

  async deleteDeck(deckId) {
    try {
      await this.datasource.deleteDeck(deckId);
      this.dispatch({
        type: DeckActions.DECK_DELETED,
        data: deckId
      });
      await this.loadEditableDecks();
    } catch (err) {
      this.dispatch({
        type: DeckActions.Errors.DECK_REMOVE_FAILURE
      });
      console.error(err);
    }
  }

  async removePackageFromDeck(deckId, packageId) {
    try {
      await this.datasource.removePackageFromDeck(deckId, packageId);
      this.dispatch({
        type: DeckActions.DECK_PACKAGE_REMOVED,
        data: packageId
      });
      await this.loadDeckDetails(deckId);
    } catch (err) {
      this.dispatch({
        type: DeckActions.Errors.DECK_REMOVE_PACKAGE_FAILURE
      });
    }
  }

  async addPackage(deckId, packageId) {
    try {
      const cardData = await this.datasource.addPackageToDeck(deckId, packageId);
      this.dispatch({
        type: DeckActions.DECK_PACKAGE_ADDED,
        data: cardData
      });
      await this.loadDeckDetails(deckId);
    } catch (err) {
      this.dispatch({
        type: DeckActions.Errors.DECK_ADD_PACKAGE_FAILURE
      });
      throw err;
    }
  }

  async addCard(deckId, packageId, cardType, cardId) {
    try {
      const cardData = await this.datasource.addCardToDeck(deckId, packageId, cardType, cardId);
      this.dispatch({
        type: DeckActions.DECK_CARD_ADDED,
        data: cardData
      });
      await this.loadDeckDetails(deckId);
    } catch (err) {
      this.dispatch({
        type: DeckActions.Errors.DECK_ADD_CARD_FAILURE
      });
      throw err;
    }
  }

  async removeCard(deckId, cardType, cardId) {
    try {
      await this.datasource.removeCardFromDeck(deckId, cardType, cardId);
      this.dispatch({
        type: DeckActions.DECK_CARD_REMOVED,
        data: cardId
      });
      await this.loadDeckDetails(deckId);
    } catch (err) {
      this.dispatch({
        type: DeckActions.Errors.DECK_REMOVE_CARD_FAILURE
      });
      throw err;
    }
  }
}

export default new DeckActionCreator(DeckDatasource);
