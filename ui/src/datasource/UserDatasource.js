import BaseDatasource from './BaseDatasource';

const LOGIN_URL = '/login';
const LOAD_INFO_URL = '/auth/me';

class UserDatasource extends BaseDatasource {
  async loadUserInfo() {
    const url = this.constructUrl(LOAD_INFO_URL);
    const response = await this.client.get(url);
    return response.data;
  }

  login(username, password) {
    const url = this.constructUrl(LOGIN_URL);
    return this.client.post(url, { username, password });
  }
}

export default new UserDatasource();
