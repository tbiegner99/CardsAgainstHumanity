import React from 'react';
import IconButton from '../../components/formelements/buttons/IconButton';
import styles from './home.css';
import Icons from '../../components/Icons/icons';
import Urls from '../../utils/Urls';

const AuthenticatedHome = (props) => {
  const triggerUrlChange = (url) => () => {
    props.onUrlChange(url);
  };

  return (
    <div>
      <h2 className={styles.title}>Choose Option</h2>
      <section className={styles.buttonsPanel}>
        <div>
          <IconButton data-id="new-game" onClick={triggerUrlChange(Urls.NEW_GAME)} title="New Game">
            {Icons.NewGame}
          </IconButton>
        </div>
        <div>
          <IconButton
            data-id="join-menu-option"
            onClick={triggerUrlChange(Urls.JOIN_GAME)}
            title="Join Game"
          >
            {Icons.JoinGame}
          </IconButton>
        </div>
        <div>
          <IconButton onClick={triggerUrlChange(Urls.DESIGN)} title="Deck Design">
            {Icons.DeckDesign}
          </IconButton>
        </div>
        <div>
          <IconButton onClick={triggerUrlChange(Urls.STATS)} title="Stats">
            {Icons.Stats}
          </IconButton>
        </div>
      </section>
    </div>
  );
};

export default AuthenticatedHome;
