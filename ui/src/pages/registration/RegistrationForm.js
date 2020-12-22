import React from 'react';
import { Link } from 'react-router-dom';
import Form from 'reactforms/src/form/Form';
import {
  TextInput,
  PasswordInput,
  FormInput,
  ErrorLabel
} from '../../components/formelements/inputs/TextInput';
import { PrimaryButton } from '../../components/formelements/buttons/Buttons';
import styles from './registrationForm.css';
import Urls from '../../utils/Urls';

class LoginForm extends React.Component {
  async submitRegistration(formData) {
    const { onSubmitRegistration } = this.props;
    try {
      await onSubmitRegistration(formData);
    } catch (err) {
      this.setState({ error: err });
    }
  }

  renderLoginError() {
    return null;
  }

  render() {
    return (
      <Form onSubmit={this.submitRegistration.bind(this)} className={styles.loginForm}>
        <h1 className={styles.formHeader}>Create Account</h1>
        <main>
          <FormInput>
            <TextInput
              data-id="displayName"
              name="displayName"
              data-rule-required
              placeholder="Display Name"
            />
            <ErrorLabel for="displayName" />
          </FormInput>

          <FormInput>
            <TextInput
              data-id="firstName"
              name="firstName"
              data-rule-required
              placeholder="First Name"
            />
            <ErrorLabel for="firstName" />
          </FormInput>

          <FormInput>
            <TextInput
              data-id="lastName"
              name="lastName"
              data-rule-required
              placeholder="Last Name"
            />
            <ErrorLabel for="lastName" />
          </FormInput>
          <FormInput>
            <TextInput
              data-id="username"
              name="email"
              data-rule-required
              data-rule-email
              placeholder="Email"
            />
            <ErrorLabel for="username" />
          </FormInput>

          <FormInput>
            <TextInput
              data-id="match-username"
              name="matchUsername"
              data-rule-required
              data-rule-email
              data-rule-matches-field="email"
              placeholder="Confirm email"
            />
            <ErrorLabel for="matchUsername" />
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
          <FormInput>
            <PasswordInput
              data-id="match-password"
              name="verifyPassword"
              data-rule-required
              data-rule-matches-field="password"
              placeholder="Confirm Password"
            />
            <ErrorLabel for="verifyPassword" />
          </FormInput>
        </main>
        <FormInput className={styles.buttonRow}>
          <Link to={Urls.LOGIN}>Back</Link>
          <PrimaryButton data-id="register-button" submittable>
            Register
          </PrimaryButton>
        </FormInput>
        {this.renderLoginError()}
      </Form>
    );
  }
}

export default LoginForm;
