import React from 'react';
import styles from './playerCard.css';
import { H5 } from '../../../../components/elements/headers/Headers';

const PlayerCard = (props) => {
  const { x: left, y: top, width, height, player } = props;
  const style = { left, top, width, height };

  return (
    <div style={style} className={styles.playerCard}>
      <H5 className={styles.nameText}>{player.name}</H5>
      <div className={styles.scoreLabel}>Score: {player.score}</div>
    </div>
  );
};

export default PlayerCard;
