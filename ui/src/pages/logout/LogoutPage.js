import React from 'react';

class LoginPage extends React.Component {
  componentDidMount() {
    const { onLogout } = this.props;
    if (typeof onLogout === 'function') {
      onLogout();
    }
  }

  render() {
    return null;
  }
}

export default LoginPage;
