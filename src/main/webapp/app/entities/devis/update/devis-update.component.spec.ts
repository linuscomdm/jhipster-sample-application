import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DevisFormService } from './devis-form.service';
import { DevisService } from '../service/devis.service';
import { IDevis } from '../devis.model';
import { IPrestataire } from 'app/entities/prestataire/prestataire.model';
import { PrestataireService } from 'app/entities/prestataire/service/prestataire.service';
import { IDemandeDeTraduction } from 'app/entities/demande-de-traduction/demande-de-traduction.model';
import { DemandeDeTraductionService } from 'app/entities/demande-de-traduction/service/demande-de-traduction.service';

import { DevisUpdateComponent } from './devis-update.component';

describe('Devis Management Update Component', () => {
  let comp: DevisUpdateComponent;
  let fixture: ComponentFixture<DevisUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let devisFormService: DevisFormService;
  let devisService: DevisService;
  let prestataireService: PrestataireService;
  let demandeDeTraductionService: DemandeDeTraductionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DevisUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DevisUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DevisUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    devisFormService = TestBed.inject(DevisFormService);
    devisService = TestBed.inject(DevisService);
    prestataireService = TestBed.inject(PrestataireService);
    demandeDeTraductionService = TestBed.inject(DemandeDeTraductionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Prestataire query and add missing value', () => {
      const devis: IDevis = { id: 456 };
      const prestataire: IPrestataire = { id: 1731 };
      devis.prestataire = prestataire;

      const prestataireCollection: IPrestataire[] = [{ id: 97384 }];
      jest.spyOn(prestataireService, 'query').mockReturnValue(of(new HttpResponse({ body: prestataireCollection })));
      const additionalPrestataires = [prestataire];
      const expectedCollection: IPrestataire[] = [...additionalPrestataires, ...prestataireCollection];
      jest.spyOn(prestataireService, 'addPrestataireToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ devis });
      comp.ngOnInit();

      expect(prestataireService.query).toHaveBeenCalled();
      expect(prestataireService.addPrestataireToCollectionIfMissing).toHaveBeenCalledWith(
        prestataireCollection,
        ...additionalPrestataires.map(expect.objectContaining)
      );
      expect(comp.prestatairesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DemandeDeTraduction query and add missing value', () => {
      const devis: IDevis = { id: 456 };
      const demandeDeTraduction: IDemandeDeTraduction = { id: 92888 };
      devis.demandeDeTraduction = demandeDeTraduction;

      const demandeDeTraductionCollection: IDemandeDeTraduction[] = [{ id: 94542 }];
      jest.spyOn(demandeDeTraductionService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeDeTraductionCollection })));
      const additionalDemandeDeTraductions = [demandeDeTraduction];
      const expectedCollection: IDemandeDeTraduction[] = [...additionalDemandeDeTraductions, ...demandeDeTraductionCollection];
      jest.spyOn(demandeDeTraductionService, 'addDemandeDeTraductionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ devis });
      comp.ngOnInit();

      expect(demandeDeTraductionService.query).toHaveBeenCalled();
      expect(demandeDeTraductionService.addDemandeDeTraductionToCollectionIfMissing).toHaveBeenCalledWith(
        demandeDeTraductionCollection,
        ...additionalDemandeDeTraductions.map(expect.objectContaining)
      );
      expect(comp.demandeDeTraductionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const devis: IDevis = { id: 456 };
      const prestataire: IPrestataire = { id: 88808 };
      devis.prestataire = prestataire;
      const demandeDeTraduction: IDemandeDeTraduction = { id: 54875 };
      devis.demandeDeTraduction = demandeDeTraduction;

      activatedRoute.data = of({ devis });
      comp.ngOnInit();

      expect(comp.prestatairesSharedCollection).toContain(prestataire);
      expect(comp.demandeDeTraductionsSharedCollection).toContain(demandeDeTraduction);
      expect(comp.devis).toEqual(devis);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDevis>>();
      const devis = { id: 123 };
      jest.spyOn(devisFormService, 'getDevis').mockReturnValue(devis);
      jest.spyOn(devisService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ devis });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: devis }));
      saveSubject.complete();

      // THEN
      expect(devisFormService.getDevis).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(devisService.update).toHaveBeenCalledWith(expect.objectContaining(devis));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDevis>>();
      const devis = { id: 123 };
      jest.spyOn(devisFormService, 'getDevis').mockReturnValue({ id: null });
      jest.spyOn(devisService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ devis: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: devis }));
      saveSubject.complete();

      // THEN
      expect(devisFormService.getDevis).toHaveBeenCalled();
      expect(devisService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDevis>>();
      const devis = { id: 123 };
      jest.spyOn(devisService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ devis });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(devisService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePrestataire', () => {
      it('Should forward to prestataireService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(prestataireService, 'comparePrestataire');
        comp.comparePrestataire(entity, entity2);
        expect(prestataireService.comparePrestataire).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDemandeDeTraduction', () => {
      it('Should forward to demandeDeTraductionService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(demandeDeTraductionService, 'compareDemandeDeTraduction');
        comp.compareDemandeDeTraduction(entity, entity2);
        expect(demandeDeTraductionService.compareDemandeDeTraduction).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
