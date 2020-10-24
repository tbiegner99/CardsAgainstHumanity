import React from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';
import Home from './Home';
import JoinGame from './joinGame/ReduxJoinGame';
import PlayGame from './gameplay/ReduxPlayGame';
import NewGame from './newGame/ReduxNewGame';
import Urls from '../../utils/Urls';

class AuthenticatedPage extends React.Component {
  renderAuthenticatedRoutes(props) {
    return (
      <Switch>
        <Route path={Urls.JOIN_GAME}>
          <JoinGame {...props} />
        </Route>
        <Route path={Urls.NEW_GAME}>
          <NewGame {...props} />
        </Route>
        <Route path={Urls.PLAY_GAME}>
          <PlayGame {...props} />
        </Route>
        <Route path={Urls.DESIGN_DECK} />
        <Route path={Urls.HOME}>
          <Home {...props} />
        </Route>
        <Route path="*">
          <Redirect to={Urls.HOME} />
        </Route>
      </Switch>
    );
  }

  render() {
    const { authenticated, loaded, ...props } = this.props;
    if (!loaded) {
      return <h3>LOADING</h3>;
    }
    return (
      <Switch>
        <Route path="*">
          {authenticated ? this.renderAuthenticatedRoutes(props) : <Redirect to="/login" />}
        </Route>
      </Switch>
    );
  }
}

export default AuthenticatedPage;
