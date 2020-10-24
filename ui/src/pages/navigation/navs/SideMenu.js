import React from 'react';
import { SlideInMenu } from '../../../components/menus/SlideInMenu';
import MenuItem from '../../../components/menus/MenuItem';

const renderChildren = () => [];

const SideMenu = (props) => {
  const { open, onClose } = props;
  return (
    <SlideInMenu open={open}>
      <MenuItem onClick={onClose}>X</MenuItem>
      {renderChildren(props)}
    </SlideInMenu>
  );
};

export default SideMenu;
