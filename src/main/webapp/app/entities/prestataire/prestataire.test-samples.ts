import dayjs from 'dayjs/esm';

import { Civilite } from 'app/entities/enumerations/civilite.model';
import { TypeIdentiteProfessionnelle } from 'app/entities/enumerations/type-identite-professionnelle.model';
import { EtatPrestataire } from 'app/entities/enumerations/etat-prestataire.model';

import { IPrestataire, NewPrestataire } from './prestataire.model';

export const sampleWithRequiredData: IPrestataire = {
  id: 77676,
  nom: 'green web-readiness proactive',
  prenom: 'Picardie',
  telephoneMobile: "Chaussée-d'Antin Taka killer",
  email: 'Fqa@BfYDaU.06G',
  numeroPieceIdentite: 'Loan c',
};

export const sampleWithPartialData: IPrestataire = {
  id: 29623,
  civilite: Civilite['Mademoiselle'],
  nom: 'payment synthesize',
  prenom: 'high-level Table Car',
  telephoneMobile: 'salmon indexing',
  email: 'iZKuM@xRp8_.dFhvhO.eyt.iRf',
  adresse: 'Architecte wireless Automotive',
  codePostal: 'Virtual',
  photoDeProfil: '../fake-data/blob/hipster.png',
  photoDeProfilContentType: 'unknown',
  numeroPieceIdentite: 'b b generate',
  titulaireDuCompte: 'deposit',
  dateCreation: dayjs('2023-04-15'),
};

export const sampleWithFullData: IPrestataire = {
  id: 51187,
  civilite: Civilite['Mademoiselle'],
  nom: 'Granite infrastructures',
  prenom: 'e-enable Midi-Pyrénées',
  nomCommercial: 'COM',
  telephoneTravail: 'Aquitaine',
  telephoneMobile: 'Bretagne Irlande',
  email: '.78mt@l.9yuO',
  adresse: "Koweït l'Abbaye",
  codePostal: 'Dauphine F',
  photoDeProfil: '../fake-data/blob/hipster.png',
  photoDeProfilContentType: 'unknown',
  numeroPieceIdentite: 'synergize executive Assistant',
  typeIdentiteProfessionnelle: TypeIdentiteProfessionnelle['CARTEPROFESSIONNEL'],
  rattachIdentitePro: '../fake-data/blob/hipster.png',
  rattachIdentiteProContentType: 'unknown',
  coordonneesBancaires: '../fake-data/blob/hipster.png',
  coordonneesBancairesContentType: 'unknown',
  titulaireDuCompte: 'algorithm b homogeneous',
  ribOuRip: 'overriding Outdoors',
  dateCreation: dayjs('2023-04-15'),
  etat: EtatPrestataire['Inactif'],
};

export const sampleWithNewData: NewPrestataire = {
  nom: 'a reboot',
  prenom: 'Fresh',
  telephoneMobile: 'generating a',
  email: 'N_Q@U.SGRJ6.UMk5.CGqGC.6d.ySSlY.cRf7',
  numeroPieceIdentite: 'didactic Awesome optical',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
