import React from 'react';
import { HeaderNavBar } from '../../../components/menus/HeaderNav';
import Branding from './Branding';
import MenuTitle from '../../../components/menus/MenuTitle';
import MenuItem from '../../../components/menus/MenuItem';
import { IconComponents } from '../../../components/Icons/icons';

const renderUserMenu = (props) => {
  if (!props.authenticated) {
    return <div />;
  }
  return (
    <MenuItem onClick={() => props.onHamburgerClick()}>
      <MenuTitle>
        <IconComponents.Hamburger />
      </MenuTitle>
    </MenuItem>
  );
};

const NavBar = (props) => (
  <HeaderNavBar>
    {renderUserMenu(props)}

    <Branding />
    <div />
  </HeaderNavBar>
);

export default NavBar;
