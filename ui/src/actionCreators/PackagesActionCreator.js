import autoBind from 'auto-bind-inheritance';
import BaseActionCreator from './BaseActionCreator';
import PackagesDatasource from '../datasource/PackageDatasource';
import PackageActions from '../actions/PackageActions';

class PackagesActionCreator extends BaseActionCreator {
  constructor(datasource) {
    super();
    this.datasource = datasource;
    autoBind(this);
  }

  async loadPackageDetails(packageId) {
    try {
      const details = await this.datasource.loadPackageDetails(packageId);
      this.dispatch({
        type: PackageActions.PACKAGE_DETAILS_LOADED,
        data: {
          packageId,
          details
        }
      });
    } catch (err) {
      this.dispatch({
        type: PackageActions.Errors.LOAD_PACKAGE_DETAILS_FAILURE
      });
      console.error(err);
    }
  }

  async loadMyPackages() {
    try {
      const decks = await this.datasource.loadMyPackages();
      this.dispatch({
        type: PackageActions.PACKAGES_LOADED,
        data: decks
      });
    } catch (err) {
      this.dispatch({
        type: PackageActions.Errors.LOAD_PACKAGE_FAILURE
      });
      console.error(err);
    }
  }

  async loadUseablePackages() {
    try {
      const decks = await this.datasource.loadUseablePackages();
      this.dispatch({
        type: PackageActions.DECK_PACKAGES_LOADED,
        data: decks
      });
    } catch (err) {
      this.dispatch({
        type: PackageActions.Errors.LOAD_DECK_PACKAGE_FAILURE
      });
      console.error(err);
    }
  }

  async createPackage(data) {
    try {
      const packageData = await this.datasource.createPackage(data);
      this.dispatch({
        type: PackageActions.PACKAGE_CREATED,
        data: packageData
      });
      await this.loadMyPackages();
    } catch (err) {
      this.dispatch({
        type: PackageActions.Errors.CREATE_PACKAGE_FAILURE
      });
      console.error(err);
    }
  }

  async deletePackage(packageId) {
    try {
      await this.datasource.deletePackage(packageId);
      this.dispatch({
        type: PackageActions.PACKAGE_DELETED,
        data: packageId
      });
      await this.loadMyPackages();
    } catch (err) {
      this.dispatch({
        type: PackageActions.Errors.DELETE_PACKAGE_FAILURE
      });
    }
  }

  async createCardsBulk(packageId, data) {
    try {
      const cardData = await this.datasource.createCardsBulk(packageId, data);
      this.dispatch({
        type: PackageActions.CARD_CREATED,
        data: cardData
      });
      await this.loadPackageDetails(packageId);
    } catch (err) {
      this.dispatch({
        type: PackageActions.Errors.CREATE_CARD_FAILURE
      });
      throw err;
    }
  }

  async createCard(packageId, data) {
    try {
      if (data.isBulk) {
        this.createCardsBulk(packageId, data);
        return;
      }
      const cardData = await this.datasource.createCard(packageId, data);
      this.dispatch({
        type: PackageActions.CARD_CREATED,
        data: cardData
      });
      await this.loadPackageDetails(packageId);
    } catch (err) {
      this.dispatch({
        type: PackageActions.Errors.CREATE_CARD_FAILURE
      });
      throw err;
    }
  }

  async updateCard(packageId, cardType, cardId, data) {
    try {
      await this.datasource.updateCard(packageId, cardType, cardId, data);
      this.dispatch({
        type: PackageActions.CARD_UPDATED,
        data: cardId
      });
      await this.loadPackageDetails(packageId);
    } catch (err) {
      this.dispatch({
        type: PackageActions.Errors.UPDATE_CARD_FAILURE
      });
      throw err;
    }
  }

  async deleteCard(packageId, cardType, cardId) {
    try {
      await this.datasource.deleteCard(packageId, cardType, cardId);
      this.dispatch({
        type: PackageActions.CARD_CREATED,
        data: cardId
      });
      await this.loadPackageDetails(packageId);
    } catch (err) {
      this.dispatch({
        type: PackageActions.Errors.DELETE_CARD_FAILURE
      });
      throw err;
    }
  }
}

export default new PackagesActionCreator(PackagesDatasource);
