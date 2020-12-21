import AbstractReducingStore from './AbstractReducingStore';
import StoreField from './StoreField';
import GameActions from '../actions/GameActions';

class GameRoundStore extends AbstractReducingStore {
  constructor() {
    super();
    this.data = {
      czarName: new StoreField('czarName', null),
      isCzar: new StoreField('isCzar', null),
      blackCard: new StoreField('blackCard', null),
      isOver: new StoreField('isOver', null),
      winner: new StoreField('winner', null),
      cardsInPlay: new StoreField('cardsInPlay', null),
      allCardsIn: new StoreField('allCardsIn', null),
      waitingForPlays: new StoreField('waitingForPlays', null),
      revealedPlays: new StoreField('revealedPlays', null),
      roundOver: new StoreField('roundOver', null),
      canPlayCard: new StoreField('canPlayCard', null),
      roundId: new StoreField('roundId', null),
      numberOfPlays: new StoreField('numberOfPlays', null),
      myPlay: new StoreField('myPlay', null)
    };
  }

  get isCzar() {
    return this.data.isCzar;
  }

  get blackCard() {
    return this.data.blackCard;
  }

  get isOver() {
    return this.data.isOver;
  }

  get winner() {
    return this.data.winner;
  }

  get czarName() {
    return this.data.czarName;
  }

  get cardsInPlay() {
    return this.data.cardsInPlay;
  }

  get allCardsIn() {
    return this.data.allCardsIn;
  }

  get revealedPlays() {
    return this.data.revealedPlays;
  }

  get waitingForPlays() {
    return this.data.waitingForPlays;
  }

  get roundOver() {
    return this.data.roundOver;
  }

  get canPlayCard() {
    return this.data.canPlayCard;
  }

  get roundId() {
    return this.data.roundId;
  }

  get numberOfPlays() {
    return this.data.numberOfPlays;
  }

  get myPlay() {
    return this.data.myPlay;
  }

  setRoundData(round) {
    if (round) {
      const {
        czarIsYou,
        czar,
        cardsInPlay,
        allCardsIn,
        blackCard,
        isOver,
        winner,
        revealedPlays,
        waitingForPlays,
        roundOver,
        canPlayCard,
        numberOfPlays,
        roundId,
        myPlay
      } = round;
      this.data.roundId.value = roundId;
      this.data.isOver.value = isOver;
      this.data.isCzar.value = czarIsYou;
      this.data.cardsInPlay.value = cardsInPlay;
      this.data.czarName.value = czar;
      this.data.allCardsIn.value = allCardsIn;
      this.data.blackCard.value = blackCard;
      this.data.winner.value = winner;
      this.data.waitingForPlays.value = waitingForPlays;
      this.data.roundOver.value = roundOver;
      this.data.revealedPlays.value = revealedPlays;
      this.data.canPlayCard.value = canPlayCard;
      this.data.numberOfPlays.value = numberOfPlays;
      this.data.myPlay.value = myPlay;
    }
  }

  handleEvent(action) {
    switch (action.type) {
      case GameActions.GAME_STATUS:
        this.setRoundData(action.data.currentRound);
        break;
      default:
        return false;
    }
    return true;
  }
}

export default new GameRoundStore();
