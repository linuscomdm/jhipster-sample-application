import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../demande-de-traduction.test-samples';

import { DemandeDeTraductionFormService } from './demande-de-traduction-form.service';

describe('DemandeDeTraduction Form Service', () => {
  let service: DemandeDeTraductionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DemandeDeTraductionFormService);
  });

  describe('Service methods', () => {
    describe('createDemandeDeTraductionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDemandeDeTraductionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            modeEnvoiPreconise: expect.any(Object),
            modeLivraisonExige: expect.any(Object),
            delaiDeTraitemenSouhaite: expect.any(Object),
            adresseLivraison: expect.any(Object),
            delaiDeTraitemenPrestataire: expect.any(Object),
            observation: expect.any(Object),
            dateCreation: expect.any(Object),
            dateCloture: expect.any(Object),
            etat: expect.any(Object),
            ville: expect.any(Object),
            demandeurService: expect.any(Object),
          })
        );
      });

      it('passing IDemandeDeTraduction should create a new form with FormGroup', () => {
        const formGroup = service.createDemandeDeTraductionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            modeEnvoiPreconise: expect.any(Object),
            modeLivraisonExige: expect.any(Object),
            delaiDeTraitemenSouhaite: expect.any(Object),
            adresseLivraison: expect.any(Object),
            delaiDeTraitemenPrestataire: expect.any(Object),
            observation: expect.any(Object),
            dateCreation: expect.any(Object),
            dateCloture: expect.any(Object),
            etat: expect.any(Object),
            ville: expect.any(Object),
            demandeurService: expect.any(Object),
          })
        );
      });
    });

    describe('getDemandeDeTraduction', () => {
      it('should return NewDemandeDeTraduction for default DemandeDeTraduction initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDemandeDeTraductionFormGroup(sampleWithNewData);

        const demandeDeTraduction = service.getDemandeDeTraduction(formGroup) as any;

        expect(demandeDeTraduction).toMatchObject(sampleWithNewData);
      });

      it('should return NewDemandeDeTraduction for empty DemandeDeTraduction initial value', () => {
        const formGroup = service.createDemandeDeTraductionFormGroup();

        const demandeDeTraduction = service.getDemandeDeTraduction(formGroup) as any;

        expect(demandeDeTraduction).toMatchObject({});
      });

      it('should return IDemandeDeTraduction', () => {
        const formGroup = service.createDemandeDeTraductionFormGroup(sampleWithRequiredData);

        const demandeDeTraduction = service.getDemandeDeTraduction(formGroup) as any;

        expect(demandeDeTraduction).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDemandeDeTraduction should not enable id FormControl', () => {
        const formGroup = service.createDemandeDeTraductionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDemandeDeTraduction should disable id FormControl', () => {
        const formGroup = service.createDemandeDeTraductionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
