import React from 'react';
import combineClasses from 'classnames';
import style from './index.css';

const CardFace = (props) => (
  <div className={combineClasses(style.face, props.className)}>{props.children}</div>
);

/* const CardFace = (props) => (
  <svg viewBox="0 0 210 300" className={combineClasses(style.face, props.className)}>
    <rect x="0" y="0" width="210" height="300" rx="5" ry="5" />
    <foreignObject x="15" y="15" width="180" height="270">
      <div xmlns="http://www.w3.org/1999/xhtml">{props.children}</div>
    </foreignObject>
  </svg>
); */

class FrontFace extends React.Component {
  render() {
    return (
      <CardFace className={combineClasses(style.frontFace, this.props.className)}>
        {this.props.children}
      </CardFace>
    );
  }
}

class BackFace extends React.Component {
  render() {
    return (
      <CardFace className={combineClasses(style.backFace, this.props.className)}>
        {this.props.children}
      </CardFace>
    );
  }
}

const renderChildOfType = (children, clazz) => {
  const childArray = React.Children.toArray(children);
  const foundElements = childArray.filter((child) => child.type === clazz);

  return foundElements[0] || null;
};

const FlipCard = (props) => {
  const { flipCard, cardContainer, flipped: flippedStyle } = style;
  const { flipped, children, className, ...otherProps } = props;
  const isCardFaceDown = !flipped;
  const cardClasses = combineClasses(flipCard, className, {
    [flippedStyle]: isCardFaceDown
  });

  return (
    <svg viewBox="0 0 210 300" {...otherProps} className={cardClasses}>
      <foreignObject x="0" y="0" width="210" height="300">
        <div className={cardContainer}>
          {renderChildOfType(children, FrontFace)}
          {renderChildOfType(children, BackFace)}
        </div>
      </foreignObject>
    </svg>
  );
};

export { FlipCard, FrontFace, BackFace };
