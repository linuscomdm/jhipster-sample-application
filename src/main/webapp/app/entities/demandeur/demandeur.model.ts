import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { IVille } from 'app/entities/ville/ville.model';
import { IBanque } from 'app/entities/banque/banque.model';
import { IAgenceBanque } from 'app/entities/agence-banque/agence-banque.model';
import { Civilite } from 'app/entities/enumerations/civilite.model';
import { EtatDemandeur } from 'app/entities/enumerations/etat-demandeur.model';

export interface IDemandeur {
  id: number;
  civilite?: Civilite | null;
  nom?: string | null;
  prenom?: string | null;
  dateDeNaissance?: dayjs.Dayjs | null;
  telephone?: string | null;
  email?: string | null;
  adresse?: string | null;
  dateCreation?: dayjs.Dayjs | null;
  etat?: EtatDemandeur | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
  ville?: Pick<IVille, 'id' | 'nom'> | null;
  banque?: Pick<IBanque, 'id' | 'libelle'> | null;
  agenceBanque?: Pick<IAgenceBanque, 'id'> | null;
}

export type NewDemandeur = Omit<IDemandeur, 'id'> & { id: null };
