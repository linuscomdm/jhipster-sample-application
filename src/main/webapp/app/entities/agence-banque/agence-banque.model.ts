import { IBanque } from 'app/entities/banque/banque.model';

export interface IAgenceBanque {
  id: number;
  code?: string | null;
  libelle?: string | null;
  banque?: Pick<IBanque, 'id'> | null;
}

export type NewAgenceBanque = Omit<IAgenceBanque, 'id'> & { id: null };
