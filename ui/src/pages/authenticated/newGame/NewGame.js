import React from 'react';
import { PrimaryButton } from '../../../components/formelements/buttons/Buttons';
import styles from './newGame.css';
import PlayerDisplay from '../../../components/PlayerDisplay';
import { Dropdown, Option } from '../../../components/formelements/inputs/Dropdown';

const renderCode = (props) => {
  const { gameId, onStartGame, gameCode, players, gameDeck } = props;
  return (
    <div>
      <h3>
        Joined Game: <span data-id="game-id">{gameId}</span>
      </h3>
      <h3>
        Game Code: <span data-id="code">{gameCode}</span>
      </h3>

      <h3>
        Selected Deck: <span data-id="deck-name">{gameDeck.name}</span>
      </h3>
      <h3>Waiting for players to join...</h3>
      <section className={styles.playerPane}>
        <PlayerDisplay players={players} />
      </section>
      <div className={styles.startButtonRow}>
        <PrimaryButton data-id="start-game" onClick={() => onStartGame(gameId)}>
          Start Game
        </PrimaryButton>
      </div>
    </div>
  );
};

class NewGame extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      selectedDeck: null
    };
  }

  getValue() {
    const defaultValue = this.props.decks[0].deckId;
    return this.state.selectedDeck || defaultValue;
  }

  onValueChanged(value) {
    this.setState({ selectedDeck: value });
  }

  mapOptions(decks) {
    return decks.map((deck) => <Option value={deck.deckId} text={deck.name} />);
  }

  renderCreatedGame(props) {
    const { decks } = props;
    return (
      <div>
        <div className={styles.selectDeck}>
          Select Deck
          <Dropdown onChange={this.onValueChanged.bind(this)} value={this.getValue()}>
            {this.mapOptions(decks)}
          </Dropdown>
        </div>
        <PrimaryButton data-id="create-game" onClick={() => props.onCreateGame(this.getValue())}>
          Click to Create New Game
        </PrimaryButton>
      </div>
    );
  }

  render() {
    const { gameId, decks } = this.props;
    if (!decks) {
      return <h3>Loading...</h3>;
    }
    return (
      <div className={styles.createGame}>
        <h3>Create a Game</h3>

        {gameId ? renderCode(this.props) : this.renderCreatedGame(this.props)}
      </div>
    );
  }
}

export default NewGame;
