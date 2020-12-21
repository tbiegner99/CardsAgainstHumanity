import React from 'react';
import { H4, H2 } from '../../../../../components/elements/headers/Headers';
import { TextInput } from '../../../../../components/formelements/inputs/TextInput';
import DeckPackages from './packagesDisplay/PackagesDisplay';
import { sortObjectsByFieldIgnoreCase } from '../../../../../utils/SortFunctions';
import styles from './DeckCardEditor.css';

class DeckCardEditor extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      filter: ''
    };
  }

  updateFilter(filter) {
    this.setState({ filter });
  }

  applyFilter(packages) {
    const { filter } = this.state;
    if (!packages) {
      return null;
    }
    const sortedPackages = [...packages].sort(sortObjectsByFieldIgnoreCase('packageName'));
    if (!filter) {
      return sortedPackages;
    }

    return sortedPackages.filter(
      (pack) => pack.packageName.toLowerCase().indexOf(filter.toLowerCase()) >= 0
    );
  }

  render() {
    const {
      deck,
      deckId,
      onAddPackage,
      onRemovePackage,
      onAddCard,
      onRemoveCard,
      packages,
      packageDetails,
      onLoadPackageDetails
    } = this.props;
    if (!deck) {
      return <H4>Loading...</H4>;
    }
    const filteredPackages = this.applyFilter(packages);
    return (
      <section className={styles.page}>
        <div className={styles.header}>
          <H4 className={styles.title}>Editing deck {deck.name}</H4>
        </div>
        <div>
          <div>
            <i>{deck.blackCardCount} black cards </i>
          </div>
          <div>
            <i>{deck.whiteCardCount} white cards </i>
          </div>
        </div>
        <div className={styles.packageActions}>
          <TextInput
            onChange={(value) => this.updateFilter(value)}
            className={styles.filter}
            placeholder="Filter..."
          />
        </div>
        <div className={styles.packagesDisplay}>
          <DeckPackages
            onAddPackage={(pack) => onAddPackage(deckId, pack)}
            onRemovePackage={(pack) => onRemovePackage(deckId, pack)}
            onAddCard={(pack, type, cardId) => onAddCard(deckId, pack, type, cardId)}
            onRemoveCard={(type, cardId) => onRemoveCard(deckId, type, cardId)}
            onLoadPackageDetails={onLoadPackageDetails}
            packageDetails={packageDetails}
            deckCards={deck.packages}
            packages={filteredPackages}
          />
        </div>
      </section>
    );
  }
}

export default DeckCardEditor;
