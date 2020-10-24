import { connect } from 'react-redux';
import LoginPage from './LoginPage';
import UserActionCreator from '../../actionCreators/UserActionCreator';

const mapStateToProps = (state) => ({
  authenticated: state.user.store.authenticated.value,
  loaded: state.user.store.authenticated.loadState === 'DONE'
});

const mapDispatchToProps = () => ({
  onCheckLogin: (username, password) => UserActionCreator.login(username, password)
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LoginPage);
