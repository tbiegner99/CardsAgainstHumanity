import AbstractReducingStore from './AbstractReducingStore';
import UrlActions from '../actions/UrlActions';

class ApplicationStore extends AbstractReducingStore {
  constructor() {
    super();
    this.data = {
      currentUrl: window.location.pathname
    };
  }

  get currentUrl() {
    return this.data.currentUrl;
  }

  handleEvent(action) {
    switch (action.type) {
      case UrlActions.CHANGE_URL:
        this.data.currentUrl = action.data.url;
        break;
      default:
        return false;
    }
    return true;
  }
}

export default new ApplicationStore();
