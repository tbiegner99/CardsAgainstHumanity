/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import { storiesOf } from '@storybook/react';
import { withKnobs } from '@storybook/addon-knobs';
import {
  PrimaryButton,
  SecondaryButton
} from '../../../src/components/formelements/buttons/Buttons';

const stories = storiesOf('Buttons', module);
stories.addDecorator(withKnobs);
stories.add('Primary Button', () => <PrimaryButton>Click Me</PrimaryButton>);

stories.add('Secondary Button', () => <SecondaryButton>Click Me</SecondaryButton>);
