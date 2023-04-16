import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../nature-document-a-traduire.test-samples';

import { NatureDocumentATraduireFormService } from './nature-document-a-traduire-form.service';

describe('NatureDocumentATraduire Form Service', () => {
  let service: NatureDocumentATraduireFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NatureDocumentATraduireFormService);
  });

  describe('Service methods', () => {
    describe('createNatureDocumentATraduireFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNatureDocumentATraduireFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codeType: expect.any(Object),
            typeDocument: expect.any(Object),
          })
        );
      });

      it('passing INatureDocumentATraduire should create a new form with FormGroup', () => {
        const formGroup = service.createNatureDocumentATraduireFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codeType: expect.any(Object),
            typeDocument: expect.any(Object),
          })
        );
      });
    });

    describe('getNatureDocumentATraduire', () => {
      it('should return NewNatureDocumentATraduire for default NatureDocumentATraduire initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNatureDocumentATraduireFormGroup(sampleWithNewData);

        const natureDocumentATraduire = service.getNatureDocumentATraduire(formGroup) as any;

        expect(natureDocumentATraduire).toMatchObject(sampleWithNewData);
      });

      it('should return NewNatureDocumentATraduire for empty NatureDocumentATraduire initial value', () => {
        const formGroup = service.createNatureDocumentATraduireFormGroup();

        const natureDocumentATraduire = service.getNatureDocumentATraduire(formGroup) as any;

        expect(natureDocumentATraduire).toMatchObject({});
      });

      it('should return INatureDocumentATraduire', () => {
        const formGroup = service.createNatureDocumentATraduireFormGroup(sampleWithRequiredData);

        const natureDocumentATraduire = service.getNatureDocumentATraduire(formGroup) as any;

        expect(natureDocumentATraduire).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INatureDocumentATraduire should not enable id FormControl', () => {
        const formGroup = service.createNatureDocumentATraduireFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNatureDocumentATraduire should disable id FormControl', () => {
        const formGroup = service.createNatureDocumentATraduireFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
