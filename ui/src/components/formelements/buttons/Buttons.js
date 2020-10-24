import React from 'react';
import Button from 'reactforms/src/form/elements/Button';
import combineClasses from 'classnames';
import styles from './buttons.css';

const createButtonWithClass = (baseClass) => (props) => {
  const { className, ...otherProps } = props;
  const combinedClasses = combineClasses(styles.button, baseClass, className);
  return <Button className={combinedClasses} {...otherProps} />;
};

const PrimaryButton = createButtonWithClass(styles.primary);

const SecondaryButton = createButtonWithClass(styles.secondary);

export { PrimaryButton, SecondaryButton };
