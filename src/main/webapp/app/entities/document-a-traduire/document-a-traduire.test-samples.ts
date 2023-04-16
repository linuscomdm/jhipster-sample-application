import { IDocumentATraduire, NewDocumentATraduire } from './document-a-traduire.model';

export const sampleWithRequiredData: IDocumentATraduire = {
  id: 88703,
  nombreDePagesATraduire: 87420,
};

export const sampleWithPartialData: IDocumentATraduire = {
  id: 16537,
  nombreDePagesATraduire: 16071,
  mentionTraitementParticulier: 'Clothing',
};

export const sampleWithFullData: IDocumentATraduire = {
  id: 58535,
  nombreDePagesATraduire: 28602,
  mentionTraitementParticulier: 'bifurcated Vaneau Investment',
  remarques: 'Auvergne',
};

export const sampleWithNewData: NewDocumentATraduire = {
  nombreDePagesATraduire: 27695,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
