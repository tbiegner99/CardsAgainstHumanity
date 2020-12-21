import { connect } from 'react-redux';
import LoginPage from './LoginPage';
import UserActionCreator from '../../actionCreators/UserActionCreator';

const mapStateToProps = (state) => ({
  authenticated: state.user.store.authenticated.value,
  loaded: state.user.store.authenticated.loadState === 'DONE'
});

const mapDispatchToProps = () => ({
  onCheckLogin: (username, password, redirectUrl) =>
    UserActionCreator.login(username, password, redirectUrl)
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LoginPage);
