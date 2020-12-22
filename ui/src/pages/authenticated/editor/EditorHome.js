import React from 'react';
import IconButton from '../../../components/formelements/buttons/IconButton';
import { DeckIcon, CardIcon } from '../../../components/Icons/icons';
import Urls from '../../../utils/Urls';
import { H2 } from '../../../components/elements/headers/Headers';

import styles from './home.css';

const EditorHome = (props) => {
  const triggerUrlChange = (url) => () => {
    props.onUrlChange(url);
  };

  return (
    <div>
      <H2 className={styles.title}>Choose Option</H2>
      <section className={styles.buttonsPanel}>
        <div>
          <IconButton
            data-id="edit-package-option"
            onClick={triggerUrlChange(Urls.DESIGN_PACKAGES)}
            title="Manage Cards"
          >
            <CardIcon />
          </IconButton>
        </div>
        <div>
          <IconButton
            data-id="edit-deck-option"
            onClick={triggerUrlChange(Urls.DESIGN_DECK)}
            title="Manage Decks"
          >
            <DeckIcon />
          </IconButton>
        </div>
      </section>
    </div>
  );
};

export default EditorHome;
