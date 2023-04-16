import dayjs from 'dayjs/esm';
import { IVille } from 'app/entities/ville/ville.model';
import { IDemandeur } from 'app/entities/demandeur/demandeur.model';
import { ModeEnvoi } from 'app/entities/enumerations/mode-envoi.model';
import { ModeLivraison } from 'app/entities/enumerations/mode-livraison.model';
import { EtatDemande } from 'app/entities/enumerations/etat-demande.model';

export interface IDemandeDeTraduction {
  id: number;
  modeEnvoiPreconise?: ModeEnvoi | null;
  modeLivraisonExige?: ModeLivraison | null;
  delaiDeTraitemenSouhaite?: number | null;
  adresseLivraison?: string | null;
  delaiDeTraitemenPrestataire?: number | null;
  observation?: string | null;
  dateCreation?: dayjs.Dayjs | null;
  dateCloture?: dayjs.Dayjs | null;
  etat?: EtatDemande | null;
  ville?: Pick<IVille, 'id' | 'nom'> | null;
  demandeurService?: Pick<IDemandeur, 'id' | 'nom'> | null;
}

export type NewDemandeDeTraduction = Omit<IDemandeDeTraduction, 'id'> & { id: null };
