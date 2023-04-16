import { IDetailDevis, NewDetailDevis } from './detail-devis.model';

export const sampleWithRequiredData: IDetailDevis = {
  id: 17953,
  qte: 74994,
  prixUnitaire: 60833,
  prixTotal: 85565,
  etat: 98081,
};

export const sampleWithPartialData: IDetailDevis = {
  id: 7885,
  qte: 35831,
  prixUnitaire: 6968,
  prixTotal: 78013,
  etat: 78154,
};

export const sampleWithFullData: IDetailDevis = {
  id: 52516,
  qte: 98174,
  prixUnitaire: 4913,
  prixTotal: 18047,
  etat: 66011,
};

export const sampleWithNewData: NewDetailDevis = {
  qte: 59617,
  prixUnitaire: 66070,
  prixTotal: 81570,
  etat: 46307,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
