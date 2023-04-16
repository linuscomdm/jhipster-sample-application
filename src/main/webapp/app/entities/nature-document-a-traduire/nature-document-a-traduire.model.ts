export interface INatureDocumentATraduire {
  id: number;
  codeType?: string | null;
  typeDocument?: string | null;
}

export type NewNatureDocumentATraduire = Omit<INatureDocumentATraduire, 'id'> & { id: null };
