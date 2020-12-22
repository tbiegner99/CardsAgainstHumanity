/* eslint-disable import/no-extraneous-dependencies */
import React from 'react';
import { storiesOf } from '@storybook/react';
import { withKnobs } from '@storybook/addon-knobs';
import PackageEditor from '../../../src/pages/authenticated/editor/cardEditor/packageEditor/PackageEditor';

const packages = [{ name: 'Package1', numberOfCards: 0, id: 5 }];

const stories = storiesOf('/pages/authenticated/cardPackageEditor/PackageEditor', module);
stories.addDecorator(withKnobs);
stories.add('Package Editor', () => <PackageEditor packages={packages} />);
