import React from 'react';
import FormsSelect from 'reactforms/src/form/elements/Select';
import Option from 'reactforms/src/form/elements/Option';

import combineClasses from 'classnames';
import styles from './dropdown.css';

const createWithClass = (InputClass, ...cssClasses) => (props) => {
  const { className, children, ...otherProps } = props;
  const combinedClasses = combineClasses(...cssClasses, className);
  return (
    <FormsSelect className={combinedClasses} {...otherProps}>
      {children}
    </FormsSelect>
  );
};

const createInputWithClass = (InputClass, ...cssClasses) =>
  createWithClass(InputClass, styles.input, ...cssClasses);

const Dropdown = createInputWithClass(styles.select);

export { Dropdown, Option };
