/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import { storiesOf } from '@storybook/react';
import { withKnobs, boolean } from '@storybook/addon-knobs';
import { SlideInMenu, MenuItem } from '../../../src/components/menus/SlideInMenu';

const stories = storiesOf('menus/SlideInMenu', module);
stories.addDecorator(withKnobs);
stories.add('displays correctly', () => (
  <SlideInMenu open={boolean('open', false)} right={boolean('right', false)}>
    <MenuItem>Item1</MenuItem>
  </SlideInMenu>
));
