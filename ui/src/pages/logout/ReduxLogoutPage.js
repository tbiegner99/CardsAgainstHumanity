import { connect } from 'react-redux';
import LogoutPage from './LogoutPage';
import UserActionCreator from '../../actionCreators/UserActionCreator';

const mapStateToProps = () => ({});

const mapDispatchToProps = () => ({
  onLogout: () => UserActionCreator.logout()
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LogoutPage);
