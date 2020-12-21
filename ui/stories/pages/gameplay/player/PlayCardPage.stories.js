/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import { storiesOf } from '@storybook/react';
import { withKnobs } from '@storybook/addon-knobs';
import PlayCardPage from '../../../../src/pages/authenticated/gameplay/roundPlay/playCard/PlayCardPage';

const blackCard = {
  numberOfAnswers: 1,
  text:
    'Next season on Man vs. Wild, Bear Grylls must survive in the depths of the Amazon with only ________________ and his wits.'
};

const questionBlackCard = {
  numberOfAnswers: 1,
  text: 'What ruined the annual family picnic?'
};

const multiPlayBlackCard = {
  numberOfAnswers: 3,
  text: 'Make a haiku'
};

const handCards = [
  {
    text: 'Brian'
  },
  {
    text: 'TJ'
  },
  {
    text: 'Rizzi'
  },
  {
    text: 'John'
  },
  {
    text: 'Lauren'
  }
];

const stories = storiesOf('pages/gameplay/player/Play Card', module);
stories.addDecorator(withKnobs);
stories.add('Single Blank Play', () => (
  <PlayCardPage handCards={handCards} blackCard={blackCard} />
));
stories.add('Question Card Play', () => (
  <PlayCardPage handCards={handCards} blackCard={questionBlackCard} />
));
stories.add('Multiple Play', () => (
  <PlayCardPage handCards={handCards} blackCard={multiPlayBlackCard} />
));
