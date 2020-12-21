import React from 'react';
import combineClasses from 'classnames';
import PropTypes from 'prop-types';
import styles from './headers.css';

export const H6 = (props) => {
  const { className, ...otherProps } = props;
  return (
    <h6 className={combineClasses(styles.header, styles.h6, props.className)} {...otherProps}>
      {props.children}
    </h6>
  );
};

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

export const H3 = (props) => {
  const { className, ...otherProps } = props;
  return (
    <h3 className={combineClasses(styles.header, styles.h3, props.className)} {...otherProps}>
      {props.children}
    </h3>
  );
};

export const H2 = (props) => {
  const { className, ...otherProps } = props;
  return (
    <h2 className={combineClasses(styles.header, styles.h2, props.className)} {...otherProps}>
      {props.children}
    </h2>
  );
};

export const H1 = (props) => {
  const { className, ...otherProps } = props;
  return (
    <h1 className={combineClasses(styles.header, styles.h1, props.className)} {...otherProps}>
      {props.children}
    </h1>
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
H6.propTypes = H5.propTypes = H4.propTypes = H3.propTypes = H2.propTypes = H1.propTypes = propTypes;
H6.defaultProps = H5.defaultProps = H4.defaultProps = H3.defaultProps = H2.defaultProps = H1.defaultProps = defaultProps;
