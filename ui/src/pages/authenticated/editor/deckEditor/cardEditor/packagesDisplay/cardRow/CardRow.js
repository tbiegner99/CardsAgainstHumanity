import React from 'react';
import CardDisplay from './CardDisplay';
import { EditIcon, DeleteIcon } from '../../../../../../../components/Icons/icons';

import styles from './CardRow.css';

const CardActions = (props) => (
  <div className={styles.actions}>
    <EditIcon className={styles.action} onClick={props.onEdit} />
    <DeleteIcon className={styles.action} onClick={props.onDelete} />
  </div>
);

export default (props) => {
  const { card, onEdit, onDelete } = props;
  const callAction = (action) => () => {
    if (typeof action === 'function') {
      action(card);
    }
  };
  return (
    <div className={styles.packageRow}>
      <CardDisplay className={styles.display} card={card} />
      <CardActions onEdit={callAction(onEdit)} onDelete={callAction(onDelete)} />
    </div>
  );
};
