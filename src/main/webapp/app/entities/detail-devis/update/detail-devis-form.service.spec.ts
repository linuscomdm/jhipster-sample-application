import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../detail-devis.test-samples';

import { DetailDevisFormService } from './detail-devis-form.service';

describe('DetailDevis Form Service', () => {
  let service: DetailDevisFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DetailDevisFormService);
  });

  describe('Service methods', () => {
    describe('createDetailDevisFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDetailDevisFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            qte: expect.any(Object),
            prixUnitaire: expect.any(Object),
            prixTotal: expect.any(Object),
            etat: expect.any(Object),
            devis: expect.any(Object),
          })
        );
      });

      it('passing IDetailDevis should create a new form with FormGroup', () => {
        const formGroup = service.createDetailDevisFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            qte: expect.any(Object),
            prixUnitaire: expect.any(Object),
            prixTotal: expect.any(Object),
            etat: expect.any(Object),
            devis: expect.any(Object),
          })
        );
      });
    });

    describe('getDetailDevis', () => {
      it('should return NewDetailDevis for default DetailDevis initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDetailDevisFormGroup(sampleWithNewData);

        const detailDevis = service.getDetailDevis(formGroup) as any;

        expect(detailDevis).toMatchObject(sampleWithNewData);
      });

      it('should return NewDetailDevis for empty DetailDevis initial value', () => {
        const formGroup = service.createDetailDevisFormGroup();

        const detailDevis = service.getDetailDevis(formGroup) as any;

        expect(detailDevis).toMatchObject({});
      });

      it('should return IDetailDevis', () => {
        const formGroup = service.createDetailDevisFormGroup(sampleWithRequiredData);

        const detailDevis = service.getDetailDevis(formGroup) as any;

        expect(detailDevis).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDetailDevis should not enable id FormControl', () => {
        const formGroup = service.createDetailDevisFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDetailDevis should disable id FormControl', () => {
        const formGroup = service.createDetailDevisFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
