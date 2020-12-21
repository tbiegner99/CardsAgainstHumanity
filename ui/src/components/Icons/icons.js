import React from 'react';
import fa from 'font-awesome/css/font-awesome.css';
import combineClasses from 'classnames';

const createIconComponent = (cssClass, ...otherClasses) => (props) => {
  const { className, ...otherProps } = props;
  const combinedClasses = combineClasses(cssClass, fa.fa, className, ...otherClasses);
  return <i className={combinedClasses} {...otherProps} />;
};

export const UserIcon = createIconComponent(fa['fa-user']);
export const UpArrow = createIconComponent(fa['fa-caret-up']);
export const DownArrow = createIconComponent(fa['fa-caret-down']);
export const HamburgerIcon = createIconComponent(fa['fa-bars']);
export const CardHandIcon = createIconComponent(fa['fa-clone']);
export const ScoreboardIcon = createIconComponent(fa['fa-list-ol']);
export const DeckDesignIcon = createIconComponent(fa['fa-edit']);
export const NewGameIcon = createIconComponent(fa['fa-gamepad']);
export const JoinGameIcon = createIconComponent(fa['fa-user-plus']);
export const StatsIcon = createIconComponent(fa['fa-trophy']);
export const AddIcon = createIconComponent(fa['fa-plus']);
export const EditIcon = createIconComponent(fa['fa-edit']);
export const DeleteIcon = createIconComponent(fa['fa-trash']);
export const CardIcon = createIconComponent(fa['fa-square']);
export const DeckIcon = createIconComponent(fa['fa-bars']);
export const UploadIcon = createIconComponent(fa['fa-upload']);
export const IconComponents = {
  User: UserIcon,
  Hamburger: HamburgerIcon,
  CardHand: CardHandIcon,
  Scoreboard: ScoreboardIcon,
  DeckDesign: DeckDesignIcon,
  NewGame: NewGameIcon,
  JoinGame: JoinGameIcon,
  Stats: StatsIcon,
  Add: AddIcon,
  Deck: DeckIcon,
  Card: CardIcon
};

export default {
  User: <IconComponents.User />,
  Hamburger: <IconComponents.Hamburger />,
  CardHand: <IconComponents.CardHand />,
  Scoreboard: <IconComponents.Scoreboard />,
  DeckDesign: <IconComponents.DeckDesign />,
  NewGame: <IconComponents.NewGame />,
  JoinGame: <IconComponents.JoinGame />,
  Stats: <IconComponents.Stats />
};
