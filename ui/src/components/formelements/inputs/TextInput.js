import React from 'react';
import FormsTextInput from 'reactforms/src/form/elements/TextInput';
import FormsPasswordInput from 'reactforms/src/form/elements/PasswordInput';
import FormsErrorLabel from 'reactforms/src/form/elements/ErrorLabel';
import combineClasses from 'classnames';
import styles from './inputs.css';

const createWithClass = (InputClass, ...cssClasses) => (props) => {
  const { className, ...otherProps } = props;
  const combinedClasses = combineClasses(...cssClasses, className);
  return <InputClass className={combinedClasses} {...otherProps} />;
};

const createInputWithClass = (InputClass, ...cssClasses) =>
  createWithClass(InputClass, styles.input, ...cssClasses);

const FormInput = (props) => {
  const { className, ...otherProps } = props;
  const combinedClasses = combineClasses(styles.formInput, className);
  return <div className={combinedClasses} {...otherProps} />;
};

const TextInput = createInputWithClass(FormsTextInput, styles.textInput);
const PasswordInput = createInputWithClass(FormsPasswordInput, styles.textInput);
const ErrorLabel = createWithClass(FormsErrorLabel, styles.errorLabel);

export { TextInput, PasswordInput, FormInput, ErrorLabel };
