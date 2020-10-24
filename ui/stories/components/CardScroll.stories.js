/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import { storiesOf } from '@storybook/react';
import { withKnobs, boolean, number } from '@storybook/addon-knobs';
import { CardScroll, Card } from '../../src/components/CardScroll';
import { BlackCard } from '../../src/components/CardsAgainstHumanityCard';

import style from './CardSlider.css';

const stories = storiesOf('CardScroll', module);
stories.addDecorator(withKnobs);
stories.add('displays correctly', () => (
  <CardScroll cardClass={style.scrollCard} vertical={boolean('vertical', false)}>
    <Card>
      <BlackCard flipped={boolean('flipped1', false)}>
        Next season on Man vs. Wild, Bear Grylls must survive in the depths of the Amazon with only
        ________________ and his wits.
      </BlackCard>
    </Card>
    <Card>
      <BlackCard flipped={boolean('flipped2', true)}>
        What ruined the annual family picnic?
      </BlackCard>
    </Card>
    <Card>
      <BlackCard flipped={boolean('flipped3', true)}>Make a haiku.</BlackCard>
    </Card>
    <Card>
      <BlackCard flipped={boolean('flipped4', true)}>Card4</BlackCard>
    </Card>
    <Card>
      <BlackCard flipped={boolean('flipped5', true)}>Card5</BlackCard>
    </Card>
    <Card>
      <BlackCard flipped={boolean('flipped6', true)}>Card6</BlackCard>
    </Card>
    <Card>
      <BlackCard flipped={boolean('flipped7', true)}>Card7</BlackCard>
    </Card>
    <Card>
      <BlackCard flipped={boolean('flipped8', true)}>Card8</BlackCard>
    </Card>
    <Card>
      <BlackCard flipped={boolean('flipped9', true)}>Card9</BlackCard>
    </Card>
    <Card>
      <BlackCard flipped={boolean('flipped10', true)}>Card10</BlackCard>
    </Card>
  </CardScroll>
));
