import { IAgenceBanque, NewAgenceBanque } from './agence-banque.model';

export const sampleWithRequiredData: IAgenceBanque = {
  id: 20370,
  code: 'auxiliary',
  libelle: 'TCP withdrawal infomediaries',
};

export const sampleWithPartialData: IAgenceBanque = {
  id: 38589,
  code: 'Generic Enhanced',
  libelle: 'Franche-Comté',
};

export const sampleWithFullData: IAgenceBanque = {
  id: 51457,
  code: 'Computer Fresh withdrawal',
  libelle: 'Borders',
};

export const sampleWithNewData: NewAgenceBanque = {
  code: 'Frozen',
  libelle: 'holistic',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
