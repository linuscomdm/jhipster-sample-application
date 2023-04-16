import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../prestataire.test-samples';

import { PrestataireFormService } from './prestataire-form.service';

describe('Prestataire Form Service', () => {
  let service: PrestataireFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrestataireFormService);
  });

  describe('Service methods', () => {
    describe('createPrestataireFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPrestataireFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            civilite: expect.any(Object),
            nom: expect.any(Object),
            prenom: expect.any(Object),
            nomCommercial: expect.any(Object),
            telephoneTravail: expect.any(Object),
            telephoneMobile: expect.any(Object),
            email: expect.any(Object),
            adresse: expect.any(Object),
            codePostal: expect.any(Object),
            photoDeProfil: expect.any(Object),
            numeroPieceIdentite: expect.any(Object),
            typeIdentiteProfessionnelle: expect.any(Object),
            rattachIdentitePro: expect.any(Object),
            coordonneesBancaires: expect.any(Object),
            titulaireDuCompte: expect.any(Object),
            ribOuRip: expect.any(Object),
            dateCreation: expect.any(Object),
            etat: expect.any(Object),
            user: expect.any(Object),
            banque: expect.any(Object),
            ville: expect.any(Object),
            prestaDdeTraductions: expect.any(Object),
            agenceBanque: expect.any(Object),
          })
        );
      });

      it('passing IPrestataire should create a new form with FormGroup', () => {
        const formGroup = service.createPrestataireFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            civilite: expect.any(Object),
            nom: expect.any(Object),
            prenom: expect.any(Object),
            nomCommercial: expect.any(Object),
            telephoneTravail: expect.any(Object),
            telephoneMobile: expect.any(Object),
            email: expect.any(Object),
            adresse: expect.any(Object),
            codePostal: expect.any(Object),
            photoDeProfil: expect.any(Object),
            numeroPieceIdentite: expect.any(Object),
            typeIdentiteProfessionnelle: expect.any(Object),
            rattachIdentitePro: expect.any(Object),
            coordonneesBancaires: expect.any(Object),
            titulaireDuCompte: expect.any(Object),
            ribOuRip: expect.any(Object),
            dateCreation: expect.any(Object),
            etat: expect.any(Object),
            user: expect.any(Object),
            banque: expect.any(Object),
            ville: expect.any(Object),
            prestaDdeTraductions: expect.any(Object),
            agenceBanque: expect.any(Object),
          })
        );
      });
    });

    describe('getPrestataire', () => {
      it('should return NewPrestataire for default Prestataire initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPrestataireFormGroup(sampleWithNewData);

        const prestataire = service.getPrestataire(formGroup) as any;

        expect(prestataire).toMatchObject(sampleWithNewData);
      });

      it('should return NewPrestataire for empty Prestataire initial value', () => {
        const formGroup = service.createPrestataireFormGroup();

        const prestataire = service.getPrestataire(formGroup) as any;

        expect(prestataire).toMatchObject({});
      });

      it('should return IPrestataire', () => {
        const formGroup = service.createPrestataireFormGroup(sampleWithRequiredData);

        const prestataire = service.getPrestataire(formGroup) as any;

        expect(prestataire).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPrestataire should not enable id FormControl', () => {
        const formGroup = service.createPrestataireFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPrestataire should disable id FormControl', () => {
        const formGroup = service.createPrestataireFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
