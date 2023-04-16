import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../terjem-theque.test-samples';

import { TerjemThequeFormService } from './terjem-theque-form.service';

describe('TerjemTheque Form Service', () => {
  let service: TerjemThequeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TerjemThequeFormService);
  });

  describe('Service methods', () => {
    describe('createTerjemThequeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTerjemThequeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lienDownload: expect.any(Object),
            formatDocTraduit: expect.any(Object),
            nomFichier: expect.any(Object),
            docTraduit: expect.any(Object),
            dateCreation: expect.any(Object),
            etat: expect.any(Object),
            prestataire: expect.any(Object),
            demandeur: expect.any(Object),
          })
        );
      });

      it('passing ITerjemTheque should create a new form with FormGroup', () => {
        const formGroup = service.createTerjemThequeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lienDownload: expect.any(Object),
            formatDocTraduit: expect.any(Object),
            nomFichier: expect.any(Object),
            docTraduit: expect.any(Object),
            dateCreation: expect.any(Object),
            etat: expect.any(Object),
            prestataire: expect.any(Object),
            demandeur: expect.any(Object),
          })
        );
      });
    });

    describe('getTerjemTheque', () => {
      it('should return NewTerjemTheque for default TerjemTheque initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTerjemThequeFormGroup(sampleWithNewData);

        const terjemTheque = service.getTerjemTheque(formGroup) as any;

        expect(terjemTheque).toMatchObject(sampleWithNewData);
      });

      it('should return NewTerjemTheque for empty TerjemTheque initial value', () => {
        const formGroup = service.createTerjemThequeFormGroup();

        const terjemTheque = service.getTerjemTheque(formGroup) as any;

        expect(terjemTheque).toMatchObject({});
      });

      it('should return ITerjemTheque', () => {
        const formGroup = service.createTerjemThequeFormGroup(sampleWithRequiredData);

        const terjemTheque = service.getTerjemTheque(formGroup) as any;

        expect(terjemTheque).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITerjemTheque should not enable id FormControl', () => {
        const formGroup = service.createTerjemThequeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTerjemTheque should disable id FormControl', () => {
        const formGroup = service.createTerjemThequeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
