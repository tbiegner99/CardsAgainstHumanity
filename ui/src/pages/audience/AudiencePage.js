import React from 'react';

import styles from './audiencePage.css';

import { PrimaryButton, SecondaryButton } from '../../components/formelements/buttons/Buttons';

class AudiencePage extends React.Component {
  render() {
    return (
      <section className={styles.audiencePage}>
        <PrimaryButton keyShortcut="h">Host New Game</PrimaryButton>
        <SecondaryButton keyShortcut="j">Join Existing Game</SecondaryButton>
      </section>
    );
  }
}

export default AudiencePage;
