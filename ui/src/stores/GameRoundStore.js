import AbstractReducingStore from './AbstractReducingStore';
import StoreField from './StoreField';
import GameActions from '../actions/GameActions';
import GameStates from '../utils/GameStates';

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
      allCardsIn: new StoreField('allCardsIn', null)
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

  setRoundData(round) {
    if (round) {
      const { czarIsYou, czar, cardsInPlay, allCardsIn, blackCard, isOver, winner } = round;
      this.data.isOver.value = isOver;
      this.data.isCzar.value = czarIsYou;
      this.data.cardsInPlay.value = cardsInPlay;
      this.data.czarName.value = czar;
      this.data.allCardsIn.value = allCardsIn;
      this.data.blackCard.value = blackCard;
      this.data.winner.value = winner;
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
