import React from 'react';
import { H6 } from '../../../../../../../components/elements/headers/Headers';

export default (props) => {
  const { card, ...otherProps } = props;
  const { cardText } = card;
  return (
    <div {...otherProps}>
      <H6>{cardText}</H6>
    </div>
  );
};
