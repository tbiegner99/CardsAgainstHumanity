import BaseActionCreator from './BaseActionCreator';
import UserDatasource from '../datasource/UserDatasource';
import UserActions from '../actions/UserActions';
import WebsocketDatasource from '../datasource/WebsocketDatasource';
import Urls from '../utils/Urls';

const POST_LOGIN_URL = '/auth/home';

class UserActionCreator extends BaseActionCreator {
  constructor(websocketDatasource) {
    super();
    this.websocketDatasource = websocketDatasource;
  }

  async loadUserInfo() {
    try {
      const userInfo = await UserDatasource.loadUserInfo();
      this.dispatch({
        type: UserActions.USER_INFO_LOADED,
        data: userInfo
      });
      await this.websocketDatasource.ensureConnected();
    } catch (err) {
      this.checkUnauthenticated(err.response);
    }
  }

  async loadDecksForUser() {
    try {
      const decks = await UserDatasource.loadMyDecks();
      this.dispatch({
        type: UserActions.USER_DECKS_LOADED,
        data: decks
      });
    } catch (err) {
      this.dispatch({
        type: UserActions.Errors.LOAD_DECK_FAILURE
      });
    }
  }

  async login(username, password, redirectLocation) {
    try {
      await UserDatasource.login(username, password);
      const loginEvent = {
        type: UserActions.LOGIN_SUCCESS
      };
      this.dispatch(loginEvent);
      this.changeUrl(redirectLocation || POST_LOGIN_URL);
      await this.websocketDatasource.ensureConnected();
    } catch (err) {
      this.dispatch({
        type: UserActions.Errors.LOGIN_FAILURE
      });
      throw err;
    }
  }

  async createUser(data) {
    try {
      const userData = await UserDatasource.createUser(data);
      this.dispatch({
        type: UserActions.USER_CREATED,
        data: userData
      });
      this.changeUrl(Urls.HOME);
      await this.websocketDatasource.ensureConnected();
    } catch (err) {
      this.dispatch({
        type: UserActions.Errors.USER_CREATE_FAILURE
      });
      throw err;
    }
  }

  async logout() {
    try {
      await UserDatasource.logout();
      const logoutEvent = {
        type: UserActions.LOGOUT_SUCCESS
      };
      this.dispatch(logoutEvent);
      this.changeUrl(Urls.LOGIN);
    } catch (err) {
      this.dispatch({
        type: UserActions.Errors.LOGOUT_FAILURE
      });
      throw err;
    }
  }
}

export default new UserActionCreator(WebsocketDatasource);
