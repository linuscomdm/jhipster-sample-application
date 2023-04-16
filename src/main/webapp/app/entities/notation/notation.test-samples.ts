import dayjs from 'dayjs/esm';

import { INotation, NewNotation } from './notation.model';

export const sampleWithRequiredData: INotation = {
  id: 44961,
  notetation: 9333,
};

export const sampleWithPartialData: INotation = {
  id: 36898,
  notetation: 26274,
};

export const sampleWithFullData: INotation = {
  id: 19680,
  notetation: 6174,
  commentaire: 'firmware Account Zambie',
  dateCreation: dayjs('2023-04-15'),
};

export const sampleWithNewData: NewNotation = {
  notetation: 52652,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
