import React from 'react';
import { BrowserRouter as Router, Route, Switch, withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import Redirect from '../components/Redirect';
import UrlActionCreator from '../actionCreators/UrlActionCreator';
import NavBar from './navigation/navs/NavBar';
import LoginPage from './login/ReduxLoginPage';
import ReduxLogoutPage from './logout/ReduxLogoutPage';
import AuthenticatedPage from './authenticated/ReduxAuthenticatedPage';
import AudiencePage from './audience/ReduxAudiencePage';
import AudienceJoinPage from './audience/JoinGameScreen';
import AudienceGameScreen from './audience/gameView/ReduxGameScreen';
import Urls from '../utils/Urls';
import styles from './main.css';
import ReduxRegistrationPage from './registration/ReduxRegistrationPage';

class Routing extends React.Component {
  componentDidUpdate(prevProps) {
    const { history, currentUrl } = this.props;
    if (prevProps.currentUrl !== currentUrl) {
      history.push(currentUrl);
    }
  }

  render() {
    const { authenticated, onChangeUrl } = this.props;
    return (
      <main className={styles.mainPage}>
        <NavBar
          authenticated={authenticated}
          onBrandClick={() => onChangeUrl(Urls.HOME)}
          onHamburgerClick={() => this.openSideMenu()}
        />
        <div className={styles.mainContent}>
          <Switch>
            <Route path={Urls.LOGOUT} component={ReduxLogoutPage} />
            <Route path={Urls.LOGIN} component={LoginPage} />
            <Route path={Urls.REGISTER} component={ReduxRegistrationPage} />
            <Route exact path={Urls.Audience.HOME} component={AudiencePage} />
            <Route exact path={Urls.Audience.JOIN} component={AudienceJoinPage} />
            <Route exact path={Urls.Audience.GAME} component={AudienceGameScreen} />
            <Route path={Urls.AUTHENTICATED} component={AuthenticatedPage} />
            <Route path="*">
              <Redirect to={Urls.LOGIN} />
            </Route>
          </Switch>
        </div>
      </main>
    );
  }
}

const Navigation = withRouter(Routing);

const mapStateToProps = (state) => ({
  currentUrl: state.application.store.currentUrl,
  authenticated: state.user.store.authenticated.value,
  onChangeUrl: (url) => UrlActionCreator.changeUrl(url)
});

const ConnectedNavigation = connect(mapStateToProps)(Navigation);

export default () => (
  <Router>
    <ConnectedNavigation />
  </Router>
);
