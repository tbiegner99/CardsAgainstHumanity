import React from 'react';
import {
  PrimaryButton,
  SecondaryButton
} from '../../../../../components/formelements/buttons/Buttons';
import { CardScroll, Card } from '../../../../../components/CardScroll';
import { WhiteCard } from '../../../../../components/CardsAgainstHumanityCard';
import BlackCardDisplay from '../../BlackCardDisplay';
import styles from '../playCard/playCardPage.css';

const renderCard = (card) => (
  <Card key={card.id} data-id="card">
    <WhiteCard flipped>{card.text}</WhiteCard>
  </Card>
);

const renderPlay = (play, onChooseWinner) => (
  <div data-id="card-play-submission" onClick={() => onChooseWinner(play)}>
    {play.cards.map(renderCard)}
  </div>
);

const renderPlays = (props) => {
  const mapPlay = (play) => renderPlay(play, props.onChooseWinner);
  return props.plays.map(mapPlay);
};

const CardHandDisplay = (props) => (
  <div className={styles.scrollContainer}>
    <CardScroll cardClass={styles.scrollCard} className={styles.handDisplay}>
      {renderPlays(props)}
    </CardScroll>
  </div>
);

class CzarPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      winnerCandidate: null
    };
  }

  getPlaysToReveal() {
    const { numberOfPlays, revealedPlays } = this.props;
    return numberOfPlays - revealedPlays.length;
  }

  renderConfirmButton() {
    const { winnerCandidate } = this.state;
    const { onChooseWinner } = this.props;
    if (!winnerCandidate) {
      return null;
    }
    return (
      <SecondaryButton data-id="confirm-winner" onClick={() => onChooseWinner(winnerCandidate)}>
        Confirm Winner
      </SecondaryButton>
    );
  }

  renderPickWinner() {
    return (
      <div className={styles.title}>
        <h3>Choose a winner</h3>
        {this.renderBlackCard()}
        {this.renderConfirmButton()}
        <div>{this.renderCardPlays(true)}</div>
      </div>
    );
  }

  renderWatingHeader(remainingPlays) {
    return (
      <div className={styles.title}>
        <h3>Waiting for {remainingPlays} players to make play</h3>

        {this.renderBlackCard()}
      </div>
    );
  }

  renderCardPlays(selectable) {
    const onChooseWinner = (play) => this.setState({ winnerCandidate: play });
    const noOp = () => {};
    const chooseWinner = !selectable ? noOp : onChooseWinner;
    return <CardHandDisplay plays={this.props.revealedPlays} onChooseWinner={chooseWinner} />;
  }

  renderRevealedPlays() {
    return (
      <div className={styles.title}>
        <h3>
          Click to reveal <span data-id="plays-to-reveal">{this.getPlaysToReveal()}</span> plays
        </h3>
        {this.renderBlackCard()}
        <div>{this.renderCardPlays(false)}</div>
        <PrimaryButton data-id="reveal-play-button" onClick={this.props.onRevealNext}>
          Reveal Next Play
        </PrimaryButton>
      </div>
    );
  }

  renderWinnerScreen() {
    const { winner } = this.props;
    return (
      <div className={styles.title}>
        <h3>Winning Play from {winner.playerName}</h3>
        {this.renderBlackCard()}
        <SecondaryButton data-id="next-round-button" onClick={this.props.onEndRound}>
          Next Round
        </SecondaryButton>
      </div>
    );
  }

  renderPlays() {
    const { waitingForPlays, winner } = this.props;

    if (winner) {
      return this.renderWinnerScreen();
    } else if (waitingForPlays > 0) {
      return this.renderWatingHeader(waitingForPlays);
    } else if (this.getPlaysToReveal() > 0) {
      return this.renderRevealedPlays();
    }
    return this.renderPickWinner();
  }

  renderBlackCard() {
    const { blackCard, winner } = this.props;
    const { winnerCandidate } = this.state;
    const winningPlay = winner || winnerCandidate || {};
    return <BlackCardDisplay currentPlay={winningPlay.cards || []} card={blackCard} />;
  }

  render() {
    return <div data-id="czar-page">{this.renderPlays()}</div>;
  }
}

export default CzarPage;
