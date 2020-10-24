/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import { storiesOf } from '@storybook/react';
import { withKnobs } from '@storybook/addon-knobs';
import { TextInput, PasswordInput } from '../../../src/components/formelements/inputs/TextInput';

const stories = storiesOf('Inputs', module);
stories.addDecorator(withKnobs);
stories.add('TextInput', () => <TextInput placeholder="someText" />);
stories.add('Password', () => <PasswordInput placeholder="password" />);
