import React from 'react';
import combineClasses from 'classnames';
import Form from 'reactforms/src/form/Form';
import FileUploader from 'reactforms/src/form/elements/FileUploader';
import ErrorLabel from 'reactforms/src/form/elements/ErrorLabel';
import { H4, H2, H6 } from '../../../../../components/elements/headers/Headers';
import { TextInput } from '../../../../../components/formelements/inputs/TextInput';
import {
  PrimaryButton,
  SecondaryButton
} from '../../../../../components/formelements/buttons/Buttons';
import { AddIcon } from '../../../../../components/Icons/icons';

import Modal from '../../../../../components/modal/Modal';
import PackageRow from './packageRow/PackageRow';

import styles from './PackageEditor.css';

class PackageEditor extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  showCreateModal() {
    this.setState({ showCreateModal: true });
  }

  closeCreateModal() {
    this.setState({ showCreateModal: false, chosenIcon: null });
  }

  applyFilter(filter) {
    this.setState({ filter });
  }

  promptFile() {
    this.refs.iconUpload.prompt();
  }

  async submitForm(data) {
    const { onAddPackage } = this.props;
    try {
      if (onAddPackage) {
        await onAddPackage(data);
      }
      this.closeCreateModal();
    } catch (err) {
      console.error(err);
    }
  }

  changeIconPreview(value) {
    this.setState({ chosenIcon: value });
  }

  renderIconPreview(chosenIcon) {
    if (chosenIcon) {
      return <img src={chosenIcon.content} width="50" height="50" />;
    }
    return <span>No file chosen</span>;
  }

  renderForm() {
    const { chosenIcon } = this.state;
    return (
      <section className={styles.createForm}>
        <H6>Package Name: </H6> <TextInput name="packageName" data-rule-required />
        <ErrorLabel for="packageName" />
        <div className={styles.iconUpload}>
          <div className={styles.iconElement}>
            <H6>Icon:</H6>
            <FileUploader
              onChange={this.changeIconPreview.bind(this)}
              ref="iconUpload"
              prompt={false}
              contentType="url"
              data-rule-max-size="10K"
              accept="image/*"
              name="icon"
              style={{ height: 0 }}
            />
            <div className={styles.iconUpload}>
              <div>{this.renderIconPreview(chosenIcon)}</div>
              <SecondaryButton onClick={this.promptFile.bind(this)}>Upload Icon</SecondaryButton>
            </div>
            <ErrorLabel for="icon" />
          </div>
        </div>
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
      <Modal onClose={cancel} title="Add Package">
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
    const { packages, onPackageDelete, onPackageEdit } = this.props;
    if (!packages) {
      return <H2>Loading...</H2>;
    }
    return (
      <section className={styles.page}>
        <div className={styles.header}>
          <H4 className={styles.title}>Package Editor</H4>
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
          {packages.map((pack) => (
            <PackageRow package={pack} onDelete={onPackageDelete} onEdit={onPackageEdit} />
          ))}
        </div>
        {this.renderCreateModal()}
      </section>
    );
  }
}

export default PackageEditor;
