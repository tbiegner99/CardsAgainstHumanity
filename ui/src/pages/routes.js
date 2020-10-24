import React from 'react';
import { BrowserRouter as Router, Route, Switch, Redirect, withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import NavBar from './navigation/navs/NavBar';
import LoginPage from './login/ReduxLoginPage';
import AuthenticatedPage from './authenticated/ReduxAuthenticatedPage';
import AudiencePage from './audience/AudiencePage';
import AudienceJoinPage from './audience/JoinGameScreen';
import Urls from '../utils/Urls';
import styles from './main.css';

class Routing extends React.Component {
  componentDidUpdate(prevProps) {
    const { history, currentUrl } = this.props;
    if (prevProps.currentUrl !== currentUrl) {
      history.push(currentUrl);
    }
  }

  render() {
    const { authenticated } = this.props;
    return (
      <main className={styles.mainPage}>
        <NavBar authenticated={authenticated} onHamburgerClick={() => this.openSideMenu()} />
        <div className={styles.mainContent}>
          <Switch>
            <Route path={Urls.LOGOUT} />
            <Route path={Urls.LOGIN}>
              <LoginPage />
            </Route>
            <Route exact path={Urls.Audience.HOME}>
              <AudiencePage />
            </Route>
            <Route exact path={Urls.Audience.JOIN}>
              <AudienceJoinPage />
            </Route>
            <Route path="/auth">
              <AuthenticatedPage />
            </Route>
            <Route path="/">
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
  authenticated: state.user.store.authenticated.value
});

const ConnectedNavigation = connect(mapStateToProps)(Navigation);

export default () => (
  <Router>
    <ConnectedNavigation />
  </Router>
);
