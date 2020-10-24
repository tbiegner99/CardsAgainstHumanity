/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import { storiesOf } from '@storybook/react';
import { withKnobs, number, object } from '@storybook/addon-knobs';
import JoinGameScreen from '../../../../src/pages/audience/JoinGameScreen';
import AudiencePage from '../../../../src/pages/audience/AudiencePage';

const stories = storiesOf('pages/audience', module);
stories.addDecorator(withKnobs);
stories.add('AudiencePage', () => (
  <div style={{ height: '100vh' }}>
    <AudiencePage />
  </div>
));
stories.add('JoinGamePage', () => (
  <div style={{ height: '100vh' }}>
    <JoinGameScreen />
  </div>
));
