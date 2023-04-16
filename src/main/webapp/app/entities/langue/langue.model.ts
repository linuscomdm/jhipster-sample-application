import { IDocumentATraduire } from 'app/entities/document-a-traduire/document-a-traduire.model';

export interface ILangue {
  id: number;
  codeLangue?: string | null;
  nomLangue?: string | null;
  docTraductions?: Pick<IDocumentATraduire, 'id' | 'nombreDePagesATraduire'> | null;
}

export type NewLangue = Omit<ILangue, 'id'> & { id: null };
