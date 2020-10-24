import { connect } from 'react-redux';
import UrlActionCreator from '../../actionCreators/UrlActionCreator';
import AuthenticatedPage from './AuthenticatedPage';

const mapStateToProps = (state) => ({
  authenticated: state.user.store.authenticated.value,
  loaded: state.user.store.authenticated.loadState === 'DONE'
});

const mapDispatchToProps = () => ({
  onUrlChange: (url) => UrlActionCreator.changeUrl(url)
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AuthenticatedPage);
