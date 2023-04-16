import { IDemandeDeTraduction } from 'app/entities/demande-de-traduction/demande-de-traduction.model';
import { IPrestataire } from 'app/entities/prestataire/prestataire.model';
import { IDemandeur } from 'app/entities/demandeur/demandeur.model';

export interface ICommentaire {
  id: number;
  texte?: string | null;
  demandeDeTraduction?: Pick<IDemandeDeTraduction, 'id'> | null;
  prestataire?: Pick<IPrestataire, 'id'> | null;
  demandeur?: Pick<IDemandeur, 'id'> | null;
}

export type NewCommentaire = Omit<ICommentaire, 'id'> & { id: null };
