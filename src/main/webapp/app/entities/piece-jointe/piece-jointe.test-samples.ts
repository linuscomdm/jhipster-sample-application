import dayjs from 'dayjs/esm';

import { IPieceJointe, NewPieceJointe } from './piece-jointe.model';

export const sampleWithRequiredData: IPieceJointe = {
  id: 43572,
  nomFichier: 'Jewelery',
  chemin: 'Oman',
  urlPiece: 'SCSI AGP c',
};

export const sampleWithPartialData: IPieceJointe = {
  id: 80903,
  nomFichier: 'invoice Clothing IB',
  chemin: 'ivory Music user-facing',
  urlPiece: 'engage Outdoors',
  dateCreation: dayjs('2023-04-16'),
};

export const sampleWithFullData: IPieceJointe = {
  id: 674,
  nomFichier: 'invoice a',
  chemin: 'Shirt Cambridgeshire c',
  urlPiece: 'c Grenade salmon',
  description: 'Manager',
  codePiece: 'RSS Zadkine',
  libellePiece: 'synergies Turquie experiences',
  rattachPj: '../fake-data/blob/hipster.png',
  rattachPjContentType: 'unknown',
  dateCreation: dayjs('2023-04-16'),
};

export const sampleWithNewData: NewPieceJointe = {
  nomFichier: 'Frozen relationships',
  chemin: 'disintermediate state',
  urlPiece: 'b withdrawal Sleek',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
