export interface IBanque {
  id: number;
  code?: string | null;
  libelle?: string | null;
}

export type NewBanque = Omit<IBanque, 'id'> & { id: null };
