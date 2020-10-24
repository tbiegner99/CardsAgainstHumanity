import React from 'react';
import Icons from '../Icons/icons';
import styles from './index.css';

const OptionMenuItem = (props) => (
  <section className={styles.gameOption}>
    <div className={styles.icon}>{props.icon}</div>
    <div className={styles.name}>{props.name}</div>
  </section>
);

const GameOptionsBar = (props) => (
  <menu className={styles.gameOptionsMenu}>
    <div className={styles.status}>
      <div className={styles.selectedStatus} style={{ left: `${props.selectedIndex * 25}%` }} />
    </div>

    <OptionMenuItem name="Round State" icon={Icons.CardHand} />
    <OptionMenuItem name="Play Card" icon={Icons.CardHand} />
    <OptionMenuItem name="Czar Order" icon={Icons.CardHand} />
    <OptionMenuItem name="Scoreboard" icon={Icons.Scoreboard} />
  </menu>
);

export default GameOptionsBar;
