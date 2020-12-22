import { connect } from 'react-redux';
import AudiencePage from './AudiencePage';
import GameActionCreator from '../../actionCreators/GameEventActionCreator';

const mapStateToProps = () => ({});

const mapDispatchToProps = () => ({
  onJoinGame: (data) => GameActionCreator.audienceJoinGame(data.code)
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AudiencePage);
