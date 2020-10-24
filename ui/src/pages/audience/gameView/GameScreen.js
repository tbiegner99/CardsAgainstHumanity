import React from 'react';
import combineClasses from 'classnames';
import { BlackCard, WhiteCard } from '../../../components/CardsAgainstHumanityCard';
import PlayerList from './playerList/PlayerList';
import styles from './gameScreen.css';

const SPACE_SIZE_IN_WINDOW_WIDTH_PCT = 0.01;
const ROUND_CONTENT_SIZE_PCT = 0.8;
const CARD_RATIO = 210 / 300;
const ROW_SPACING_AS_PCT = 0.02;
const CARD_SPACE_HEIGHT_AS_PCT = 0.93; // -7% for header
const getCardWidthFromHeight = (height) => CARD_RATIO * height;
const getRoundContentSize = () => ROUND_CONTENT_SIZE_PCT * window.innerWidth;
const getCardSpaceHeight = () => CARD_SPACE_HEIGHT_AS_PCT * window.innerHeight;
const getVerticalSpacing = () => ROW_SPACING_AS_PCT * window.innerHeight;
const getSpaceSize = () => SPACE_SIZE_IN_WINDOW_WIDTH_PCT * getRoundContentSize();

class GameScreen extends React.Component {
  constructor(props) {
    super(props);
    this.state = this.computeState();
  }

  componentDidMount() {
    this.updateEvent = () => this.updateWindowSize();
    window.addEventListener('resize', this.updateEvent);
  }

  componentWillUnmount() {
    window.removeEventListener('resize', this.updateEvent);
  }

  componentDidUpdate(prevProps) {
    if (prevProps !== this.props) {
      this.setState(this.computeState());
    }
  }

  updateWindowSize() {
    this.setState(this.computeState);
  }

  computeState() {
    const {
      blackCard: { numberOfAnswers },
      revealedCards,
      winningPlayId
    } = this.props.roundData;

    let rows = 0;
    let cardsFit = false;
    let newCardHeight;
    let cardWidth;
    let maxAnswersPerRow;

    while (!cardsFit) {
      rows++;
      const totalRows = rows + 1; // for blackCard;
      const verticalSpaces = totalRows - 1;
      const totalSpace = getCardSpaceHeight();
      const rowSpace = totalSpace - verticalSpaces * getVerticalSpacing();
      newCardHeight = rowSpace / totalRows;
      const rowWidth = getRoundContentSize();
      cardWidth = getCardWidthFromHeight(newCardHeight);
      const horizontalSpace = getSpaceSize();
      const spacePerPlay = numberOfAnswers * cardWidth + horizontalSpace;
      maxAnswersPerRow = Math.floor(rowWidth / spacePerPlay);
      const neededRows = revealedCards.length / maxAnswersPerRow;

      cardsFit = neededRows <= rows;
    }

    const answersPerRow = Math.ceil(revealedCards.length / rows);
    const hasWinningPlay = revealedCards.filter((item) => item.playId === winningPlayId).length > 0;

    return {
      cardWidth,
      cardHeight: newCardHeight,
      answersPerRow,
      hasWinningPlay,
      numberOfRows: rows
    };
  }

  getCardStyle(isWinningPlay, cardIndex) {
    if (isWinningPlay) {
      return this.getWinningPlayStyle(cardIndex);
    }
    const { cardHeight } = this.state;
    return { height: `${cardHeight}px` };
  }

  getWinningPlayStyle(cardIndex) {
    const {
      blackCard: { numberOfAnswers }
    } = this.props.roundData;
    const rowSize = getRoundContentSize();
    const numberOfCards = numberOfAnswers + 1;
    const spaces = numberOfCards * getSpaceSize();
    const cardWidth = (rowSize - spaces) / numberOfCards;
    const left = getSpaceSize() + (cardWidth + getSpaceSize()) * cardIndex;
    return {
      width: `${cardWidth}px`,
      top: `calc(50vh - 250px)`,
      left,
      position: 'absolute'
    };
  }

  renderBlankPlay(i, numberOfAnswers) {
    const answers = [];
    for (let answer = 0; answer < numberOfAnswers; answer++) {
      const key = i * numberOfAnswers + answer;
      const style = {
        ...this.getCardStyle(false),
        left: `${(i * numberOfAnswers + answer) * 10}px`
      };
      answers.push(<WhiteCard key={key} className={styles.playedCard} style={style} />);
    }
    return answers;
  }

  renderPlayedCards() {
    const { numberOfPlays } = this.props.roundData;
    const cards = [];
    for (let i = 0; i < numberOfPlays; i++) {
      cards.push(this.renderRevealedPlay(i));
    }

    return cards;
  }

  renderCardPlay(i, revealedIndex, cardPlay, winningPlayId, numberOfAnswers) {
    const { cardWidth, cardHeight, answersPerRow } = this.state;
    const isWinningPlay = cardPlay.playId === winningPlayId;
    const { cards } = cardPlay;
    const className = combineClasses(styles.playedCard, {
      [styles.winningCard]: isWinningPlay
    });

    const row = Math.floor(revealedIndex / answersPerRow);
    const verticalSpace = row * getVerticalSpacing();
    const rowIndex = revealedIndex % answersPerRow;
    const spaceSize = rowIndex > 0 ? getSpaceSize() : 0;
    const contentSpace =
      cardWidth * numberOfAnswers * answersPerRow + (answersPerRow - 1) * getSpaceSize();
    const offset = (getRoundContentSize() - contentSpace) / 2;
    const answers = [];
    for (let answerIndex = 0; answerIndex < numberOfAnswers; answerIndex++) {
      const key = i * numberOfAnswers + answerIndex;
      const left = rowIndex * numberOfAnswers + answerIndex;
      const style = {
        left: `${left * cardWidth + rowIndex * spaceSize + offset}px`,
        top: `calc(100vh - ${cardHeight * (row + 1) + verticalSpace}px)`,
        ...this.getCardStyle(isWinningPlay, answerIndex + 1)
      };

      const card = cards[answerIndex];
      answers.push(
        <WhiteCard flipped key={key} className={className} style={style}>
          {card.text}
        </WhiteCard>
      );
    }
    return answers;
  }

  renderRevealedPlay(i) {
    const { revealedCards, numberOfPlays, winningPlayId, blackCard } = this.props.roundData;
    const revealedIndex = numberOfPlays - 1 - i;

    if (revealedIndex >= revealedCards.length) {
      return this.renderBlankPlay(i, blackCard.numberOfAnswers);
    }
    const cardData = revealedCards[revealedIndex];
    return this.renderCardPlay(
      i,
      revealedIndex,
      cardData,
      winningPlayId,
      blackCard.numberOfAnswers
    );
  }

  render() {
    const { czarOrder, roundData } = this.props;
    const { blackCard } = roundData;
    const { hasWinningPlay, cardWidth } = this.state;

    const blackCardStyle = {
      left: `calc(50% - ${cardWidth / 2}px)`,
      ...this.getCardStyle(hasWinningPlay, 0)
    };

    return (
      <section className={styles.gameScreen}>
        <div className={styles.roundContent}>
          <div className={styles.czarHeader} />
          <div
            className={combineClasses({ [styles.winningScreen]: hasWinningPlay }, styles.screen)}
          />
          <BlackCard
            flipped
            className={combineClasses(styles.card, { [styles.winningCard]: hasWinningPlay })}
            style={blackCardStyle}
          >
            {blackCard.text}
          </BlackCard>
          {this.renderPlayedCards()}
        </div>
        <div className={styles.playersList}>
          <PlayerList
            players={czarOrder}
            width={(1 - ROUND_CONTENT_SIZE_PCT) * window.innerWidth}
            height={window.innerHeight}
          />
        </div>
      </section>
    );
  }
}

export default GameScreen;
