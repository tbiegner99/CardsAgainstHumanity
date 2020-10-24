import React from 'react';
import combineClasses from 'classnames';
import Button from 'reactforms/src/form/elements/Button';
import styles from './iconButton.css';

class IconButton extends React.Component {
  render() {
    const { className, title, children, ...otherProps } = this.props;
    const combinedClasses = combineClasses(className, styles.iconButton);
    return (
      <Button className={combinedClasses} {...otherProps}>
        <div className={styles.icon}>{children}</div>
        <div className={styles.title}>{title}</div>
      </Button>
    );
  }
}

export default IconButton;
