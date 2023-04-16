import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../langue.test-samples';

import { LangueFormService } from './langue-form.service';

describe('Langue Form Service', () => {
  let service: LangueFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LangueFormService);
  });

  describe('Service methods', () => {
    describe('createLangueFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLangueFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codeLangue: expect.any(Object),
            nomLangue: expect.any(Object),
            docTraductions: expect.any(Object),
          })
        );
      });

      it('passing ILangue should create a new form with FormGroup', () => {
        const formGroup = service.createLangueFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codeLangue: expect.any(Object),
            nomLangue: expect.any(Object),
            docTraductions: expect.any(Object),
          })
        );
      });
    });

    describe('getLangue', () => {
      it('should return NewLangue for default Langue initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLangueFormGroup(sampleWithNewData);

        const langue = service.getLangue(formGroup) as any;

        expect(langue).toMatchObject(sampleWithNewData);
      });

      it('should return NewLangue for empty Langue initial value', () => {
        const formGroup = service.createLangueFormGroup();

        const langue = service.getLangue(formGroup) as any;

        expect(langue).toMatchObject({});
      });

      it('should return ILangue', () => {
        const formGroup = service.createLangueFormGroup(sampleWithRequiredData);

        const langue = service.getLangue(formGroup) as any;

        expect(langue).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILangue should not enable id FormControl', () => {
        const formGroup = service.createLangueFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLangue should disable id FormControl', () => {
        const formGroup = service.createLangueFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
