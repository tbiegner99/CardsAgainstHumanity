import React from 'react';
import { H4 } from '../../../../../components/elements/headers/Headers';

export default (props) => {
  const { deck, ...otherProps } = props;
  const { name, blackCardCount, whiteCardCount } = deck;
  return (
    <div {...otherProps}>
      <H4>{name}</H4>
      <div>
        <i>{blackCardCount} black cards </i>
      </div>
      <div>
        <i>{whiteCardCount} white cards </i>
      </div>
    </div>
  );
};
