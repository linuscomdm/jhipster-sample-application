import dayjs from 'dayjs/esm';

import { IDevis, NewDevis } from './devis.model';

export const sampleWithRequiredData: IDevis = {
  id: 65977,
  numero: 'navigate data-warehouse Vaugir',
  date: dayjs('2023-04-15'),
  prixTotal: 49920,
  etat: 36541,
};

export const sampleWithPartialData: IDevis = {
  id: 51439,
  numero: 'Vision-oriented',
  date: dayjs('2023-04-16'),
  prixTotal: 176,
  etat: 98743,
};

export const sampleWithFullData: IDevis = {
  id: 46477,
  numero: 'generate',
  date: dayjs('2023-04-16'),
  prixTotal: 77825,
  etat: 80435,
};

export const sampleWithNewData: NewDevis = {
  numero: 'bi-directional a enhance',
  date: dayjs('2023-04-15'),
  prixTotal: 90250,
  etat: 96804,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
