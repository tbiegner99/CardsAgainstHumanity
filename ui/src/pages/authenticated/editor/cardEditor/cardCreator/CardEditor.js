import React from 'react';
import { H4, H2 } from '../../../../../components/elements/headers/Headers';
import { TextInput } from '../../../../../components/formelements/inputs/TextInput';
import { PrimaryButton } from '../../../../../components/formelements/buttons/Buttons';
import { AddIcon, UploadIcon } from '../../../../../components/Icons/icons';

import Modal from '../../../../../components/modal/Modal';

import styles from './CardEditor.css';
import CardRow from './cardRow/CardRow';
import CardTypeSelect from '../../../../../components/CardTypeSelect/CardTypeSelect';
import CreateCard from './CreateCard';
import { sortObjectsByFieldIgnoreCase } from '../../../../../utils/SortFunctions';

class CardEditor extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      cardTypeSelected: 'white'
    };
  }

  showCreateModal(updateCard) {
    this.setState({ showCreateModal: true, updateCard });
  }

  showUploadModal() {
    this.setState({ showCreateModal: true, bulk: true });
  }

  closeCreateModal() {
    this.setState({ showCreateModal: false, updateCard: null, bulk: false });
  }

  applyFilter(filter) {
    this.setState({ filter });
  }

  async submitForm(data) {
    const { onAddCard } = this.props;
    try {
      if (onAddCard) {
        await onAddCard(data);
      }
      this.closeCreateModal();
    } catch (err) {
      console.error(err);
    }
  }

  renderCreateModal() {
    const { showCreateModal, updateCard, cardTypeSelected, bulk } = this.state;
    const { package: pack, onCreateCard, onUpdateCard } = this.props;

    if (!showCreateModal) {
      return null;
    }
    const cancel = this.closeCreateModal.bind(this);
    const onSubmit = async (data) => {
      try {
        if (updateCard) {
          await onUpdateCard(pack.packageInfo.id, cardTypeSelected, updateCard.id, data);
        } else {
          await onCreateCard(pack.packageInfo.id, data);
        }
        this.closeCreateModal();
      } catch (err) {
        // do nothing
        console.error(err);
      }
    };
    const title = updateCard
      ? `Updating card ${updateCard.id}`
      : bulk
      ? 'Add Cards from Text file'
      : 'Add Card';

    return (
      <Modal className={styles.createModal} onClose={cancel} title={title}>
        <CreateCard
          bulk={bulk}
          cardToUpdate={updateCard}
          onCreateCard={onSubmit}
          onCancel={cancel}
        />
      </Modal>
    );
  }

  render() {
    const { package: pack, onDeleteCard } = this.props;
    const { cardTypeSelected } = this.state;
    if (!pack) {
      return <H2>Loading...</H2>;
    }
    const onTypeSelect = (value) => this.setState({ cardTypeSelected: value });
    const selectedCards = cardTypeSelected === 'white' ? pack.whiteCards : pack.blackCards;
    const onEdit = (card) => () => this.showCreateModal(card);
    const cards = [...selectedCards].sort(sortObjectsByFieldIgnoreCase('cardText'));
    return (
      <section className={styles.page}>
        <div className={styles.header}>
          <H4 className={styles.title}>Editing package {pack.packageInfo.packageName}</H4>
        </div>
        <div>
          <div>
            <i>{pack.blackCards.length} black cards </i>
          </div>
          <div>
            <i>{pack.whiteCards.length} white cards </i>
          </div>
        </div>
        <div className={styles.packageActions}>
          <TextInput
            onChange={this.applyFilter.bind(this)}
            className={styles.filter}
            placeholder="Filter..."
          />
          <PrimaryButton onClick={() => this.showUploadModal()}>
            <UploadIcon /> Upload
          </PrimaryButton>
          <PrimaryButton onClick={() => this.showCreateModal()}>
            <AddIcon /> Add
          </PrimaryButton>
        </div>
        <CardTypeSelect type={cardTypeSelected} onChange={onTypeSelect} />
        <div className={styles.packagesDisplay}>
          {cards.map((card) => (
            <CardRow
              card={card}
              onEdit={onEdit(card)}
              onDelete={() => onDeleteCard(pack.packageInfo.id, cardTypeSelected, card.id)}
            />
          ))}
        </div>
        {this.renderCreateModal()}
      </section>
    );
  }
}

export default CardEditor;
