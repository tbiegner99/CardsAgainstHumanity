import React from 'react';
import combineClasses from 'classnames';
import { CardScroll, Card } from '../../../../../components/CardScroll';
import { WhiteCard } from '../../../../../components/CardsAgainstHumanityCard';
import {
  PrimaryButton,
  SecondaryButton
} from '../../../../../components/formelements/buttons/Buttons';
import styles from './playCardPage.css';

const BLANK_REGEX = /_+/;

const hasBlank = (str) => BLANK_REGEX.test(str);

const isQuestion = (card) => !hasBlank(card.text);

const replaceNextBlank = (str, text) => str.replace(BLANK_REGEX, text);

const answerQuestion = (blackCard, cards) => {
  const answers = cards.map((card) => card.text).join(' ');
  return [blackCard.text, answers].join(' ');
};

const previewPlay = (blackCard, cards) => {
  let str = blackCard.text;

  if (isQuestion(blackCard)) {
    return answerQuestion(blackCard, cards);
  }
  for (let playIndex = 0; playIndex < cards.length && hasBlank(str); playIndex++) {
    str = replaceNextBlank(str, cards[playIndex].text);
  }

  return str;
};

const getNumberOfPlays = (blackCard) => blackCard.numberOfAnswers;

const BlackCardDisplay = (props) => (
  <section className={styles.blackCardDisplay}>
    {previewPlay(props.card, props.currentPlay)}
  </section>
);

const renderHandCard = (card, isSelected, onCardSelect) => (
  <Card>
    <WhiteCard
      className={combineClasses(styles.handCard, { [styles.selectedCard]: isSelected })}
      onClick={isSelected ? null : () => onCardSelect(card)}
      flipped
    >
      {card.text}
    </WhiteCard>
  </Card>
);

const isSelected = (card, selectedCards) => selectedCards.includes(card);

const renderCards = (props) => {
  const mapCard = (card) =>
    renderHandCard(card, isSelected(card, props.selectedCards), props.onCardSelect);
  return props.handCards.map(mapCard);
};

const CardHandDisplay = (props) => (
  <div className={styles.scrollContainer}>
    <CardScroll cardClass={styles.scrollCard} className={styles.handDisplay}>
      {renderCards(props)}
    </CardScroll>
  </div>
);

const renderModalClose = (onClose) => {
  if (!onClose) {
    return null;
  }

  return (
    <button onClick={onClose} className={styles.modalClose}>
      X
    </button>
  );
};

const ConfirmationModal = (props) => (
  <div className={styles.modal}>
    <div className={styles.modalBack} />
    <div className={styles.modalFrame}>
      <div className={styles.modalContent}>
        <header>{renderModalClose(props.onClose)}</header>
        <main>{props.children}</main>
        <footer>
          <div>
            <span className={styles.modalButton}>
              <PrimaryButton onClick={props.onConfirm}>{props.confirmText}</PrimaryButton>
            </span>
            <span className={styles.modalButton}>
              <SecondaryButton onClick={props.onCancel}>{props.cancelText}</SecondaryButton>
            </span>
          </div>
        </footer>
      </div>
    </div>
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
        confirmText="Make Play"
        cancelText="Cancel"
      >
        {previewPlay(this.props.blackCard, selectedCards)}
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

  render() {
    const { blackCard, handCards } = this.props;
    const { cardsToPlay, selectedCards } = this.state;
    if (!blackCard) {
      return <h2>LOADING...</h2>;
    }
    const selectableCards = handCards.filter((card) => selectedCards.indexOf(card) < 0);
    return (
      <div>
        <BlackCardDisplay currentPlay={selectedCards} card={blackCard} />
        <div className={styles.title}>
          <h3>Click {cardsToPlay} Cards to play</h3>
          {this.renderClearButton()}
        </div>
        <CardHandDisplay
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
