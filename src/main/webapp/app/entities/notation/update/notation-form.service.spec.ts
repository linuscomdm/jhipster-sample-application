import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../notation.test-samples';

import { NotationFormService } from './notation-form.service';

describe('Notation Form Service', () => {
  let service: NotationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NotationFormService);
  });

  describe('Service methods', () => {
    describe('createNotationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNotationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            notetation: expect.any(Object),
            commentaire: expect.any(Object),
            dateCreation: expect.any(Object),
            demandeur: expect.any(Object),
            prestataire: expect.any(Object),
          })
        );
      });

      it('passing INotation should create a new form with FormGroup', () => {
        const formGroup = service.createNotationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            notetation: expect.any(Object),
            commentaire: expect.any(Object),
            dateCreation: expect.any(Object),
            demandeur: expect.any(Object),
            prestataire: expect.any(Object),
          })
        );
      });
    });

    describe('getNotation', () => {
      it('should return NewNotation for default Notation initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNotationFormGroup(sampleWithNewData);

        const notation = service.getNotation(formGroup) as any;

        expect(notation).toMatchObject(sampleWithNewData);
      });

      it('should return NewNotation for empty Notation initial value', () => {
        const formGroup = service.createNotationFormGroup();

        const notation = service.getNotation(formGroup) as any;

        expect(notation).toMatchObject({});
      });

      it('should return INotation', () => {
        const formGroup = service.createNotationFormGroup(sampleWithRequiredData);

        const notation = service.getNotation(formGroup) as any;

        expect(notation).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INotation should not enable id FormControl', () => {
        const formGroup = service.createNotationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNotation should disable id FormControl', () => {
        const formGroup = service.createNotationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
