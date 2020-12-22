import React from 'react';
import combineClasses from 'classnames';
import Form from 'reactforms/src/form/Form';
import FileUploader from 'reactforms/src/form/elements/FileUploader';
import ErrorLabel from 'reactforms/src/form/elements/ErrorLabel';
import { H4, H2, H6 } from '../../../../components/elements/headers/Headers';
import { TextInput } from '../../../../components/formelements/inputs/TextInput';
import {
  PrimaryButton,
  SecondaryButton
} from '../../../../components/formelements/buttons/Buttons';
import { AddIcon } from '../../../../components/Icons/icons';

import Modal from '../../../../components/modal/Modal';
import DeckRow from './deckRow/DeckRow';

import styles from './deckEditor.css';

class DeckEditor extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  showCreateModal() {
    this.setState({ showCreateModal: true });
  }

  closeCreateModal() {
    this.setState({ showCreateModal: false });
  }

  applyFilter(filter) {
    this.setState({ filter });
  }

  promptFile() {
    this.refs.iconUpload.prompt();
  }

  async submitForm(data) {
    const { onCreateDeck } = this.props;
    try {
      if (onCreateDeck) {
        await onCreateDeck(data);
      }
      this.closeCreateModal();
    } catch (err) {
      console.error(err);
    }
  }

  renderForm() {
    return (
      <section className={styles.createForm}>
        <H6>Deck Name: </H6> <TextInput name="deckName" data-rule-required />
        <ErrorLabel for="deckName" />
      </section>
    );
  }

  renderCreateModal() {
    const { showCreateModal } = this.state;

    if (!showCreateModal) {
      return null;
    }
    const cancel = this.closeCreateModal.bind(this);
    return (
      <Modal onClose={cancel} title="Add Deck">
        <Form onSubmit={this.submitForm.bind(this)} className={styles.addForm}>
          <main className={styles.formContent}>{this.renderForm()}</main>
          <footer className={styles.submitButtons}>
            <span className={styles.formButton}>
              <PrimaryButton submittable>Submit</PrimaryButton>
            </span>
            <span className={styles.formButton}>
              <SecondaryButton onClick={cancel}>Cancel</SecondaryButton>
            </span>
          </footer>
        </Form>
      </Modal>
    );
  }

  render() {
    const { decks, onDeckDelete, onDeckEdit } = this.props;
    if (!decks) {
      return <H2>Loading...</H2>;
    }
    return (
      <section className={styles.page}>
        <div className={styles.header}>
          <H4 className={styles.title}>Deck Editor</H4>
        </div>
        <div className={styles.packageActions}>
          <TextInput
            onChange={this.applyFilter.bind(this)}
            className={styles.filter}
            placeholder="Filter..."
          />
          <PrimaryButton onClick={this.showCreateModal.bind(this)}>
            <AddIcon /> Add
          </PrimaryButton>
        </div>

        <div className={styles.packagesDisplay}>
          {decks.map((deck) => (
            <DeckRow deck={deck} onDelete={onDeckDelete} onEdit={onDeckEdit} />
          ))}
        </div>
        {this.renderCreateModal()}
      </section>
    );
  }
}

export default DeckEditor;
