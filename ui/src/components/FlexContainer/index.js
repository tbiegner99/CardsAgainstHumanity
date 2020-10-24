import React from 'react';

const FlexContainer = (props) => {
  const {
    flexWrap,
    flexDirection,
    style = {},
    alignItems,
    alignContent,
    justifyContent,
    children,
    ...otherProps
  } = props;
  const elementStyle = {
    flexDirection,
    alignItems,
    justifyContent,
    flexWrap,
    alignContent,
    ...style,
    display: 'flex'
  };
  return (
    <div style={elementStyle} {...otherProps}>
      {children}
    </div>
  );
};

const FlexChild = (props) => {
  const {
    order,
    flexGrow,
    style,
    flexShrink,
    flexBasis,
    alignSelf,
    children,
    ...otherProps
  } = props;
  const elementStyle = {
    order,
    flexGrow,
    flexShrink,
    flexBasis,
    alignSelf,
    ...style
  };
  return (
    <div style={elementStyle} {...otherProps}>
      {children}
    </div>
  );
};

export { FlexContainer, FlexChild };
