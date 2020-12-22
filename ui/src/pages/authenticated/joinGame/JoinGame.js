import React from 'react';
import ErrorLabel from 'reactforms/src/form/elements/ErrorLabel';
import Form from 'reactforms/src/form/Form';
import Redirect from '../../../components/Redirect';
import { PrimaryButton } from '../../../components/formelements/buttons/Buttons';
import { TextInput } from '../../../components/formelements/inputs/TextInput';
import PlayerDisplay from '../../../components/PlayerDisplay';
import Urls from '../../../utils/Urls';

const renderCode = (props) => {
  const { gameStarted, gameId, players } = props;
  if (gameStarted) {
    return <Redirect to={Urls.parameterize(Urls.PLAY_GAME, { gameId })} />;
  }
  return (
    <div>
      <h3>
        Joined Game: <span data-id="game-id">{gameId}</span>
      </h3>
      <h2>Waiting for game to start</h2>
      <PlayerDisplay players={players} />
    </div>
  );
};

const renderCreatedGame = (props) => (
  <Form onSubmit={props.onJoinGame}>
    <TextInput data-id="game-code-input" name="code" data-rule-required />
    <div>
      <ErrorLabel for="code" />
    </div>
    <PrimaryButton data-id="join-game" submittable>
      Join Game
    </PrimaryButton>
  </Form>
);

const JoinGame = (props) => (
  <div>
    <h3>Join Game</h3>
    {props.gameId ? renderCode(props) : renderCreatedGame(props)}
  </div>
);

export default JoinGame;
