import React from 'react';
import { CardScroll, Card } from '../../../../components/CardScroll';
import { WhiteCard } from '../../../../components/CardsAgainstHumanityCard';
import BlackCardDisplay from '../BlackCardDisplay';
import {
  PrimaryButton,
  SecondaryButton
} from '../../../../components/formelements/buttons/Buttons';
import styles from '../roundPlay/playCard/playCardPage.css';
import Urls from '../../../../utils/Urls';

const renderHandCard = (card) => (
  <Card key={card.id} data-id="card">
    <WhiteCard className={styles.handCard} flipped>
      {card.text}
    </WhiteCard>
  </Card>
);

const renderCards = (props) => props.handCards.map(renderHandCard);

const CardHandDisplay = (props) => (
  <div className={styles.scrollContainer}>
    <CardScroll cardClass={styles.scrollCard} className={styles.handDisplay}>
      {renderCards(props)}
    </CardScroll>
  </div>
);

class PlayCardPage extends React.Component {
  renderTitle() {
    const { onChangeRoute, gameId } = this.props;
    const playCard = () => onChangeRoute(Urls.parameterize(Urls.PLAY_GAME, { gameId }));
    return (
      <div>
        <h3>Your current hand:</h3>
        <SecondaryButton onClick={playCard}>Click to play or judge.</SecondaryButton>
      </div>
    );
  }

  render() {
    const { blackCard, handCards } = this.props;
    if (!blackCard) {
      return <h3>LOADING...</h3>;
    }
    return (
      <div>
        <div className={styles.title}>{this.renderTitle()}</div>
        <BlackCardDisplay card={blackCard} currentPlay={[]} />

        <CardHandDisplay handCards={handCards} />
      </div>
    );
  }
}

export default PlayCardPage;
