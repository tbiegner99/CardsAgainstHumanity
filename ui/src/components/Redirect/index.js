import React from 'react';
import UrlActionCreator from '../../actionCreators/UrlActionCreator';
import { Redirect as RouterRedirect } from 'react-router-dom';

class Redirect extends React.Component {
  componentDidMount() {
    const { to } = this.props;
    UrlActionCreator.changeUrl(to);
  }

  render() {
    return null;
  }
}

export default Redirect;
