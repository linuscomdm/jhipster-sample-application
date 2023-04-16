import { ILangue } from 'app/entities/langue/langue.model';
import { INatureDocumentATraduire } from 'app/entities/nature-document-a-traduire/nature-document-a-traduire.model';
import { IDemandeDeTraduction } from 'app/entities/demande-de-traduction/demande-de-traduction.model';

export interface IDocumentATraduire {
  id: number;
  nombreDePagesATraduire?: number | null;
  mentionTraitementParticulier?: string | null;
  remarques?: string | null;
  langueDestination?: Pick<ILangue, 'id' | 'nomLangue'> | null;
  typeDocument?: Pick<INatureDocumentATraduire, 'id' | 'typeDocument'> | null;
  demandeTraductions?: Pick<IDemandeDeTraduction, 'id' | 'delaiDeTraitemenSouhaite'> | null;
}

export type NewDocumentATraduire = Omit<IDocumentATraduire, 'id'> & { id: null };
