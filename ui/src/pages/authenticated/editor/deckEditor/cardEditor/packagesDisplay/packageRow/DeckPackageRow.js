import React from 'react';
import Checkbox from 'reactforms/src/form/elements/Checkbox';
import { UpArrow, DownArrow } from '../../../../../../../components/Icons/icons';
import { H4, H5 } from '../../../../../../../components/elements/headers/Headers';

import styles from './deckPackageRow.css';

class DeckPackageRow extends React.Component {
  constructor(props) {
    super(props);
    this.state = { expanded: false };
  }

  toggleExpanded() {
    const { expanded } = this.state;
    const { package: pack, details, onLoadPackageDetails } = this.props;
    if (!expanded && typeof details === 'undefined' && onLoadPackageDetails) {
      onLoadPackageDetails(pack);
    }
    this.setState({ expanded: !expanded });
  }

  selectPackageChange(value) {
    const { package: pack, onAddPackage, onRemovePackage } = this.props;
    if (value) {
      onAddPackage(pack);
    } else {
      onRemovePackage(pack);
    }
  }

  selectCardChange(value, card, cardType) {
    const { package: pack, onAddCard, onRemoveCard } = this.props;
    if (value) {
      onAddCard(pack.id, cardType, card.id);
    } else {
      onRemoveCard(cardType, card.id);
    }
  }

  renderCardSelect(card, type) {
    const { packageCards } = this.props;
    const inclusions =
      type === 'white' ? packageCards.whiteCardInclusions : packageCards.blackCardInclusions;
    let selected = packageCards.allCards;
    if (typeof inclusions[card.id] === 'boolean') {
      selected = inclusions[card.id];
    }
    return (
      <div className={styles.cardSelect}>
        <Checkbox
          selected={selected}
          onChange={(val) => this.selectCardChange(val, card, type)}
          className={styles.checkIcon}
        >
          {card.cardText}
        </Checkbox>
      </div>
    );
  }

  renderCards(type) {
    const { details } = this.props;
    if (!details) {
      return <H5>Loading...</H5>;
    }
    const cardsToRender = type === 'white' ? details.whiteCards : details.blackCards;
    return cardsToRender.map((card) => this.renderCardSelect(card, type));
  }

  renderExpandedSection() {
    const { expanded } = this.state;
    if (!expanded) {
      return null;
    }
    return (
      <section className={styles.card}>
        <div>
          <H4>Black Cards</H4>
          {this.renderCards('black')}
        </div>
        <div>
          <H4>White Cards</H4>
          {this.renderCards('white')}
        </div>
      </section>
    );
  }

  render() {
    const { package: pack, packageCards, packageDetails } = this.props;
    const { expanded } = this.state;
    const selected = packageCards.allCards || false;
    return (
      <div>
        <div className={styles.packageRow}>
          <div onClick={() => this.toggleExpanded()} className={styles.arrow}>
            {!expanded ? <DownArrow /> : <UpArrow />}
          </div>
          <div className={styles.packageSelect}>
            <Checkbox selected={selected} onChange={(val) => this.selectPackageChange(val)} />
          </div>
          <div onClick={() => this.toggleExpanded()}>
            <div>
              <H4>{pack.packageName}</H4>
              <div>
                <i>{pack.blackCardCount} black cards </i>
              </div>
              <div>
                <i>{pack.whiteCardCount} white cards </i>
              </div>
            </div>
          </div>
        </div>
        {this.renderExpandedSection()}
      </div>
    );
  }
}

export default DeckPackageRow;
