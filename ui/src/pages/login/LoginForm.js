import React from 'react';
import { Link } from 'react-router-dom';
import queryString from 'query-string';
import Form from 'reactforms/src/form/Form';
import {
  TextInput,
  PasswordInput,
  FormInput,
  ErrorLabel
} from '../../components/formelements/inputs/TextInput';
import { PrimaryButton } from '../../components/formelements/buttons/Buttons';
import styles from './loginForm.css';
import Urls from '../../utils/Urls';

class LoginForm extends React.Component {
  async submitLogin(formData) {
    const { onCheckLogin } = this.props;
    const { username, password } = formData;
    const { redirectUrl } = queryString.parse(window.location.search);
    try {
      await onCheckLogin(username, password, redirectUrl);
    } catch (err) {
      this.setState({ error: err });
    }
  }

  renderLoginError() {
    return null;
  }

  render() {
    return (
      <Form onSubmit={this.submitLogin.bind(this)} className={styles.loginForm}>
        <h1 className={styles.formHeader}>Login</h1>
        <main>
          <FormInput>
            <TextInput
              data-id="username"
              name="username"
              data-rule-required
              data-rule-email
              placeholder="Email"
            />
            <ErrorLabel for="username" />
          </FormInput>
          <FormInput>
            <PasswordInput
              data-id="password"
              name="password"
              data-rule-required
              placeholder="Password"
            />
            <ErrorLabel for="password" />
          </FormInput>
        </main>
        <FormInput className={styles.buttonRow}>
          <Link data-id="register-link" to={Urls.REGISTER}>
            Register
          </Link>
          <PrimaryButton data-id="login-button" submittable>
            Login
          </PrimaryButton>
        </FormInput>

        <FormInput className={styles.audienceRow}>
          <Link data-id="audience-join-link" to={Urls.Audience.HOME}>
            Join game as audience member
          </Link>
        </FormInput>
        {this.renderLoginError()}
      </Form>
    );
  }
}

export default LoginForm;
