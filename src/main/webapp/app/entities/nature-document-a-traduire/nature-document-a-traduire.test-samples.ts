import { INatureDocumentATraduire, NewNatureDocumentATraduire } from './nature-document-a-traduire.model';

export const sampleWithRequiredData: INatureDocumentATraduire = {
  id: 38500,
};

export const sampleWithPartialData: INatureDocumentATraduire = {
  id: 83874,
  typeDocument: 'Sausages SMTP deposit',
};

export const sampleWithFullData: INatureDocumentATraduire = {
  id: 37909,
  codeType: 'Pastourelle Frozen deposit',
  typeDocument: 'toolset Automotive 1080p',
};

export const sampleWithNewData: NewNatureDocumentATraduire = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
