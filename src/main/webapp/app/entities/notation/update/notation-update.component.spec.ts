import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NotationFormService } from './notation-form.service';
import { NotationService } from '../service/notation.service';
import { INotation } from '../notation.model';
import { IDemandeur } from 'app/entities/demandeur/demandeur.model';
import { DemandeurService } from 'app/entities/demandeur/service/demandeur.service';
import { IPrestataire } from 'app/entities/prestataire/prestataire.model';
import { PrestataireService } from 'app/entities/prestataire/service/prestataire.service';

import { NotationUpdateComponent } from './notation-update.component';

describe('Notation Management Update Component', () => {
  let comp: NotationUpdateComponent;
  let fixture: ComponentFixture<NotationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let notationFormService: NotationFormService;
  let notationService: NotationService;
  let demandeurService: DemandeurService;
  let prestataireService: PrestataireService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NotationUpdateComponent],
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
      .overrideTemplate(NotationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NotationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    notationFormService = TestBed.inject(NotationFormService);
    notationService = TestBed.inject(NotationService);
    demandeurService = TestBed.inject(DemandeurService);
    prestataireService = TestBed.inject(PrestataireService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Demandeur query and add missing value', () => {
      const notation: INotation = { id: 456 };
      const demandeur: IDemandeur = { id: 38517 };
      notation.demandeur = demandeur;

      const demandeurCollection: IDemandeur[] = [{ id: 57755 }];
      jest.spyOn(demandeurService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeurCollection })));
      const additionalDemandeurs = [demandeur];
      const expectedCollection: IDemandeur[] = [...additionalDemandeurs, ...demandeurCollection];
      jest.spyOn(demandeurService, 'addDemandeurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ notation });
      comp.ngOnInit();

      expect(demandeurService.query).toHaveBeenCalled();
      expect(demandeurService.addDemandeurToCollectionIfMissing).toHaveBeenCalledWith(
        demandeurCollection,
        ...additionalDemandeurs.map(expect.objectContaining)
      );
      expect(comp.demandeursSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Prestataire query and add missing value', () => {
      const notation: INotation = { id: 456 };
      const prestataire: IPrestataire = { id: 37292 };
      notation.prestataire = prestataire;

      const prestataireCollection: IPrestataire[] = [{ id: 98607 }];
      jest.spyOn(prestataireService, 'query').mockReturnValue(of(new HttpResponse({ body: prestataireCollection })));
      const additionalPrestataires = [prestataire];
      const expectedCollection: IPrestataire[] = [...additionalPrestataires, ...prestataireCollection];
      jest.spyOn(prestataireService, 'addPrestataireToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ notation });
      comp.ngOnInit();

      expect(prestataireService.query).toHaveBeenCalled();
      expect(prestataireService.addPrestataireToCollectionIfMissing).toHaveBeenCalledWith(
        prestataireCollection,
        ...additionalPrestataires.map(expect.objectContaining)
      );
      expect(comp.prestatairesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const notation: INotation = { id: 456 };
      const demandeur: IDemandeur = { id: 99226 };
      notation.demandeur = demandeur;
      const prestataire: IPrestataire = { id: 91144 };
      notation.prestataire = prestataire;

      activatedRoute.data = of({ notation });
      comp.ngOnInit();

      expect(comp.demandeursSharedCollection).toContain(demandeur);
      expect(comp.prestatairesSharedCollection).toContain(prestataire);
      expect(comp.notation).toEqual(notation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotation>>();
      const notation = { id: 123 };
      jest.spyOn(notationFormService, 'getNotation').mockReturnValue(notation);
      jest.spyOn(notationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: notation }));
      saveSubject.complete();

      // THEN
      expect(notationFormService.getNotation).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(notationService.update).toHaveBeenCalledWith(expect.objectContaining(notation));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotation>>();
      const notation = { id: 123 };
      jest.spyOn(notationFormService, 'getNotation').mockReturnValue({ id: null });
      jest.spyOn(notationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notation: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: notation }));
      saveSubject.complete();

      // THEN
      expect(notationFormService.getNotation).toHaveBeenCalled();
      expect(notationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotation>>();
      const notation = { id: 123 };
      jest.spyOn(notationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(notationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDemandeur', () => {
      it('Should forward to demandeurService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(demandeurService, 'compareDemandeur');
        comp.compareDemandeur(entity, entity2);
        expect(demandeurService.compareDemandeur).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePrestataire', () => {
      it('Should forward to prestataireService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(prestataireService, 'comparePrestataire');
        comp.comparePrestataire(entity, entity2);
        expect(prestataireService.comparePrestataire).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
