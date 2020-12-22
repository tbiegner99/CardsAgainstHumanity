import React from 'react';
import DeckDisplay from './DeckDisplay';
import { EditIcon, DeleteIcon } from '../../../../../components/Icons/icons';

import styles from './deckRow.css';

const DeckActions = (props) => (
  <div className={styles.actions}>
    <EditIcon className={styles.action} onClick={props.onEdit} />

    <DeleteIcon className={styles.action} onClick={props.onDelete} />
  </div>
);

export default (props) => {
  const { deck, onEdit, onDelete } = props;
  const { whiteCardCount, blackCardCount } = deck;
  const callAction = (action) => () => {
    if (typeof action === 'function') {
      action(deck);
    }
  };
  return (
    <div className={styles.packageRow}>
      <DeckDisplay className={styles.display} deck={deck} />
      <DeckActions onEdit={callAction(onEdit)} onDelete={callAction(onDelete)} />
    </div>
  );
};
