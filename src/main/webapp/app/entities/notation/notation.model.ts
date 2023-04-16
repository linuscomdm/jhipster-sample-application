import dayjs from 'dayjs/esm';
import { IDemandeur } from 'app/entities/demandeur/demandeur.model';
import { IPrestataire } from 'app/entities/prestataire/prestataire.model';

export interface INotation {
  id: number;
  notetation?: number | null;
  commentaire?: string | null;
  dateCreation?: dayjs.Dayjs | null;
  demandeur?: Pick<IDemandeur, 'id'> | null;
  prestataire?: Pick<IPrestataire, 'id'> | null;
}

export type NewNotation = Omit<INotation, 'id'> & { id: null };
