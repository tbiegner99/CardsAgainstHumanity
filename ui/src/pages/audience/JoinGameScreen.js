import React from 'react';
import Form from 'reactforms/src/form/Form';
import styles from './joinGameScreen.css';

import { PrimaryButton } from '../../components/formelements/buttons/Buttons';
import { TextInput } from '../../components/formelements/inputs/TextInput';
import ErrorLabel from 'reactforms/src/form/elements/ErrorLabel';

class AudienceJoinPage extends React.Component {
  render() {
    return (
      <Form className={styles.joinGameScreen} onSubmit={(args) => console.log(args)}>
        <TextInput data-rule-required name="code" placeholder="Code" />
        <ErrorLabel for="code" className={styles.errLabel} />
        <PrimaryButton submittable>Join Game</PrimaryButton>
      </Form>
    );
  }
}

export default AudienceJoinPage;
