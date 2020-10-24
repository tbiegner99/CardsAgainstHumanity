import React from 'react';
import Form from 'reactforms/src/form/Form';
import {
  TextInput,
  PasswordInput,
  FormInput,
  ErrorLabel
} from '../../components/formelements/inputs/TextInput';
import { PrimaryButton } from '../../components/formelements/buttons/Buttons';
import styles from './loginForm.css';

class LoginForm extends React.Component {
  async submitLogin(formData) {
    const { onCheckLogin } = this.props;
    const { username, password } = formData;
    try {
      await onCheckLogin(username, password);
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
            <TextInput name="username" data-rule-required data-rule-email placeholder="Email" />
            <ErrorLabel for="username" />
          </FormInput>
          <FormInput>
            <PasswordInput name="password" data-rule-required placeholder="Password" />
            <ErrorLabel for="password" />
          </FormInput>
        </main>
        <FormInput className={styles.buttonRow}>
          <PrimaryButton submittable>Login</PrimaryButton>
        </FormInput>
        {this.renderLoginError()}
      </Form>
    );
  }
}

export default LoginForm;
