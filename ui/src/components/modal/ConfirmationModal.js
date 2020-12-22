import React from 'react';
import combineClasses from 'classnames';
import Modal from './Modal';
import { PrimaryButton, SecondaryButton } from '../formelements/buttons/Buttons';
import styles from './modal.css';

const ConfirmationModal = (props) => {
  const {
    onConfirm,
    onCancel,
    confirmText,
    cancelText,
    cancelButtonClass,
    confirmButtonClass,
    children,
    ...otherProps
  } = props;
  return (
    <Modal {...otherProps}>
      <main className={styles.confirmMessage}>{children}</main>
      <footer className={styles.confirmModalFooter}>
        <span className={combineClasses(styles.modalButton, confirmButtonClass)}>
          <PrimaryButton onClick={onConfirm}>{confirmText}</PrimaryButton>
        </span>
        <span className={combineClasses(styles.modalButton, cancelButtonClass)}>
          <SecondaryButton onClick={onCancel}>{cancelText}</SecondaryButton>
        </span>
      </footer>
    </Modal>
  );
};
export default ConfirmationModal;
