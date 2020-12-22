import React from 'react';
import PlayCardPage from './playCard/PlayCardPage';
import CzarPage from './czar/CzarPage';

const renderPlayerPage = (props) => {
  const { blackCard, handCards, onCardsSelected, myPlay, canPlayCard } = props;
  return (
    <PlayCardPage
      isCzar
      blackCard={blackCard}
      handCards={handCards}
      myPlay={myPlay}
      canPlayCard={canPlayCard}
      onCardsSelected={onCardsSelected}
    />
  );
};

const renderCzarPage = (props) => (
  <CzarPage
    blackCard={props.blackCard}
    revealedPlays={props.revealedPlays}
    waitingForPlays={props.waitingForPlays}
    numberOfPlays={props.numberOfPlays}
    winner={props.winner}
    onRevealNext={props.onRevealNext}
    onChooseWinner={props.onChooseWinner}
    onEndRound={props.onEndRound}
  />
);

const renderGameContent = (props) => {
  const { isCzar, blackCard } = props;

  if (!blackCard) {
    return <h2>LOADING...</h2>;
  }
  return (
    <div data-id="gameplay-page">{isCzar ? renderCzarPage(props) : renderPlayerPage(props)}</div>
  );
};

const PlayGame = (props) => renderGameContent(props);

export default PlayGame;
