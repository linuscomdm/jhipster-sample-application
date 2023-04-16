import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TerjemThequeFormService } from './terjem-theque-form.service';
import { TerjemThequeService } from '../service/terjem-theque.service';
import { ITerjemTheque } from '../terjem-theque.model';
import { IPrestataire } from 'app/entities/prestataire/prestataire.model';
import { PrestataireService } from 'app/entities/prestataire/service/prestataire.service';
import { IDemandeur } from 'app/entities/demandeur/demandeur.model';
import { DemandeurService } from 'app/entities/demandeur/service/demandeur.service';

import { TerjemThequeUpdateComponent } from './terjem-theque-update.component';

describe('TerjemTheque Management Update Component', () => {
  let comp: TerjemThequeUpdateComponent;
  let fixture: ComponentFixture<TerjemThequeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let terjemThequeFormService: TerjemThequeFormService;
  let terjemThequeService: TerjemThequeService;
  let prestataireService: PrestataireService;
  let demandeurService: DemandeurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TerjemThequeUpdateComponent],
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
      .overrideTemplate(TerjemThequeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TerjemThequeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    terjemThequeFormService = TestBed.inject(TerjemThequeFormService);
    terjemThequeService = TestBed.inject(TerjemThequeService);
    prestataireService = TestBed.inject(PrestataireService);
    demandeurService = TestBed.inject(DemandeurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Prestataire query and add missing value', () => {
      const terjemTheque: ITerjemTheque = { id: 456 };
      const prestataire: IPrestataire = { id: 18400 };
      terjemTheque.prestataire = prestataire;

      const prestataireCollection: IPrestataire[] = [{ id: 98623 }];
      jest.spyOn(prestataireService, 'query').mockReturnValue(of(new HttpResponse({ body: prestataireCollection })));
      const additionalPrestataires = [prestataire];
      const expectedCollection: IPrestataire[] = [...additionalPrestataires, ...prestataireCollection];
      jest.spyOn(prestataireService, 'addPrestataireToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ terjemTheque });
      comp.ngOnInit();

      expect(prestataireService.query).toHaveBeenCalled();
      expect(prestataireService.addPrestataireToCollectionIfMissing).toHaveBeenCalledWith(
        prestataireCollection,
        ...additionalPrestataires.map(expect.objectContaining)
      );
      expect(comp.prestatairesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Demandeur query and add missing value', () => {
      const terjemTheque: ITerjemTheque = { id: 456 };
      const demandeur: IDemandeur = { id: 39828 };
      terjemTheque.demandeur = demandeur;

      const demandeurCollection: IDemandeur[] = [{ id: 75557 }];
      jest.spyOn(demandeurService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeurCollection })));
      const additionalDemandeurs = [demandeur];
      const expectedCollection: IDemandeur[] = [...additionalDemandeurs, ...demandeurCollection];
      jest.spyOn(demandeurService, 'addDemandeurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ terjemTheque });
      comp.ngOnInit();

      expect(demandeurService.query).toHaveBeenCalled();
      expect(demandeurService.addDemandeurToCollectionIfMissing).toHaveBeenCalledWith(
        demandeurCollection,
        ...additionalDemandeurs.map(expect.objectContaining)
      );
      expect(comp.demandeursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const terjemTheque: ITerjemTheque = { id: 456 };
      const prestataire: IPrestataire = { id: 93723 };
      terjemTheque.prestataire = prestataire;
      const demandeur: IDemandeur = { id: 26668 };
      terjemTheque.demandeur = demandeur;

      activatedRoute.data = of({ terjemTheque });
      comp.ngOnInit();

      expect(comp.prestatairesSharedCollection).toContain(prestataire);
      expect(comp.demandeursSharedCollection).toContain(demandeur);
      expect(comp.terjemTheque).toEqual(terjemTheque);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITerjemTheque>>();
      const terjemTheque = { id: 123 };
      jest.spyOn(terjemThequeFormService, 'getTerjemTheque').mockReturnValue(terjemTheque);
      jest.spyOn(terjemThequeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ terjemTheque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: terjemTheque }));
      saveSubject.complete();

      // THEN
      expect(terjemThequeFormService.getTerjemTheque).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(terjemThequeService.update).toHaveBeenCalledWith(expect.objectContaining(terjemTheque));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITerjemTheque>>();
      const terjemTheque = { id: 123 };
      jest.spyOn(terjemThequeFormService, 'getTerjemTheque').mockReturnValue({ id: null });
      jest.spyOn(terjemThequeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ terjemTheque: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: terjemTheque }));
      saveSubject.complete();

      // THEN
      expect(terjemThequeFormService.getTerjemTheque).toHaveBeenCalled();
      expect(terjemThequeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITerjemTheque>>();
      const terjemTheque = { id: 123 };
      jest.spyOn(terjemThequeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ terjemTheque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(terjemThequeService.update).toHaveBeenCalled();
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

    describe('compareDemandeur', () => {
      it('Should forward to demandeurService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(demandeurService, 'compareDemandeur');
        comp.compareDemandeur(entity, entity2);
        expect(demandeurService.compareDemandeur).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
