import React from 'react';
import combineClasses from 'classnames';
import styles from './styles.css';

const HeaderRow = (props) => (
  <div className={combineClasses(styles.row, styles.headerRow)}>
    Players ({props.players.length})
  </div>
);

const PlayerRow = (props) => {
  const { player } = props;
  return (
    <div className={styles.playerRow}>
      <strong className={styles.displayName}>{player.displayName}</strong>(
      <i>
        {player.firstName} {player.lastName}
      </i>
      )
    </div>
  );
};

const PlayerDisplay = (props) => {
  const { players = [] } = props;
  const playerRows = players.map((player) => <PlayerRow player={player} />);
  return (
    <section className={styles.playerDisplay}>
      <HeaderRow players={players} />
      {playerRows}
    </section>
  );
};

export default PlayerDisplay;
