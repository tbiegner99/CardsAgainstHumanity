import DispatcherFactory from '../dispatcher/DispatcherFactory';
import UserActions from '../actions/UserActions';
import UrlActions from '../actions/UrlActions';

const UNAUTHENTICATED = 401;

class BaseActionCreator {
  dispatch(action) {
    DispatcherFactory.dispatch(action);
  }

  checkUnauthenticated(response) {
    if (response && response.status === UNAUTHENTICATED) {
      this.dispatch({
        type: UserActions.Errors.UNAUTHENTICATED
      });
    }
  }

  changeUrl(redirectLocation) {
    this.dispatch({
      type: UrlActions.CHANGE_URL,
      data: {
        url: redirectLocation
      }
    });
  }
}

export default BaseActionCreator;
