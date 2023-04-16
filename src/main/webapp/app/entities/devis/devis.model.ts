import dayjs from 'dayjs/esm';
import { IPrestataire } from 'app/entities/prestataire/prestataire.model';
import { IDemandeDeTraduction } from 'app/entities/demande-de-traduction/demande-de-traduction.model';

export interface IDevis {
  id: number;
  numero?: string | null;
  date?: dayjs.Dayjs | null;
  prixTotal?: number | null;
  etat?: number | null;
  prestataire?: Pick<IPrestataire, 'id'> | null;
  demandeDeTraduction?: Pick<IDemandeDeTraduction, 'id'> | null;
}

export type NewDevis = Omit<IDevis, 'id'> & { id: null };
