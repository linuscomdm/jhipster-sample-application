import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DemandeDeTraductionFormService } from './demande-de-traduction-form.service';
import { DemandeDeTraductionService } from '../service/demande-de-traduction.service';
import { IDemandeDeTraduction } from '../demande-de-traduction.model';
import { IVille } from 'app/entities/ville/ville.model';
import { VilleService } from 'app/entities/ville/service/ville.service';
import { IDemandeur } from 'app/entities/demandeur/demandeur.model';
import { DemandeurService } from 'app/entities/demandeur/service/demandeur.service';

import { DemandeDeTraductionUpdateComponent } from './demande-de-traduction-update.component';

describe('DemandeDeTraduction Management Update Component', () => {
  let comp: DemandeDeTraductionUpdateComponent;
  let fixture: ComponentFixture<DemandeDeTraductionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let demandeDeTraductionFormService: DemandeDeTraductionFormService;
  let demandeDeTraductionService: DemandeDeTraductionService;
  let villeService: VilleService;
  let demandeurService: DemandeurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DemandeDeTraductionUpdateComponent],
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
      .overrideTemplate(DemandeDeTraductionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DemandeDeTraductionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    demandeDeTraductionFormService = TestBed.inject(DemandeDeTraductionFormService);
    demandeDeTraductionService = TestBed.inject(DemandeDeTraductionService);
    villeService = TestBed.inject(VilleService);
    demandeurService = TestBed.inject(DemandeurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Ville query and add missing value', () => {
      const demandeDeTraduction: IDemandeDeTraduction = { id: 456 };
      const ville: IVille = { id: 41161 };
      demandeDeTraduction.ville = ville;

      const villeCollection: IVille[] = [{ id: 89378 }];
      jest.spyOn(villeService, 'query').mockReturnValue(of(new HttpResponse({ body: villeCollection })));
      const additionalVilles = [ville];
      const expectedCollection: IVille[] = [...additionalVilles, ...villeCollection];
      jest.spyOn(villeService, 'addVilleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demandeDeTraduction });
      comp.ngOnInit();

      expect(villeService.query).toHaveBeenCalled();
      expect(villeService.addVilleToCollectionIfMissing).toHaveBeenCalledWith(
        villeCollection,
        ...additionalVilles.map(expect.objectContaining)
      );
      expect(comp.villesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Demandeur query and add missing value', () => {
      const demandeDeTraduction: IDemandeDeTraduction = { id: 456 };
      const demandeurService: IDemandeur = { id: 17406 };
      demandeDeTraduction.demandeurService = demandeurService;

      const demandeurCollection: IDemandeur[] = [{ id: 73663 }];
      jest.spyOn(demandeurService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeurCollection })));
      const additionalDemandeurs = [demandeurService];
      const expectedCollection: IDemandeur[] = [...additionalDemandeurs, ...demandeurCollection];
      jest.spyOn(demandeurService, 'addDemandeurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demandeDeTraduction });
      comp.ngOnInit();

      expect(demandeurService.query).toHaveBeenCalled();
      expect(demandeurService.addDemandeurToCollectionIfMissing).toHaveBeenCalledWith(
        demandeurCollection,
        ...additionalDemandeurs.map(expect.objectContaining)
      );
      expect(comp.demandeursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const demandeDeTraduction: IDemandeDeTraduction = { id: 456 };
      const ville: IVille = { id: 21820 };
      demandeDeTraduction.ville = ville;
      const demandeurService: IDemandeur = { id: 59299 };
      demandeDeTraduction.demandeurService = demandeurService;

      activatedRoute.data = of({ demandeDeTraduction });
      comp.ngOnInit();

      expect(comp.villesSharedCollection).toContain(ville);
      expect(comp.demandeursSharedCollection).toContain(demandeurService);
      expect(comp.demandeDeTraduction).toEqual(demandeDeTraduction);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemandeDeTraduction>>();
      const demandeDeTraduction = { id: 123 };
      jest.spyOn(demandeDeTraductionFormService, 'getDemandeDeTraduction').mockReturnValue(demandeDeTraduction);
      jest.spyOn(demandeDeTraductionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeDeTraduction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeDeTraduction }));
      saveSubject.complete();

      // THEN
      expect(demandeDeTraductionFormService.getDemandeDeTraduction).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(demandeDeTraductionService.update).toHaveBeenCalledWith(expect.objectContaining(demandeDeTraduction));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemandeDeTraduction>>();
      const demandeDeTraduction = { id: 123 };
      jest.spyOn(demandeDeTraductionFormService, 'getDemandeDeTraduction').mockReturnValue({ id: null });
      jest.spyOn(demandeDeTraductionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeDeTraduction: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeDeTraduction }));
      saveSubject.complete();

      // THEN
      expect(demandeDeTraductionFormService.getDemandeDeTraduction).toHaveBeenCalled();
      expect(demandeDeTraductionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemandeDeTraduction>>();
      const demandeDeTraduction = { id: 123 };
      jest.spyOn(demandeDeTraductionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeDeTraduction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(demandeDeTraductionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareVille', () => {
      it('Should forward to villeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(villeService, 'compareVille');
        comp.compareVille(entity, entity2);
        expect(villeService.compareVille).toHaveBeenCalledWith(entity, entity2);
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
