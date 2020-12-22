import React from 'react';
import Redirect from '../../components/Redirect';
import LoginForm from './LoginForm';
import styles from './loginPage.css';
import Urls from '../../utils/Urls';

const renderLoginForm = (props) => {
  if (props.authenticated && props.loaded) {
    return <Redirect to={Urls.AUTHENTICATED} />;
  }
  return <LoginForm {...props} />;
};

const LoginPage = (props) => <main className={styles.loginPage}>{renderLoginForm(props)}</main>;

export default LoginPage;
