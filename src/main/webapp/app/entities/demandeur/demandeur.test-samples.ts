import dayjs from 'dayjs/esm';

import { Civilite } from 'app/entities/enumerations/civilite.model';
import { EtatDemandeur } from 'app/entities/enumerations/etat-demandeur.model';

import { IDemandeur, NewDemandeur } from './demandeur.model';

export const sampleWithRequiredData: IDemandeur = {
  id: 42139,
  nom: 'strategy Ergonomic superstructure',
  prenom: 'Practical',
  telephone: '+33 559771699',
  email: '4p1Xn@iSfMoQ.8lIz6.D.ptZ.wY',
};

export const sampleWithPartialData: IDemandeur = {
  id: 25029,
  nom: 'Garden override Account',
  prenom: 'a dot-com full-range',
  telephone: '+33 485591722',
  email: 'OZ5@m2z.9qvWt.WwFDQ.eQsU.HS',
  adresse: 'engage',
  dateCreation: dayjs('2023-04-16'),
};

export const sampleWithFullData: IDemandeur = {
  id: 12754,
  civilite: Civilite['Mademoiselle'],
  nom: 'Seychelles',
  prenom: 'dynamic Soft',
  dateDeNaissance: dayjs('2023-04-15'),
  telephone: '+33 410241152',
  email: 'G0Jk@ThNv.dyc.UJb4',
  adresse: 'Berkshire',
  dateCreation: dayjs('2023-04-16'),
  etat: EtatDemandeur['Inactif'],
};

export const sampleWithNewData: NewDemandeur = {
  nom: 'generate',
  prenom: 'withdrawal b Fundamental',
  telephone: '+33 354307247',
  email: 'aR6a@uH.NIr3',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
