import React from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';
import Home from './Home';
import JoinGame from './joinGame/ReduxJoinGame';
import PlayGame from './gameplay/ReduxPlayGame';
import NewGame from './newGame/ReduxNewGame';
import Urls from '../../utils/Urls';
import EditorHome from './editor/EditorHome';
import ReduxPackageEditor from './editor/cardEditor/packageEditor/ReduxPackageEditor';
import ReduxCardEditor from './editor/cardEditor/cardCreator/ReduxCardEditor';
import ReduxDeckEditor from './editor/deckEditor/ReduxDeckEditor';
import ReduxDeckCardEditor from './editor/deckEditor/cardEditor/ReduxDeckCardEditor';

class AuthenticatedPage extends React.Component {
  renderAuthenticatedRoutes(passedProps) {
    const passPropsTo = (Component) => (props) => <Component {...passedProps} {...props} />;
    return (
      <Switch>
        <Route path={Urls.JOIN_GAME} render={passPropsTo(JoinGame)} />
        <Route path={Urls.NEW_GAME} render={passPropsTo(NewGame)} />
        <Route path={Urls.PLAY_GAME} render={passPropsTo(PlayGame)} />
        <Route path={Urls.DESIGN} exact render={passPropsTo(EditorHome)} />
        <Route path={Urls.DESIGN_PACKAGES} exact render={passPropsTo(ReduxPackageEditor)} />
        <Route path={Urls.DESIGN_DECK} exact render={passPropsTo(ReduxDeckEditor)} />
        <Route path={Urls.EDIT_PACKAGE} exact render={passPropsTo(ReduxCardEditor)} />
        <Route path={Urls.EDIT_DECK} exact render={passPropsTo(ReduxDeckCardEditor)} />
        <Route path={Urls.HOME} render={passPropsTo(Home)} />
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
    const redirectLocation = window.location.pathname;
    return (
      <Switch>
        <Route path="*">
          {authenticated ? (
            this.renderAuthenticatedRoutes(props)
          ) : (
            <Redirect to={`/login?redirectUrl=${redirectLocation}`} />
          )}
        </Route>
      </Switch>
    );
  }
}

export default AuthenticatedPage;
