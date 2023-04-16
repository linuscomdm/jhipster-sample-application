import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CommentaireFormService } from './commentaire-form.service';
import { CommentaireService } from '../service/commentaire.service';
import { ICommentaire } from '../commentaire.model';
import { IDemandeDeTraduction } from 'app/entities/demande-de-traduction/demande-de-traduction.model';
import { DemandeDeTraductionService } from 'app/entities/demande-de-traduction/service/demande-de-traduction.service';
import { IPrestataire } from 'app/entities/prestataire/prestataire.model';
import { PrestataireService } from 'app/entities/prestataire/service/prestataire.service';
import { IDemandeur } from 'app/entities/demandeur/demandeur.model';
import { DemandeurService } from 'app/entities/demandeur/service/demandeur.service';

import { CommentaireUpdateComponent } from './commentaire-update.component';

describe('Commentaire Management Update Component', () => {
  let comp: CommentaireUpdateComponent;
  let fixture: ComponentFixture<CommentaireUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let commentaireFormService: CommentaireFormService;
  let commentaireService: CommentaireService;
  let demandeDeTraductionService: DemandeDeTraductionService;
  let prestataireService: PrestataireService;
  let demandeurService: DemandeurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CommentaireUpdateComponent],
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
      .overrideTemplate(CommentaireUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CommentaireUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    commentaireFormService = TestBed.inject(CommentaireFormService);
    commentaireService = TestBed.inject(CommentaireService);
    demandeDeTraductionService = TestBed.inject(DemandeDeTraductionService);
    prestataireService = TestBed.inject(PrestataireService);
    demandeurService = TestBed.inject(DemandeurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DemandeDeTraduction query and add missing value', () => {
      const commentaire: ICommentaire = { id: 456 };
      const demandeDeTraduction: IDemandeDeTraduction = { id: 49496 };
      commentaire.demandeDeTraduction = demandeDeTraduction;

      const demandeDeTraductionCollection: IDemandeDeTraduction[] = [{ id: 89938 }];
      jest.spyOn(demandeDeTraductionService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeDeTraductionCollection })));
      const additionalDemandeDeTraductions = [demandeDeTraduction];
      const expectedCollection: IDemandeDeTraduction[] = [...additionalDemandeDeTraductions, ...demandeDeTraductionCollection];
      jest.spyOn(demandeDeTraductionService, 'addDemandeDeTraductionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ commentaire });
      comp.ngOnInit();

      expect(demandeDeTraductionService.query).toHaveBeenCalled();
      expect(demandeDeTraductionService.addDemandeDeTraductionToCollectionIfMissing).toHaveBeenCalledWith(
        demandeDeTraductionCollection,
        ...additionalDemandeDeTraductions.map(expect.objectContaining)
      );
      expect(comp.demandeDeTraductionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Prestataire query and add missing value', () => {
      const commentaire: ICommentaire = { id: 456 };
      const prestataire: IPrestataire = { id: 89308 };
      commentaire.prestataire = prestataire;

      const prestataireCollection: IPrestataire[] = [{ id: 93821 }];
      jest.spyOn(prestataireService, 'query').mockReturnValue(of(new HttpResponse({ body: prestataireCollection })));
      const additionalPrestataires = [prestataire];
      const expectedCollection: IPrestataire[] = [...additionalPrestataires, ...prestataireCollection];
      jest.spyOn(prestataireService, 'addPrestataireToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ commentaire });
      comp.ngOnInit();

      expect(prestataireService.query).toHaveBeenCalled();
      expect(prestataireService.addPrestataireToCollectionIfMissing).toHaveBeenCalledWith(
        prestataireCollection,
        ...additionalPrestataires.map(expect.objectContaining)
      );
      expect(comp.prestatairesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Demandeur query and add missing value', () => {
      const commentaire: ICommentaire = { id: 456 };
      const demandeur: IDemandeur = { id: 57062 };
      commentaire.demandeur = demandeur;

      const demandeurCollection: IDemandeur[] = [{ id: 69120 }];
      jest.spyOn(demandeurService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeurCollection })));
      const additionalDemandeurs = [demandeur];
      const expectedCollection: IDemandeur[] = [...additionalDemandeurs, ...demandeurCollection];
      jest.spyOn(demandeurService, 'addDemandeurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ commentaire });
      comp.ngOnInit();

      expect(demandeurService.query).toHaveBeenCalled();
      expect(demandeurService.addDemandeurToCollectionIfMissing).toHaveBeenCalledWith(
        demandeurCollection,
        ...additionalDemandeurs.map(expect.objectContaining)
      );
      expect(comp.demandeursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const commentaire: ICommentaire = { id: 456 };
      const demandeDeTraduction: IDemandeDeTraduction = { id: 47154 };
      commentaire.demandeDeTraduction = demandeDeTraduction;
      const prestataire: IPrestataire = { id: 11650 };
      commentaire.prestataire = prestataire;
      const demandeur: IDemandeur = { id: 95355 };
      commentaire.demandeur = demandeur;

      activatedRoute.data = of({ commentaire });
      comp.ngOnInit();

      expect(comp.demandeDeTraductionsSharedCollection).toContain(demandeDeTraduction);
      expect(comp.prestatairesSharedCollection).toContain(prestataire);
      expect(comp.demandeursSharedCollection).toContain(demandeur);
      expect(comp.commentaire).toEqual(commentaire);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommentaire>>();
      const commentaire = { id: 123 };
      jest.spyOn(commentaireFormService, 'getCommentaire').mockReturnValue(commentaire);
      jest.spyOn(commentaireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commentaire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commentaire }));
      saveSubject.complete();

      // THEN
      expect(commentaireFormService.getCommentaire).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(commentaireService.update).toHaveBeenCalledWith(expect.objectContaining(commentaire));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommentaire>>();
      const commentaire = { id: 123 };
      jest.spyOn(commentaireFormService, 'getCommentaire').mockReturnValue({ id: null });
      jest.spyOn(commentaireService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commentaire: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commentaire }));
      saveSubject.complete();

      // THEN
      expect(commentaireFormService.getCommentaire).toHaveBeenCalled();
      expect(commentaireService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommentaire>>();
      const commentaire = { id: 123 };
      jest.spyOn(commentaireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commentaire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(commentaireService.update).toHaveBeenCalled();
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
