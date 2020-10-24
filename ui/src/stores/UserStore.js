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
      authenticated: new StoreField('authenticated', false, this.checkAuthenticated.bind(this))
    };
  }

  get userData() {
    return this.data.userData;
  }

  get authenticated() {
    return this.data.authenticated;
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
      case UserActions.USER_INFO_LOADED:
        this.data.userData.value = action.data;
        this.data.authenticated.value = true;
        break;
      case UserActions.LOGIN_SUCCESS:
        this.data.userData.loadValue();
        this.data.authenticated.value = true;
        break;
      default:
        return false;
    }
    return true;
  }
}

export default new UserStore();
