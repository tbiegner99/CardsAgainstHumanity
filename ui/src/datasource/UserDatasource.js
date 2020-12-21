import BaseDatasource from './BaseDatasource';
import DeckSerializer from './serializers/DecksSerializer';
import UserSerializer from './serializers/UserSerializer';

const LOGIN_URL = '/login';
const CREATE_USER_URL = '/players';
const LOGOUT_URL = '/logout';
const LOAD_INFO_URL = '/auth/me';
const DECKS_URL = '/deck';

class UserDatasource extends BaseDatasource {
  async loadUserInfo() {
    const url = this.constructUrl(LOAD_INFO_URL);
    const response = await this.client.get(url);
    return UserSerializer.deserializeUserData(response.data);
  }

  login(username, password) {
    const url = this.constructUrl(LOGIN_URL);
    return this.client.post(url, { username, password });
  }

  logout() {
    const url = this.constructUrl(LOGOUT_URL);
    return this.client.post(url);
  }

  async loadMyDecks() {
    const url = this.constructUrl(DECKS_URL);
    const response = await this.client.get(url);
    return response.data.map((deck) => DeckSerializer.deserializeDeckResponse(deck));
  }

  async createUser(userData) {
    const url = this.constructUrl(CREATE_USER_URL);
    const body = UserSerializer.serializeCreateUserData(userData);
    const response = await this.client.post(url, body);
    return UserSerializer.deserializeUserData(response.data);
  }
}

export default new UserDatasource();
