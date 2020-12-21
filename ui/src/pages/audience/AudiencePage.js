import React from 'react';
import Form from 'reactforms/src/form/Form';
import ErrorLabel from 'reactforms/src/form/elements/ErrorLabel';
import { PrimaryButton, SecondaryButton } from '../../components/formelements/buttons/Buttons';
import { FormInput, TextInput } from '../../components/formelements/inputs/TextInput';

import styles from './audiencePage.css';

class AudiencePage extends React.Component {
  render() {
    const { onJoinGame } = this.props;
    return (
      <section className={styles.audiencePage}>
        {/* <PrimaryButton keyShortcut="h">Host New Game</PrimaryButton> */}
        <Form onSubmit={onJoinGame}>
          <FormInput>
            <TextInput data-id="code" name="code" data-rule-required placeholder="Code" />
            <ErrorLabel for="code" />
          </FormInput>
          <SecondaryButton submittable className={styles.optionButton} keyShortcut="j">
            Join Existing Game
          </SecondaryButton>
        </Form>
      </section>
    );
  }
}

export default AudiencePage;
