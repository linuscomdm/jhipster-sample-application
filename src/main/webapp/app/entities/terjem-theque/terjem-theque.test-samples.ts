import dayjs from 'dayjs/esm';

import { FormatDocTraduit } from 'app/entities/enumerations/format-doc-traduit.model';

import { ITerjemTheque, NewTerjemTheque } from './terjem-theque.model';

export const sampleWithRequiredData: ITerjemTheque = {
  id: 2773,
  lienDownload: 'sky Naira',
  formatDocTraduit: FormatDocTraduit['PNG'],
  nomFichier: 'purple',
};

export const sampleWithPartialData: ITerjemTheque = {
  id: 94643,
  lienDownload: 'hacking multi-byte',
  formatDocTraduit: FormatDocTraduit['PDF'],
  nomFichier: 'transmitting forecast',
};

export const sampleWithFullData: ITerjemTheque = {
  id: 99079,
  lienDownload: 'Money PCI transmitting',
  formatDocTraduit: FormatDocTraduit['PDF'],
  nomFichier: 'Configurable',
  docTraduit: '../fake-data/blob/hipster.png',
  docTraduitContentType: 'unknown',
  dateCreation: dayjs('2023-04-15'),
  etat: 91077,
};

export const sampleWithNewData: NewTerjemTheque = {
  lienDownload: 'Car',
  formatDocTraduit: FormatDocTraduit['JPEG'],
  nomFichier: 'invoice deposit Fresh',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
