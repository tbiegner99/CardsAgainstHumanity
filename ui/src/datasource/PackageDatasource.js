import BaseDatasource from './BaseDatasource';
import PackageSerializer from './serializers/PackageSerializer';

const PACKAGES_URL = '/packages/me';
const USEABLE_PACKAGES_URL = '/packages';
const CREATE_PACKAGE_URL = '/packages';
const getUrlPartFromType = (type) => (type === 'white' ? 'whiteCards' : 'blackCards');
const getDeleteUrl = (packageId) => `/packages/${packageId}`;
const getPackageDetailsUrl = (packageId) => `/packages/${packageId}`;
const getCreateCardUrl = (packageId, type) => `/packages/${packageId}/${getUrlPartFromType(type)}`;
const getBulkCreateCardUrl = (packageId, type) => `${getCreateCardUrl(packageId, type)}/bulk`;
const getEditCardUrl = (packageId, type, cardId) =>
  `/packages/${packageId}/${getUrlPartFromType(type)}/${cardId}`;

class PackageDatasource extends BaseDatasource {
  async loadMyPackages() {
    const url = this.constructUrl(PACKAGES_URL);
    const response = await this.client.get(url);
    return response.data.map((pack) => PackageSerializer.deserializePackageResponse(pack));
  }

  async loadUseablePackages() {
    const url = this.constructUrl(USEABLE_PACKAGES_URL);
    const response = await this.client.get(url);
    return response.data.map((pack) => PackageSerializer.deserializePackageResponse(pack));
  }

  async loadPackageDetails(packageId) {
    const url = this.constructUrl(getPackageDetailsUrl(packageId));
    const response = await this.client.get(url);
    return PackageSerializer.deserializePackageDetailsResponse(response.data);
  }

  async createPackage(data) {
    const url = this.constructUrl(CREATE_PACKAGE_URL);
    const body = PackageSerializer.serializeCreatePackageData(data);
    const response = await this.client.post(url, body);
    return PackageSerializer.deserializePackageResponse(response.data);
  }

  async createCard(packageId, data) {
    const url = this.constructUrl(getCreateCardUrl(packageId, data.type));
    const body = PackageSerializer.serializeCreateCardData(data);
    const response = await this.client.post(url, body);
    return PackageSerializer.deserializeCardResponse(response.data);
  }

  async createCardsBulk(packageId, data) {
    const url = this.constructUrl(getBulkCreateCardUrl(packageId, data.type));
    const body = PackageSerializer.serializeBulkCreateCardData(data);
    await this.client.post(url, body);
  }

  async updateCard(packageId, cardType, cardId, data) {
    const url = this.constructUrl(getEditCardUrl(packageId, cardType, cardId));
    const body = PackageSerializer.serializeUpdateCardData(data);
    const response = await this.client.put(url, body);
    return PackageSerializer.deserializeCardResponse(response.data);
  }

  async deletePackage(packageId) {
    const url = this.constructUrl(getDeleteUrl(packageId));
    await this.client.delete(url);
  }

  async deleteCard(packageId, type, cardId) {
    const url = this.constructUrl(getEditCardUrl(packageId, type, cardId));
    await this.client.delete(url);
  }
}

export default new PackageDatasource();
