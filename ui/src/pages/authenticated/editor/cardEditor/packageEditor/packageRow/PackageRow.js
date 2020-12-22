import React from 'react';
import PackageDisplay from './PackageDisplay';
import { EditIcon, DeleteIcon } from '../../../../../../components/Icons/icons';

import styles from './PackageRow.css';

const PackageActions = (props) => (
  <div className={styles.actions}>
    <EditIcon className={styles.action} onClick={props.onEdit} />

    {props.canDelete && <DeleteIcon className={styles.action} onClick={props.onDelete} />}
  </div>
);

export default (props) => {
  const { package: pack, onEdit, onDelete } = props;
  const { whiteCardCount, blackCardCount } = pack;
  const totalCards = whiteCardCount + blackCardCount;
  const callAction = (action) => () => {
    if (typeof action === 'function') {
      action(pack);
    }
  };
  return (
    <div className={styles.packageRow}>
      <img
        className={styles.icon}
        src="https://www.flaticon.com/svg/static/icons/svg/25/25694.svg"
      />
      <PackageDisplay className={styles.display} package={pack} />
      <PackageActions
        onEdit={callAction(onEdit)}
        onDelete={callAction(onDelete)}
        canDelete={totalCards === 0}
      />
    </div>
  );
};
