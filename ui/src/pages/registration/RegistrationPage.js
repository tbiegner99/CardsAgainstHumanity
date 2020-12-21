import React from 'react';
import { Redirect } from 'react-router-dom';
import RegistrationForm from './RegistrationForm';
import styles from './registrationPage.css';

const renderLoginForm = (props) => {
  if (props.authenticated && props.loaded) {
    return <Redirect to="/auth" />;
  }
  return <RegistrationForm {...props} />;
};

const RegisterPage = (props) => <main className={styles.loginPage}>{renderLoginForm(props)}</main>;

export default RegisterPage;
