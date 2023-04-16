import dayjs from 'dayjs/esm';
import { IPrestataire } from 'app/entities/prestataire/prestataire.model';
import { IDemandeur } from 'app/entities/demandeur/demandeur.model';
import { FormatDocTraduit } from 'app/entities/enumerations/format-doc-traduit.model';

export interface ITerjemTheque {
  id: number;
  lienDownload?: string | null;
  formatDocTraduit?: FormatDocTraduit | null;
  nomFichier?: string | null;
  docTraduit?: string | null;
  docTraduitContentType?: string | null;
  dateCreation?: dayjs.Dayjs | null;
  etat?: number | null;
  prestataire?: Pick<IPrestataire, 'id'> | null;
  demandeur?: Pick<IDemandeur, 'id'> | null;
}

export type NewTerjemTheque = Omit<ITerjemTheque, 'id'> & { id: null };
