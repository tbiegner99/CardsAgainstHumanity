import React from 'react';
import combineClasses from 'classnames';
import { CardScroll, Card } from '../../../../../components/CardScroll';
import { WhiteCard } from '../../../../../components/CardsAgainstHumanityCard';
import { SecondaryButton } from '../../../../../components/formelements/buttons/Buttons';
import BlackCardDisplay from '../../BlackCardDisplay';
import CardUtilities from '../../../../../utils/CardUtilities';
import ConfirmationModal from '../../../../../components/modal/ConfirmationModal';
import styles from './playCardPage.css';

const getNumberOfPlays = (blackCard) => blackCard.numberOfAnswers;

const renderHandCard = (card, selectable, onCardSelect, isSelected) => (
  <Card key={card.id} data-id="card">
    <WhiteCard
      data-id={!selectable ? 'not-selectable' : 'selectable'}
      className={combineClasses(styles.handCard, { [styles.selectedCard]: isSelected })}
      onClick={!selectable ? null : () => onCardSelect(card)}
      flipped
    >
      {card.text}
    </WhiteCard>
  </Card>
);

const isCardSelected = (card, selectedCards) => selectedCards.includes(card);

const renderCards = (props) => {
  const mapCard = (card) => {
    const isSelected = isCardSelected(card, props.selectedCards);
    const selectable = props.canPlayCard && !props.isSelected;
    return renderHandCard(card, selectable, props.onCardSelect, isSelected);
  };
  return props.handCards.map(mapCard);
};

const CardHandDisplay = (props) => (
  <div className={styles.scrollContainer}>
    <CardScroll cardClass={styles.scrollCard} className={styles.handDisplay}>
      {renderCards(props)}
    </CardScroll>
  </div>
);

class PlayCardPage extends React.Component {
  constructor(props) {
    super(props);
    const { blackCard } = props;
    this.state = {
      showCardConfirmation: false,
      selectedCards: props.currentPlay || [],
      cardsToPlay: blackCard ? getNumberOfPlays(blackCard) : null
    };
  }

  cancelSelection() {
    this.setState({
      showCardConfirmation: false,
      selectedCards: this.props.currentPlay || [],
      cardsToPlay: getNumberOfPlays(this.props.blackCard)
    });
  }

  async selectCard() {
    await this.props.onCardsSelected(this.state.selectedCards);
    this.cancelSelection();
  }

  canPlayCard(selectedCards) {
    return selectedCards.length < getNumberOfPlays(this.props.blackCard);
  }

  addCardToPlay(card) {
    const { selectedCards } = this.state;
    if (!this.canPlayCard(selectedCards)) {
      return;
    }
    const nextPlay = selectedCards.concat([card]);
    this.setState({
      selectedCards: nextPlay,
      cardsToPlay: getNumberOfPlays(this.props.blackCard) - nextPlay.length
    });

    if (!this.canPlayCard(nextPlay)) {
      this.confirmPlay();
    }
  }

  confirmPlay() {
    this.setState({
      showCardConfirmation: true
    });
  }

  renderCardPlayModal() {
    const { showCardConfirmation, selectedCards } = this.state;

    if (!showCardConfirmation) return null;

    return (
      <ConfirmationModal
        onConfirm={() => this.selectCard()}
        onCancel={() => this.cancelSelection()}
        confirmButtonClass="make-play-button"
        confirmText="Make Play"
        cancelButtonClass="cancel-play-button"
        cancelText="Cancel"
      >
        {CardUtilities.previewPlay(this.props.blackCard, selectedCards)}
      </ConfirmationModal>
    );
  }

  renderClearButton() {
    const { selectedCards } = this.state;
    if (selectedCards.length === 0) {
      return null;
    }
    return (
      <div>
        <SecondaryButton onClick={() => this.cancelSelection()}>Clear Selection</SecondaryButton>
      </div>
    );
  }

  renderTitle() {
    const { myPlay, canPlayCard } = this.props;
    const { cardsToPlay } = this.state;
    if (myPlay) {
      return <h3>You have already made your play for this round.</h3>;
    } else if (!canPlayCard) {
      return <h3>You may not make selection for this round.</h3>;
    }
    return (
      <h3>
        Click <span data-id="cards-to-play">{cardsToPlay}</span> Cards to play
      </h3>
    );
  }

  render() {
    const { blackCard, handCards, myPlay, canPlayCard } = this.props;
    const { selectedCards } = this.state;
    const selectableCards = handCards.filter((card) => selectedCards.indexOf(card) < 0);
    let currentPlay = selectedCards;
    if (!selectedCards.length && myPlay) {
      currentPlay = myPlay.cards || [];
    }
    return (
      <div>
        <div className={styles.title}>
          {this.renderTitle()}
          {this.renderClearButton()}
        </div>
        <BlackCardDisplay currentPlay={currentPlay} card={blackCard} />
        <CardHandDisplay
          canPlayCard={canPlayCard}
          handCards={selectableCards}
          selectedCards={selectedCards}
          onCardSelect={(card) => this.addCardToPlay(card)}
        />
        {this.renderCardPlayModal()}
      </div>
    );
  }
}

export default PlayCardPage;
