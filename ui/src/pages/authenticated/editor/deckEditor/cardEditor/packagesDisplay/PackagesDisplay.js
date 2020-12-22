import React from 'react';
import { H2 } from '../../../../../../components/elements/headers/Headers';
import DeckPackageRow from './packageRow/DeckPackageRow';

const getPackageDetails = (deckPackages, includeAll, packageId) => {
  const pack = deckPackages[packageId];
  if (!pack) {
    return {
      allCards: includeAll,
      blackCardInclusions: {},
      whiteCardInclusions: {}
    };
  }
  return Object.assign({}, pack, { allCards: includeAll || pack.allCards });
};

const PackagesDisplay = (props) => {
  const {
    packages,
    deckCards,
    packageDetails,
    onAddCard,
    includeAll,
    onRemoveCard,
    onAddPackage,
    onRemovePackage,
    onLoadPackageDetails
  } = props;
  if (!props.packages) {
    return <H2>Loading...</H2>;
  }
  return packages.map((pack) => (
    <DeckPackageRow
      onAddPackage={onAddPackage}
      onRemovePackage={onRemovePackage}
      onAddCard={onAddCard}
      onRemoveCard={onRemoveCard}
      onLoadPackageDetails={onLoadPackageDetails}
      details={packageDetails[pack.id]}
      packageCards={getPackageDetails(deckCards, includeAll, pack.id)}
      package={pack}
    />
  ));
};

export default PackagesDisplay;
