import { ILangue, NewLangue } from './langue.model';

export const sampleWithRequiredData: ILangue = {
  id: 61115,
};

export const sampleWithPartialData: ILangue = {
  id: 65909,
  codeLangue: 'users Aquitaine',
  nomLangue: 'silver',
};

export const sampleWithFullData: ILangue = {
  id: 229,
  codeLangue: 'Unbranded',
  nomLangue: 'b invoice Tuna',
};

export const sampleWithNewData: NewLangue = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
