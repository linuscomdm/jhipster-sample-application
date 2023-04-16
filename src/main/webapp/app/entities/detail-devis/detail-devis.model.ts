import { IDevis } from 'app/entities/devis/devis.model';

export interface IDetailDevis {
  id: number;
  qte?: number | null;
  prixUnitaire?: number | null;
  prixTotal?: number | null;
  etat?: number | null;
  devis?: Pick<IDevis, 'id'> | null;
}

export type NewDetailDevis = Omit<IDetailDevis, 'id'> & { id: null };
