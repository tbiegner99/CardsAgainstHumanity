import React from 'react';
import fa from 'font-awesome/css/font-awesome.css';
import combineClasses from 'classnames';

const createIconComponent = (cssClass, ...otherClasses) => (props) => {
  const { className, ...otherProps } = props;
  const combinedClasses = combineClasses(cssClass, fa.fa, className, ...otherClasses);
  return <i className={combinedClasses} {...otherProps} />;
};

const IconComponents = {
  User: createIconComponent(fa['fa-user']),
  Hamburger: createIconComponent(fa['fa-bars']),
  CardHand: createIconComponent(fa['fa-clone']),
  Scoreboard: createIconComponent(fa['fa-list-ol']),
  DeckDesign: createIconComponent(fa['fa-edit']),
  NewGame: createIconComponent(fa['fa-gamepad']),
  JoinGame: createIconComponent(fa['fa-user-plus']),
  Stats: createIconComponent(fa['fa-trophy'])
};

export { IconComponents };

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
