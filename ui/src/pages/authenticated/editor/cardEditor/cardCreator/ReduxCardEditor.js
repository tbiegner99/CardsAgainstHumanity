import { connect } from 'react-redux';
import PackageActionCreator from '../../../../../actionCreators/PackagesActionCreator';
import UrlActionCreator from '../../../../../actionCreators/UrlActionCreator';
import CardEditor from './CardEditor';

const mapStateToProps = (state, ownProps) => {
  const { packageId } = ownProps.match.params;
  const pack = state.packages.store.getPackageDetails(packageId);
  return {
    package: pack
  };
};

const mapDispatchToProps = () => ({
  onUrlChange: (url) => UrlActionCreator.changeUrl(url),
  onCreateCard: (packageId, data) => PackageActionCreator.createCard(packageId, data),
  onDeleteCard: (packageId, cardType, cardId) =>
    PackageActionCreator.deleteCard(packageId, cardType, cardId),
  onUpdateCard: (packageId, type, cardId, data) =>
    PackageActionCreator.updateCard(packageId, type, cardId, data)
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CardEditor);
