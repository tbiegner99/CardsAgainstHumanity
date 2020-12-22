import React from 'react';
import { H4 } from '../../../../../../components/elements/headers/Headers';

export default (props) => {
  const { package: pack, ...otherProps } = props;
  const { packageName, blackCardCount, whiteCardCount } = pack;
  return (
    <div {...otherProps}>
      <H4>{packageName}</H4>
      <div>
        <i>{blackCardCount} black cards </i>
      </div>
      <div>
        <i>{whiteCardCount} white cards </i>
      </div>
    </div>
  );
};
