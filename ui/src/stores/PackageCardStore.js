import AbstractReducingStore from './AbstractReducingStore';
import StoreField from './StoreField';
import PackageActionCreator from '../actionCreators/PackagesActionCreator';
import PackageActions from '../actions/PackageActions';

class PackageCardStore extends AbstractReducingStore {
  constructor() {
    super();
    this.data = {
      packageDetails: {},
      packages: new StoreField('packages', null, PackageActionCreator.loadMyPackages)
    };
  }

  getLoadedPackageDetails() {
    const loaded = {};
    Object.entries(this.data.packageDetails).forEach(([key, value]) => {
      loaded[key] = value.value;
    });
    return loaded;
  }

  setupFieldFor(packageId) {
    if (!this.data.packageDetails[packageId]) {
      this.data.packageDetails[packageId] = new StoreField(
        packageId,
        null,
        PackageActionCreator.loadPackageDetails.bind(PackageActionCreator, packageId)
      );
    }
  }

  getPackageDetails(packageId) {
    this.setupFieldFor(packageId);
    return this.data.packageDetails[packageId].value;
  }

  setPackageDetails(packageId, details) {
    this.setupFieldFor(packageId);
    this.data.packageDetails[packageId].value = details;
  }

  get packages() {
    return this.data.packages;
  }

  handleEvent(action) {
    const { type, data } = action;
    switch (type) {
      case PackageActions.PACKAGE_DETAILS_LOADED:
        this.setPackageDetails(data.packageId, data.details);
        break;
      case PackageActions.PACKAGES_LOADED:
        this.data.packages.value = action.data;
        break;
      default:
        return false;
    }
    return true;
  }
}

export default new PackageCardStore();
