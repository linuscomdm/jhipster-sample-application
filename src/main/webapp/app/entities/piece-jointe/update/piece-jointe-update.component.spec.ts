import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PieceJointeFormService } from './piece-jointe-form.service';
import { PieceJointeService } from '../service/piece-jointe.service';
import { IPieceJointe } from '../piece-jointe.model';
import { IDemandeDeTraduction } from 'app/entities/demande-de-traduction/demande-de-traduction.model';
import { DemandeDeTraductionService } from 'app/entities/demande-de-traduction/service/demande-de-traduction.service';
import { IPrestataire } from 'app/entities/prestataire/prestataire.model';
import { PrestataireService } from 'app/entities/prestataire/service/prestataire.service';

import { PieceJointeUpdateComponent } from './piece-jointe-update.component';

describe('PieceJointe Management Update Component', () => {
  let comp: PieceJointeUpdateComponent;
  let fixture: ComponentFixture<PieceJointeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pieceJointeFormService: PieceJointeFormService;
  let pieceJointeService: PieceJointeService;
  let demandeDeTraductionService: DemandeDeTraductionService;
  let prestataireService: PrestataireService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PieceJointeUpdateComponent],
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
      .overrideTemplate(PieceJointeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PieceJointeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pieceJointeFormService = TestBed.inject(PieceJointeFormService);
    pieceJointeService = TestBed.inject(PieceJointeService);
    demandeDeTraductionService = TestBed.inject(DemandeDeTraductionService);
    prestataireService = TestBed.inject(PrestataireService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DemandeDeTraduction query and add missing value', () => {
      const pieceJointe: IPieceJointe = { id: 456 };
      const pjDdeTraductions: IDemandeDeTraduction = { id: 3728 };
      pieceJointe.pjDdeTraductions = pjDdeTraductions;

      const demandeDeTraductionCollection: IDemandeDeTraduction[] = [{ id: 22364 }];
      jest.spyOn(demandeDeTraductionService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeDeTraductionCollection })));
      const additionalDemandeDeTraductions = [pjDdeTraductions];
      const expectedCollection: IDemandeDeTraduction[] = [...additionalDemandeDeTraductions, ...demandeDeTraductionCollection];
      jest.spyOn(demandeDeTraductionService, 'addDemandeDeTraductionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pieceJointe });
      comp.ngOnInit();

      expect(demandeDeTraductionService.query).toHaveBeenCalled();
      expect(demandeDeTraductionService.addDemandeDeTraductionToCollectionIfMissing).toHaveBeenCalledWith(
        demandeDeTraductionCollection,
        ...additionalDemandeDeTraductions.map(expect.objectContaining)
      );
      expect(comp.demandeDeTraductionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Prestataire query and add missing value', () => {
      const pieceJointe: IPieceJointe = { id: 456 };
      const prestataire: IPrestataire = { id: 23176 };
      pieceJointe.prestataire = prestataire;

      const prestataireCollection: IPrestataire[] = [{ id: 84321 }];
      jest.spyOn(prestataireService, 'query').mockReturnValue(of(new HttpResponse({ body: prestataireCollection })));
      const additionalPrestataires = [prestataire];
      const expectedCollection: IPrestataire[] = [...additionalPrestataires, ...prestataireCollection];
      jest.spyOn(prestataireService, 'addPrestataireToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pieceJointe });
      comp.ngOnInit();

      expect(prestataireService.query).toHaveBeenCalled();
      expect(prestataireService.addPrestataireToCollectionIfMissing).toHaveBeenCalledWith(
        prestataireCollection,
        ...additionalPrestataires.map(expect.objectContaining)
      );
      expect(comp.prestatairesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pieceJointe: IPieceJointe = { id: 456 };
      const pjDdeTraductions: IDemandeDeTraduction = { id: 96252 };
      pieceJointe.pjDdeTraductions = pjDdeTraductions;
      const prestataire: IPrestataire = { id: 86880 };
      pieceJointe.prestataire = prestataire;

      activatedRoute.data = of({ pieceJointe });
      comp.ngOnInit();

      expect(comp.demandeDeTraductionsSharedCollection).toContain(pjDdeTraductions);
      expect(comp.prestatairesSharedCollection).toContain(prestataire);
      expect(comp.pieceJointe).toEqual(pieceJointe);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPieceJointe>>();
      const pieceJointe = { id: 123 };
      jest.spyOn(pieceJointeFormService, 'getPieceJointe').mockReturnValue(pieceJointe);
      jest.spyOn(pieceJointeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pieceJointe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pieceJointe }));
      saveSubject.complete();

      // THEN
      expect(pieceJointeFormService.getPieceJointe).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pieceJointeService.update).toHaveBeenCalledWith(expect.objectContaining(pieceJointe));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPieceJointe>>();
      const pieceJointe = { id: 123 };
      jest.spyOn(pieceJointeFormService, 'getPieceJointe').mockReturnValue({ id: null });
      jest.spyOn(pieceJointeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pieceJointe: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pieceJointe }));
      saveSubject.complete();

      // THEN
      expect(pieceJointeFormService.getPieceJointe).toHaveBeenCalled();
      expect(pieceJointeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPieceJointe>>();
      const pieceJointe = { id: 123 };
      jest.spyOn(pieceJointeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pieceJointe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pieceJointeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDemandeDeTraduction', () => {
      it('Should forward to demandeDeTraductionService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(demandeDeTraductionService, 'compareDemandeDeTraduction');
        comp.compareDemandeDeTraduction(entity, entity2);
        expect(demandeDeTraductionService.compareDemandeDeTraduction).toHaveBeenCalledWith(entity, entity2);
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
