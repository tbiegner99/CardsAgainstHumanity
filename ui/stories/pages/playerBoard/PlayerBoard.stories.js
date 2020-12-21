/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import { storiesOf } from '@storybook/react';
import { withKnobs } from '@storybook/addon-knobs';
import PlayerBoard, {
  SortDirection,
  DataFields
} from '../../../src/components/playerBoard/PlayerBoard';

const playerData = [
  {
    name: 'Brian',
    czarOrder: 1,
    score: 5
  },
  {
    name: 'TJ',
    czarOrder: 4,
    score: 7
  },
  {
    name: 'Rizzi',
    czarOrder: 2,
    score: 3
  },
  {
    name: 'John',
    czarOrder: 3,
    score: 3
  },
  {
    name: 'Lauren',
    czarOrder: 5,
    score: 4
  },
  {
    name: 'Chris',
    czarOrder: 7,
    score: 7
  },
  {
    name: 'Martino',
    czarOrder: 6,
    score: 2
  }
];

const stories = storiesOf('pages/playerBord/PlayerBoard', module);
stories.addDecorator(withKnobs);
stories.add('Player Scoreboard', () => (
  <PlayerBoard
    sortDirection={SortDirection.DESCENDING}
    sortField={DataFields.SCORE}
    playerData={playerData}
  />
));
stories.add('Player Czar Order', () => <PlayerBoard sort />);
