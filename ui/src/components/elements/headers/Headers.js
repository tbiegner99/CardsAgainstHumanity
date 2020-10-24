import React from 'react';
import combineClasses from 'classnames';
import PropTypes from 'prop-types';
import styles from './headers.css';

export const H5 = (props) => {
  const { className, ...otherProps } = props;
  return (
    <h5 className={combineClasses(styles.header, styles.h5, props.className)} {...otherProps}>
      {props.children}
    </h5>
  );
};
export const H4 = (props) => {
  const { className, ...otherProps } = props;
  return (
    <h4 className={combineClasses(styles.header, styles.h4, props.className)} {...otherProps}>
      {props.children}
    </h4>
  );
};
const propTypes = {
  className: PropTypes.string,
  children: PropTypes.node
};
const defaultProps = {
  className: null,
  children: null
};
/* eslint-disable no-multi-assign */
H5.propTypes = H4.propTypes = propTypes;
H5.defaultProps = H4.defaultProps = defaultProps;
