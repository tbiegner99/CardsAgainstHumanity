/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import { storiesOf } from '@storybook/react';
import { withKnobs, number } from '@storybook/addon-knobs';
import GameOptionsBar from '../../src/components/GameOptionsBar';

const stories = storiesOf('GameOptionsBar', module);
stories.addDecorator(withKnobs);
stories.add('display', () => <GameOptionsBar selectedIndex={number('selectedIndex', 0)} />);
