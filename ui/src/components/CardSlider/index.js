import React from 'react';
import combineClasses from 'classnames';
import style from './index.css';

class Card extends React.Component {
  render() {
    const { className, children, selected, width, ...otherProps } = this.props;
    const classes = combineClasses(style.sliderCard, className, { selected });
    const elementStyle = { width };
    return (
      <div style={elementStyle} className={classes} {...otherProps}>
        {children}
      </div>
    );
  }
}

class CardSlider extends React.Component {
  constructor(props) {
    super(props);
    this.state = { selectedIndex: 0, frameIndex: 0 };
  }

  renderArrowIf(content, condition, action) {
    const className = combineClasses(style.arrow, { [style.disabled]: !condition });
    return (
      <div onClick={action} className={className}>
        {content}
      </div>
    );
  }

  getNumberOfElementsToScroll() {
    return this.props.cardsPerFrame;
  }

  renderRightArrow() {
    const { frameIndex } = this.state;
    const numberOfCards = React.Children.count(this.props.children);
    const elementsToScroll = this.getNumberOfElementsToScroll();
    const scrollRight = () =>
      this.setState({ frameIndex: Math.min(numberOfCards - 1, frameIndex + elementsToScroll) });
    const canScrollRight = frameIndex + elementsToScroll < numberOfCards;
    return this.renderArrowIf('>', canScrollRight, scrollRight);
  }

  renderLeftArrow() {
    const { frameIndex } = this.state;
    const elementsToScroll = this.getNumberOfElementsToScroll();
    const scrollLeft = () =>
      this.setState({ frameIndex: Math.max(0, frameIndex - elementsToScroll) });
    const canScrollLeft = frameIndex > 0;
    return this.renderArrowIf('<', canScrollLeft, scrollLeft);
  }

  renderChild(child, width) {
    const cardClass = combineClasses(this.props.cardClass, child.props.className);
    return React.cloneElement(child, { className: cardClass, width: `${width}%` });
  }

  renderCards(cardWidth) {
    return React.Children.map(this.props.children, (child, key) =>
      this.renderChild(child, cardWidth, key)
    );
  }

  renderCardPane() {
    const cardWidth = 100 / this.props.cardsPerFrame;
    const rowWidth = cardWidth * React.Children.count(this.props.children);
    const leftAdjustment = -1 * cardWidth * this.state.frameIndex;
    const rowStyle = { width: `${rowWidth}%`, left: `${leftAdjustment}%` };
    return (
      <section className={style.cardFrame}>
        <div style={rowStyle} className={style.cardRow}>
          {this.renderCards(cardWidth)}
        </div>
      </section>
    );
  }

  render() {
    const componentClasses = combineClasses(style.cardSlider, this.props.className);
    return (
      <section className={componentClasses}>
        {this.renderLeftArrow()}
        {this.renderCardPane()}
        {this.renderRightArrow()}
      </section>
    );
  }
}

export { CardSlider, Card };
