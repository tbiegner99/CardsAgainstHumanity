/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import { storiesOf } from '@storybook/react';
import { withKnobs, boolean } from '@storybook/addon-knobs';
import { FlipCard } from '../../src/components/FlipCard';

import { WhiteCard, BlackCard } from '../../src/components/CardsAgainstHumanityCard';

const stories = storiesOf('FlipCard', module);
stories.addDecorator(withKnobs);
stories.add('displays correctly', () => <FlipCard flipped={boolean('flipped', false)} />);
stories.add('white card', () => (
  <WhiteCard flipped={boolean('flipped', false)}>Bullshit</WhiteCard>
));
stories.add('black card', () => (
  <BlackCard flipped={boolean('flipped', false)}>
    Next season on Man vs. Wild, Bear Grylls must survive in the depths of the Amazon with only
    ________________ and his wits.
  </BlackCard>
));
