import { ICommentaire, NewCommentaire } from './commentaire.model';

export const sampleWithRequiredData: ICommentaire = {
  id: 48517,
};

export const sampleWithPartialData: ICommentaire = {
  id: 32246,
  texte: 'Pants',
};

export const sampleWithFullData: ICommentaire = {
  id: 91999,
  texte: 'architect b Industrial',
};

export const sampleWithNewData: NewCommentaire = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
