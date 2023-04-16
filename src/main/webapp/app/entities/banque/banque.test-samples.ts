import { IBanque, NewBanque } from './banque.model';

export const sampleWithRequiredData: IBanque = {
  id: 83469,
  code: 'Laos',
  libelle: 'Rubber',
};

export const sampleWithPartialData: IBanque = {
  id: 78703,
  code: 'Île-de-France Developpeur',
  libelle: 'c Chair portals',
};

export const sampleWithFullData: IBanque = {
  id: 90625,
  code: 'b Technicien Afghani',
  libelle: 'content payment',
};

export const sampleWithNewData: NewBanque = {
  code: 'Account',
  libelle: 'intuitive Intelligent Buckinghamshire',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
