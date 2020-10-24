/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import { storiesOf } from '@storybook/react';
import { withKnobs } from '@storybook/addon-knobs';
import LoginForm from '../../../src/pages/login/LoginForm';

const stories = storiesOf('pages/login/LoginForm', module);
stories.addDecorator(withKnobs);
stories.add('Login Form', () => <LoginForm />);
