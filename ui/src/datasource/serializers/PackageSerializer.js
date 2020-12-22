class PackageSerializer {
  deserializePackageResponse(pack) {
    return pack;
  }

  serializeCreatePackageData(pack) {
    const { icon, packageName } = pack;
    return {
      packageName,
      official: false,
      icon: icon ? icon.content : null,
      iconType: 'IMAGE'
    };
  }

  serializeBulkCreateCardData(data) {
    const { bulkItems } = data;
    return bulkItems.map((cardText) => ({
      cardText
    }));
  }

  serializeCreateCardData(data) {
    const { cardText } = data;
    return {
      cardText
    };
  }

  serializeUpdateCardData(data) {
    const { cardText } = data;
    return {
      cardText
    };
  }

  deserializePackageDetailsResponse(packageDetails) {
    return packageDetails;
  }

  deserializeCardResponse(card) {
    return card;
  }
}

export default new PackageSerializer();
