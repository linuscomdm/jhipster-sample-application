import dayjs from 'dayjs/esm';
import { IDemandeDeTraduction } from 'app/entities/demande-de-traduction/demande-de-traduction.model';
import { IPrestataire } from 'app/entities/prestataire/prestataire.model';

export interface IPieceJointe {
  id: number;
  nomFichier?: string | null;
  chemin?: string | null;
  urlPiece?: string | null;
  description?: string | null;
  codePiece?: string | null;
  libellePiece?: string | null;
  rattachPj?: string | null;
  rattachPjContentType?: string | null;
  dateCreation?: dayjs.Dayjs | null;
  pjDdeTraductions?: Pick<IDemandeDeTraduction, 'id' | 'delaiDeTraitemenSouhaite'> | null;
  prestataire?: Pick<IPrestataire, 'id'> | null;
}

export type NewPieceJointe = Omit<IPieceJointe, 'id'> & { id: null };
