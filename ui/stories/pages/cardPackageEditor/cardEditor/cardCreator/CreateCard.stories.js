/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import { storiesOf } from '@storybook/react';
import { withKnobs } from '@storybook/addon-knobs';
import CreateCard from '../../../../../src/pages/authenticated/editor/cardEditor/cardCreator/CreateCard';

const stories = storiesOf('/pages/authenticated/cardPackageEditor/cardEditor/cardCreator', module);
stories.addDecorator(withKnobs);
stories.add('CreateCardr', () => <CreateCard />);
