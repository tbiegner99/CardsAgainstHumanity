import React from 'react';
import { PrimaryButton } from '../../../components/formelements/buttons/Buttons';
import styles from './newGame.css';
import PlayerDisplay from '../../../components/PlayerDisplay';

const renderCode = (props) => {
  const { gameId, onStartGame, gameCode, players } = props;
  return (
    <div>
      <h3>Joined Game: {gameId}</h3>
      <h3>Game Code: {gameCode}</h3>
      <h3>Waiting for players to join...</h3>
      <section className={styles.playerPane}>
        <PlayerDisplay players={players} />
      </section>
      <div className={styles.startButtonRow}>
        <PrimaryButton onClick={() => onStartGame(gameId)}>Start Game</PrimaryButton>
      </div>
    </div>
  );
};

const renderCreatedGame = (props) => (
  <PrimaryButton onClick={props.onCreateGame}>Click to Create New Game</PrimaryButton>
);

const NewGame = (props) => (
  <div className={styles.createGame}>
    <h3>Create a Game</h3>
    {props.gameId ? renderCode(props) : renderCreatedGame(props)}
  </div>
);

export default NewGame;
