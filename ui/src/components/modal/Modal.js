import React from 'react';
import combineClasses from 'classnames';
import { H3 } from '../elements/headers/Headers';
import styles from './modal.css';

const renderModalClose = (onClose) => {
  if (!onClose) {
    return null;
  }

  return (
    <button onClick={onClose} className={styles.modalClose}>
      X
    </button>
  );
};

const Modal = (props) => {
  const { onClose, title, children, className, ...otherProps } = props;
  return (
    <div className={styles.modal}>
      <div className={styles.modalBack} />
      <div className={styles.modalFrame}>
        <div {...otherProps} className={combineClasses(styles.modalContent, className)}>
          <header>{renderModalClose(props.onClose)}</header>
          <H3 className={styles.title}>{props.title}</H3>
          <main className={styles.mainContent}>{props.children}</main>
        </div>
      </div>
    </div>
  );
};
export default Modal;
