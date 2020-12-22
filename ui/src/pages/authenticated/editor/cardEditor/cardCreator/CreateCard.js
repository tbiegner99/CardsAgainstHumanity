import React from 'react';
import Form from 'reactforms/src/form/Form';
import HiddenField from 'reactforms/src/form/elements/HiddenField';
import FileUploader from 'reactforms/src/form/elements/FileUploader';
import { H5 } from '../../../../../components/elements/headers/Headers';
import {
  BlackCard,
  WhiteCard,
  Instructions
} from '../../../../../components/CardsAgainstHumanityCard';

import {
  PrimaryButton,
  SecondaryButton
} from '../../../../../components/formelements/buttons/Buttons';
import { TextInput } from '../../../../../components/formelements/inputs/TextInput';
import styles from './createCard.css';
import CardTypeSelect from '../../../../../components/CardTypeSelect/CardTypeSelect';

const getNumberOfAnswers = (text) => {
  const blankRegex = /_+/g;
  const matches = text.match(blankRegex) || [];
  return Math.max(matches.length, 1);
};

const validateNumberOfAnswers = (value) => {
  Instructions.getInstructionForNumberOfAnswers(getNumberOfAnswers(value));
  return true;
};

const getMessage = (value) => {
  const answers = getNumberOfAnswers(value);
  return `Illegal number of answers ${answers}. Max is 3`;
};

class CreateCard extends React.Component {
  constructor(props) {
    super(props);
    const { cardText, type } = props.cardToUpdate || {};
    this.state = {
      type: type || 'white',
      text: cardText || '',
      bulkItems: []
    };
  }

  updateText(value) {
    this.setState({ text: value });
  }

  processBulkFile(fileContent) {
    const bulkItems = fileContent.content.split('\n');
    this.setState({
      bulkItems
    });
  }

  renderTextInput() {
    return (
      <div>
        Text:
        <TextInput
          style={{ width: '100%' }}
          data-rule-required
          data-rule-custom={validateNumberOfAnswers}
          data-msg-custom={getMessage}
          name="cardText"
          value={this.state.text}
          onChange={this.updateText.bind(this)}
        />
      </div>
    );
  }

  renderType() {
    const { type } = this.state;
    const { cardToUpdate } = this.props;
    const onChange = (value) => this.setState({ type: value });
    return (
      <CardTypeSelect
        className={styles.typeSelect}
        name="cardType"
        onChange={onChange}
        disabled={!!cardToUpdate}
        type={type}
      />
    );
  }

  renderWhiteCard() {
    return (
      <WhiteCard className={styles.cardPreview} flipped>
        {this.state.text}
      </WhiteCard>
    );
  }

  renderBlackCard() {
    const numberOfAnswers = getNumberOfAnswers(this.state.text);
    let instruction = null;
    try {
      instruction = Instructions.getInstructionForNumberOfAnswers(numberOfAnswers);
    } catch (e) {
      console.warn(e);
    }
    return (
      <BlackCard className={styles.cardPreview} flipped instruction={instruction}>
        {this.state.text}
      </BlackCard>
    );
  }

  renderCard() {
    const { type } = this.state;
    if (type === 'black') {
      return this.renderBlackCard();
    }
    return this.renderWhiteCard();
  }

  renderBulkUpload() {
    return (
      <section className={styles.bulkDisplay}>
        <div className={styles.bulkForm}>
          {this.renderType()}
          <div>
            <FileUploader
              onChange={this.processBulkFile.bind(this)}
              ref="bulkUpload"
              prompt={false}
              accept="text/plain"
              contentType="text"
              name="icon"
              data-rule-required
              style={{ height: 0 }}
            />
            <HiddenField name="bulkItems" value={this.state.bulkItems} />
            <HiddenField name="isBulk" value />
            <SecondaryButton onClick={() => this.refs.bulkUpload.prompt()}>
              Upload Cards
            </SecondaryButton>
          </div>
        </div>

        <div className={styles.previewCards}>
          <H5>Preview Cards</H5>
          {this.state.bulkItems.map((item) => (
            <div>{item}</div>
          ))}
        </div>
      </section>
    );
  }

  renderSingleCreateForm() {
    return [this.renderType(), this.renderTextInput(), this.renderCard()];
  }

  renderForm() {
    const { bulk, cardToUpdate } = this.props;
    if (!bulk) {
      return this.renderSingleCreateForm();
    }

    return this.renderBulkUpload();
  }

  render() {
    const { onCreateCard, onCancel, bulk } = this.props;
    return (
      <Form onSubmit={onCreateCard} className={styles.addForm}>
        <main className={styles.formContent}>
          <HiddenField name="type" value={this.state.type} />
          {this.renderForm()}
        </main>
        <footer className={styles.submitButtons}>
          <span className={styles.formButton}>
            <PrimaryButton submittable>Submit</PrimaryButton>
          </span>
          <span className={styles.formButton}>
            <SecondaryButton onClick={onCancel}>Cancel</SecondaryButton>
          </span>
        </footer>
      </Form>
    );
  }
}

export default CreateCard;
