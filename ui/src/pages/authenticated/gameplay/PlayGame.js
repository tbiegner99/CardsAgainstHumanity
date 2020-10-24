import React from 'react';
import GameOptionsBar from '../../../components/GameOptionsBar';
import PlayCardPage from './player/playCard/PlayCardPage';
import CzarPage from './czar/CzarPage';
import styles from './main.css';

const renderPlayerPage = (props) => {
  const { blackCard, handCards } = props;
  return <PlayCardPage blackCard={blackCard} handCards={handCards} />;
};

const renderCzarPage = (props) => <CzarPage />;

const renderGameContent = (props) => {
  const { isCzar } = props;
  if (isCzar) {
    return renderCzarPage(props);
  }
  return renderPlayerPage(props);
};

const PlayGame = (props) => (
  <main className={styles.mainPage}>
    <div className={styles.mainContent}>{renderGameContent(props)}</div>
    <GameOptionsBar isCzar={props.isCzar} />
  </main>
);

export default PlayGame;
