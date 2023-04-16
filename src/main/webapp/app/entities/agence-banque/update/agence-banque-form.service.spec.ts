import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../agence-banque.test-samples';

import { AgenceBanqueFormService } from './agence-banque-form.service';

describe('AgenceBanque Form Service', () => {
  let service: AgenceBanqueFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AgenceBanqueFormService);
  });

  describe('Service methods', () => {
    describe('createAgenceBanqueFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAgenceBanqueFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            libelle: expect.any(Object),
            banque: expect.any(Object),
          })
        );
      });

      it('passing IAgenceBanque should create a new form with FormGroup', () => {
        const formGroup = service.createAgenceBanqueFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            libelle: expect.any(Object),
            banque: expect.any(Object),
          })
        );
      });
    });

    describe('getAgenceBanque', () => {
      it('should return NewAgenceBanque for default AgenceBanque initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAgenceBanqueFormGroup(sampleWithNewData);

        const agenceBanque = service.getAgenceBanque(formGroup) as any;

        expect(agenceBanque).toMatchObject(sampleWithNewData);
      });

      it('should return NewAgenceBanque for empty AgenceBanque initial value', () => {
        const formGroup = service.createAgenceBanqueFormGroup();

        const agenceBanque = service.getAgenceBanque(formGroup) as any;

        expect(agenceBanque).toMatchObject({});
      });

      it('should return IAgenceBanque', () => {
        const formGroup = service.createAgenceBanqueFormGroup(sampleWithRequiredData);

        const agenceBanque = service.getAgenceBanque(formGroup) as any;

        expect(agenceBanque).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAgenceBanque should not enable id FormControl', () => {
        const formGroup = service.createAgenceBanqueFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAgenceBanque should disable id FormControl', () => {
        const formGroup = service.createAgenceBanqueFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
