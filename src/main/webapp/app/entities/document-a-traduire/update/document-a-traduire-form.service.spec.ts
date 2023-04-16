import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../document-a-traduire.test-samples';

import { DocumentATraduireFormService } from './document-a-traduire-form.service';

describe('DocumentATraduire Form Service', () => {
  let service: DocumentATraduireFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DocumentATraduireFormService);
  });

  describe('Service methods', () => {
    describe('createDocumentATraduireFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDocumentATraduireFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreDePagesATraduire: expect.any(Object),
            mentionTraitementParticulier: expect.any(Object),
            remarques: expect.any(Object),
            langueDestination: expect.any(Object),
            typeDocument: expect.any(Object),
            demandeTraductions: expect.any(Object),
          })
        );
      });

      it('passing IDocumentATraduire should create a new form with FormGroup', () => {
        const formGroup = service.createDocumentATraduireFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreDePagesATraduire: expect.any(Object),
            mentionTraitementParticulier: expect.any(Object),
            remarques: expect.any(Object),
            langueDestination: expect.any(Object),
            typeDocument: expect.any(Object),
            demandeTraductions: expect.any(Object),
          })
        );
      });
    });

    describe('getDocumentATraduire', () => {
      it('should return NewDocumentATraduire for default DocumentATraduire initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDocumentATraduireFormGroup(sampleWithNewData);

        const documentATraduire = service.getDocumentATraduire(formGroup) as any;

        expect(documentATraduire).toMatchObject(sampleWithNewData);
      });

      it('should return NewDocumentATraduire for empty DocumentATraduire initial value', () => {
        const formGroup = service.createDocumentATraduireFormGroup();

        const documentATraduire = service.getDocumentATraduire(formGroup) as any;

        expect(documentATraduire).toMatchObject({});
      });

      it('should return IDocumentATraduire', () => {
        const formGroup = service.createDocumentATraduireFormGroup(sampleWithRequiredData);

        const documentATraduire = service.getDocumentATraduire(formGroup) as any;

        expect(documentATraduire).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDocumentATraduire should not enable id FormControl', () => {
        const formGroup = service.createDocumentATraduireFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDocumentATraduire should disable id FormControl', () => {
        const formGroup = service.createDocumentATraduireFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
