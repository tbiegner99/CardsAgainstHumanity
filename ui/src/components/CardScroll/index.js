import React from 'react';
import combineClasses from 'classnames';
import { FlexContainer } from '../FlexContainer';
import { Card } from '../CardSlider';

import styles from './index.css';

const renderChild = (child, props) => {
  const { vertical, cardClass } = props;
  const cardClasses = combineClasses(styles.card, cardClass, child.props.className, {});
  return React.cloneElement(child, { className: cardClasses });
};

const renderChildren = (props) =>
  React.Children.map(props.children, (child) => renderChild(child, props));

const CardScroll = (props) => {
  const className = props.vertical ? styles.column : styles.row;
  return <FlexContainer className={className}>{renderChildren(props)}</FlexContainer>;
};
export { CardScroll, Card };
