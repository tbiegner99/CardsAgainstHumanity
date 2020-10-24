import axios from 'axios';

const BASE_URL = '/cah';

class BaseDatasource {
  constructor(config) {
    this._client = axios.create(config);
  }

  constructUrl(url) {
    return `${BASE_URL}${url}`;
  }

  get client() {
    return this._client;
  }
}

export default BaseDatasource;
