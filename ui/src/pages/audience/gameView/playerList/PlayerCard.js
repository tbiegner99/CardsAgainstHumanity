import React from 'react';
import combineClasses from 'classnames';
import styles from './playerCard.css';
import { H5 } from '../../../../components/elements/headers/Headers';

const PlayerCard = (props) => {
  const { x: left, y: top, width, height, player, isWinner, hasWinner } = props;
  const style = { left, top, width, height };

  return (
    <div
      style={style}
      className={combineClasses(styles.playerCard, {
        [styles.winner]: isWinner,
        [styles.hasWinner]: hasWinner
      })}
    >
      <H5 className={styles.nameText}>{player.displayName}</H5>
      <div className={styles.scoreLabel}>Score: {player.score}</div>
    </div>
  );
};

export default PlayerCard;
