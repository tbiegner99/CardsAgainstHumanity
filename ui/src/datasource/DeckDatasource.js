import BaseDatasource from './BaseDatasource';
import DeckSerializer from './serializers/DecksSerializer';

const DECKS_URL = '/deck/me';
const CREATE_DECK = '/deck';
const getRemoveDeckUrl = (deckId) => `/deck/${deckId}`;
const getDeckDetailsUrl = (deckId) => `/deck/${deckId}`;
const getAddPackageUrl = (deckId) => `/deck/${deckId}/package`;
const getAddCardUrl = (deckId, cardType) => `/deck/${deckId}/${cardType}Card`;
const getRemovePackageUrl = (deckId, packageId) => `${getAddPackageUrl(deckId)}/${packageId}`;
const getRemoveCardUrl = (deckId, cardType, cardId) => `/deck/${deckId}/${cardType}Card/${cardId}`;

class PackageDatasource extends BaseDatasource {
  async loadDeckDetails(deckId) {
    const url = this.constructUrl(getDeckDetailsUrl(deckId));
    const response = await this.client.get(url);
    return DeckSerializer.deserializeDeckDetailsResponse(response.data);
  }

  async loadEditableDecks() {
    const url = this.constructUrl(DECKS_URL);
    const response = await this.client.get(url);
    return response.data.map((deck) => DeckSerializer.deserializeDeckResponse(deck));
  }

  async createDeck(data) {
    const url = this.constructUrl(CREATE_DECK);
    const body = DeckSerializer.serializeCreatePackageData(data);
    const response = await this.client.post(url, body);
    return DeckSerializer.deserializeDeckResponse(response.data);
  }

  async deleteDeck(deckId) {
    const url = this.constructUrl(getRemoveDeckUrl(deckId));
    await this.client.delete(url);
  }

  async addPackageToDeck(deckId, packageId) {
    const url = this.constructUrl(getAddPackageUrl(deckId));
    const body = { packageId };
    await this.client.post(url, body);
  }

  async addCardToDeck(deckId, packageId, cardType, cardId) {
    const url = this.constructUrl(getAddCardUrl(deckId, cardType, cardId));
    const body = { packageId, cardId };
    await this.client.post(url, body);
  }

  async removePackageFromDeck(deckId, packageId) {
    const url = this.constructUrl(getRemovePackageUrl(deckId, packageId));
    await this.client.delete(url);
  }

  async removeCardFromDeck(deckId, cardType, cardId) {
    const url = this.constructUrl(getRemoveCardUrl(deckId, cardType, cardId));
    await this.client.delete(url);
  }
}

export default new PackageDatasource();
