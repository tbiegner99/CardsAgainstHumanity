import React from 'react';
import LoginForm from './LoginForm';
import styles from './loginPage.css';
import { Redirect } from 'react-router-dom';

const renderLoginForm = (props) => {
  if (props.authenticated && props.loaded) {
    return <Redirect to="/auth" />;
  }
  return <LoginForm {...props} />;
};

const LoginPage = (props) => <main className={styles.loginPage}>{renderLoginForm(props)}</main>;

export default LoginPage;
