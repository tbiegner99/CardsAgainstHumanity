import { connect } from 'react-redux';
import LoginPage from './RegistrationPage';
import UserActionCreator from '../../actionCreators/UserActionCreator';

const mapStateToProps = () => ({});

const mapDispatchToProps = () => ({
  onSubmitRegistration: (data) => UserActionCreator.createUser(data)
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LoginPage);
