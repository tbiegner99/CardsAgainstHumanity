import AbstractReducingStore from './AbstractReducingStore';
import StoreField from './StoreField';
import UserActions from '../actions/UserActions';
import UserActionCreator from '../actionCreators/UserActionCreator';
import autoBind from 'auto-bind-inheritance';

class UserStore extends AbstractReducingStore {
  constructor() {
    super();
    this.data = {
      userData: new StoreField('userData', null, this.loadUserData.bind(this)),
      decks: new StoreField('decks', null, this.loadUserDecks.bind(this)),
      authenticated: new StoreField('authenticated', false, this.checkAuthenticated.bind(this))
    };
  }

  get userData() {
    return this.data.userData;
  }

  get authenticated() {
    return this.data.authenticated;
  }

  get decks() {
    return this.data.decks;
  }

  loadUserDecks() {
    return UserActionCreator.loadDecksForUser();
  }

  loadUserData() {
    return UserActionCreator.loadUserInfo();
  }

  checkAuthenticated() {
    this.data.userData.loadValue();
  }

  handleEvent(action) {
    switch (action.type) {
      case UserActions.Errors.UNAUTHENTICATED:
        this.data.authenticated.value = false;
        break;
      case UserActions.USER_CREATED:
      case UserActions.USER_INFO_LOADED:
        this.data.userData.value = action.data;
        this.data.authenticated.value = true;
        break;
      case UserActions.LOGOUT_SUCCESS:
        this.data.userData.value = null;
        this.data.decks = null;
        this.data.authenticated = false;
        break;
      case UserActions.LOGIN_SUCCESS:
        this.data.userData.loadValue();
        this.data.authenticated.value = true;
        break;
      case UserActions.USER_DECKS_LOADED:
        this.data.decks.value = action.data;
        break;
      default:
        return false;
    }
    return true;
  }
}

export default new UserStore();
