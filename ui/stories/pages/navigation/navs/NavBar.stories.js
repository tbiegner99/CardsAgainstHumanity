/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import { storiesOf } from '@storybook/react';
import { withKnobs } from '@storybook/addon-knobs';
import NavBar from '../../../../src/pages/navigation/navs/NavBar';

const stories = storiesOf('pages/navigation/nav/NavBar', module);
stories.addDecorator(withKnobs);
stories.add('NavBar', () => <NavBar />);
