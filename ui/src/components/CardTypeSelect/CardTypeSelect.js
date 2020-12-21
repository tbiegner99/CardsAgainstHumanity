import React from 'react';
import HiddenField from 'reactforms/src/form/elements/HiddenField';
import InputGroup from 'reactforms/src/form/elements/InputGroup';

import { SecondaryButton } from '../formelements/buttons/Buttons';
import styles from './cardTypeSelect.css';

const StaticCardTypeSelect = (props) => {
  const { name, type, ...otherProps } = props;
  return (
    <div {...otherProps}>
      <HiddenField name={name} value={type} />
      <SecondaryButton selected={type === 'white'} value="white" className={styles.toggleButton}>
        White
      </SecondaryButton>
      <SecondaryButton selected={type === 'black'} value="black" className={styles.toggleButton}>
        Black
      </SecondaryButton>
    </div>
  );
};

const CardTypeSelect = (props) => {
  const { type, onChange, disabled, name, ...otherProps } = props;
  if (disabled) {
    return <StaticCardTypeSelect type={type} name={name} {...otherProps} />;
  }
  return (
    <InputGroup {...otherProps} name={name} onChange={onChange}>
      <SecondaryButton selected={type === 'white'} value="white" className={styles.toggleButton}>
        White
      </SecondaryButton>
      <SecondaryButton selected={type === 'black'} value="black" className={styles.toggleButton}>
        Black
      </SecondaryButton>
    </InputGroup>
  );
};
export default CardTypeSelect;
