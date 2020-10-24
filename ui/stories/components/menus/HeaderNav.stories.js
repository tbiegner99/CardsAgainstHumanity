/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import { storiesOf } from '@storybook/react';
import { withKnobs, boolean } from '@storybook/addon-knobs';
import { HeaderNavBar } from '../../../src/components/menus/HeaderNav';
import DropDownMenu from '../../../src/components/menus/DropDownMenu';
import MenuTitle from '../../../src/components/menus/MenuTitle';
import MenuItem from '../../../src/components/menus/MenuItem';
import Submenu from '../../../src/components/menus/Submenu';

const stories = storiesOf('menus/NavBar', module);
stories.addDecorator(withKnobs);
stories.add('displays correctly', () => (
  <HeaderNavBar>
    <DropDownMenu onToggle={() => {}}>
      <MenuTitle>&#9776;</MenuTitle>
      <Submenu>
        <MenuItem>
          <b>a bunch of items</b>
        </MenuItem>
      </Submenu>
    </DropDownMenu>
    <MenuTitle>Cards Against Humanity</MenuTitle>
    <DropDownMenu rightAlign onToggle={() => {}}>
      <MenuTitle>
        <span role="img" aria-label="user icon">
          &#128100;
        </span>
        some user
      </MenuTitle>
      <Submenu>
        <MenuItem>Leave Game</MenuItem>
        <MenuItem>Log Out</MenuItem>
      </Submenu>
    </DropDownMenu>
  </HeaderNavBar>
));
