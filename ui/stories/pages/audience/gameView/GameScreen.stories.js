/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import { storiesOf } from '@storybook/react';
import { withKnobs, number, object } from '@storybook/addon-knobs';
import GameScreen from '../../../../src/pages/audience/gameView/GameScreen';

const blackCard = {
  numberOfAnswers: 2,
  text: 'What ruined the annual family picnic?'
};

const czarOrder = [
  {
    playerId: 1,
    name: 'Rizzi',
    score: 8
  },
  {
    playerId: 2,
    name: 'TJ',
    score: 8
  },
  {
    playerId: 3,
    name: 'John',
    score: 8
  },
  {
    playerId: 4,
    name: 'Lauren',
    score: 8
  },
  {
    playerId: 6,
    name: 'Brian',
    score: 8
  }
];

const round = {
  blackCard,
  numberOfPlays: number('number of plays', 6),
  revealedCards: [
    {
      playId: 2,
      cards: [{ text: 'card1' }, { text: 'card2' }]
    },
    {
      playId: 3,
      cards: [{ text: 'card3' }, { text: 'card4' }]
    },
    {
      playId: 4,
      cards: [{ text: 'card5' }, { text: 'card6' }]
    },
    {
      playId: 5,
      cards: [{ text: 'card7' }, { text: 'card8' }]
    }
  ],
  winningPlayId: number('winningPlayId', 1)
};

const stories = storiesOf('pages/audience/gameView', module);
stories.addDecorator(withKnobs);
stories.add('Single Blank Play', () => (
  <GameScreen czarOrder={object('players', czarOrder)} roundData={object('roundData', round)} />
));
