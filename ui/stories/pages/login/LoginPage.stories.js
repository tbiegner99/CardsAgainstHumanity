/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import { storiesOf } from '@storybook/react';
import { withKnobs } from '@storybook/addon-knobs';
import LoginPage from '../../../src/pages/login/LoginPage';

const stories = storiesOf('pages/login/LoginPage', module);
stories.addDecorator(withKnobs);
stories.add('Login Page', () => <LoginPage />);
