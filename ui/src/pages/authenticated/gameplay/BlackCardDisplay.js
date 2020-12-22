import React from 'react';
import CardUtilities from '../../../utils/CardUtilities';
import styles from './roundPlay/playCard/playCardPage.css';

const BlackCardDisplay = (props) => (
  <section className={styles.blackCardDisplay}>
    {CardUtilities.previewPlay(props.card, props.currentPlay)}
  </section>
);

export default BlackCardDisplay;
