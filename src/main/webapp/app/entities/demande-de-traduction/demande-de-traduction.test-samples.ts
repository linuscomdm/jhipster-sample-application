import dayjs from 'dayjs/esm';

import { ModeEnvoi } from 'app/entities/enumerations/mode-envoi.model';
import { ModeLivraison } from 'app/entities/enumerations/mode-livraison.model';
import { EtatDemande } from 'app/entities/enumerations/etat-demande.model';

import { IDemandeDeTraduction, NewDemandeDeTraduction } from './demande-de-traduction.model';

export const sampleWithRequiredData: IDemandeDeTraduction = {
  id: 76056,
};

export const sampleWithPartialData: IDemandeDeTraduction = {
  id: 81517,
  modeLivraisonExige: ModeLivraison['REMISEENMAINSPROPRES'],
  delaiDeTraitemenPrestataire: 60126,
  observation: 'viral interfaces calculate',
  dateCreation: dayjs('2023-04-16'),
  dateCloture: dayjs('2023-04-15'),
  etat: EtatDemande['Close'],
};

export const sampleWithFullData: IDemandeDeTraduction = {
  id: 30516,
  modeEnvoiPreconise: ModeEnvoi['EMAIL'],
  modeLivraisonExige: ModeLivraison['EMAIL'],
  delaiDeTraitemenSouhaite: 16643,
  adresseLivraison: 'superstructure',
  delaiDeTraitemenPrestataire: 57824,
  observation: 'Bike',
  dateCreation: dayjs('2023-04-15'),
  dateCloture: dayjs('2023-04-16'),
  etat: EtatDemande['DevisAccepte'],
};

export const sampleWithNewData: NewDemandeDeTraduction = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
