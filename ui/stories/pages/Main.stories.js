/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import { storiesOf } from '@storybook/react';
import { withKnobs } from '@storybook/addon-knobs';
import Main from '../../src/pages/authenticated/gameplay/PlayGame';

const stories = storiesOf('pages', module);
stories.addDecorator(withKnobs);
stories.add('Main', () => <Main />);
