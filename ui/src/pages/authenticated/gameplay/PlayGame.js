import React from 'react';
import { Switch, Route } from 'react-router-dom';
import Redirect from '../../../components/Redirect';
import RoundPlay from './roundPlay/ReduxRoundPlay';
import Urls from '../../../utils/Urls';
import Scoreboard from './scoreboard/Scoreboard';
import GameOptionsBar from '../../../components/GameOptionsBar';
import CzarOrder from './czarOrder/CzarOrder';
import styles from './main.css';
import MyHand from './myHand/MyHand';

const MainGame = (props) => {
  const passPropsTo = (Component) => (passedProps) => <Component {...props} {...passedProps} />;
  return (
    <main className={styles.mainPage}>
      <div className={styles.mainContent}>
        <Switch>
          <Route path={Urls.SCOREBOARD} render={passPropsTo(Scoreboard)} />
          <Route path={Urls.MY_HAND} render={passPropsTo(MyHand)} />
          <Route path={Urls.CZAR_ORDER} render={passPropsTo(CzarOrder)} />
          <Route path={Urls.PLAY_GAME} render={passPropsTo(RoundPlay)} />
          <Route path="*">
            <Redirect to={Urls.HOME} />
          </Route>
        </Switch>
      </div>
      <GameOptionsBar {...props} />
    </main>
  );
};

export default MainGame;
