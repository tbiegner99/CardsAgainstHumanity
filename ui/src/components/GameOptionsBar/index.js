import React from 'react';
import Urls from '../../utils/Urls';
import Icons from '../Icons/icons';
import { matchPath } from 'react-router';
import styles from './index.css';

const OptionMenuItem = (props) => {
  const { icon, name, ...otherProps } = props;
  return (
    <section className={styles.gameOption} {...otherProps}>
      <div className={styles.icon}>{icon}</div>
      <div className={styles.name}>{name}</div>
    </section>
  );
};

const getSelectedIndexForRoute = () => {
  if (matchPath(window.location.pathname, { path: Urls.PLAY_GAME, exact: true }) !== null) {
    return 0;
  } else if (matchPath(window.location.pathname, { path: Urls.MY_HAND, exact: true }) !== null) {
    return 1;
  } else if (matchPath(window.location.pathname, { path: Urls.CZAR_ORDER, exact: true }) !== null) {
    return 2;
  } else if (matchPath(window.location.pathname, { path: Urls.SCOREBOARD, exact: true }) !== null) {
    return 3;
  }

  return 0;
};

const GameOptionsBar = (props) => {
  const changeRoute = (url) => () =>
    props.onChangeRoute(Urls.parameterize(url, { gameId: props.gameId }));
  const selectedIndex = getSelectedIndexForRoute();

  return (
    <menu className={styles.gameOptionsMenu}>
      <div className={styles.status}>
        <div className={styles.selectedStatus} style={{ left: `${selectedIndex * 25}%` }} />
      </div>
      <OptionMenuItem
        name="Round State"
        icon={Icons.CardHand}
        onClick={changeRoute(Urls.PLAY_GAME)}
      />
      <OptionMenuItem name="My Hand" icon={Icons.CardHand} onClick={changeRoute(Urls.MY_HAND)} />
      <OptionMenuItem
        name="Czar Order"
        icon={Icons.CardHand}
        onClick={changeRoute(Urls.CZAR_ORDER)}
      />
      <OptionMenuItem
        name="Scoreboard"
        icon={Icons.Scoreboard}
        onClick={changeRoute(Urls.SCOREBOARD)}
      />
    </menu>
  );
};

export default GameOptionsBar;
