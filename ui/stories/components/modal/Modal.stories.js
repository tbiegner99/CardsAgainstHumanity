import React from 'react';
import { storiesOf } from '@storybook/react';
import { withKnobs, boolean, number } from '@storybook/addon-knobs';
import { action } from '@storybook/addon-actions';
import ConfirmationModal from '../../../src/components/modal/ConfirmationModal';

const stories = storiesOf('Modal', module);
stories.addDecorator(withKnobs);
stories.add('displays correctly', () => (
  <ConfirmationModal
    onCancel={action('cancel')}
    onConfirm={action('submit')}
    onClose={action('close action')}
    confirmText="Submit"
    cancelText="Cancel"
  >
    Are you sure you want to do something
  </ConfirmationModal>
));
