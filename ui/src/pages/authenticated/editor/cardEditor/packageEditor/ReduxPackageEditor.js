import { connect } from 'react-redux';
import PackageActionCreator from '../../../../../actionCreators/PackagesActionCreator';
import UrlActionCreator from '../../../../../actionCreators/UrlActionCreator';
import Urls from '../../../../../utils/Urls';
import PackageEditor from './PackageEditor';

const mapStateToProps = (state) => {
  const packages = state.packages.store.data.packages.value;
  return {
    packages
  };
};

const mapDispatchToProps = () => ({
  onUrlChange: (url) => UrlActionCreator.changeUrl(url),
  onAddPackage: (data) => PackageActionCreator.createPackage(data),
  onPackageDelete: (pack) => PackageActionCreator.deletePackage(pack.id),
  onPackageEdit: (pack) =>
    UrlActionCreator.changeUrl(Urls.parameterize(Urls.EDIT_PACKAGE, { packageId: pack.id }))
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PackageEditor);
