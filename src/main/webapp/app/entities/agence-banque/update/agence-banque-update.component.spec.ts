import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AgenceBanqueFormService } from './agence-banque-form.service';
import { AgenceBanqueService } from '../service/agence-banque.service';
import { IAgenceBanque } from '../agence-banque.model';
import { IBanque } from 'app/entities/banque/banque.model';
import { BanqueService } from 'app/entities/banque/service/banque.service';

import { AgenceBanqueUpdateComponent } from './agence-banque-update.component';

describe('AgenceBanque Management Update Component', () => {
  let comp: AgenceBanqueUpdateComponent;
  let fixture: ComponentFixture<AgenceBanqueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let agenceBanqueFormService: AgenceBanqueFormService;
  let agenceBanqueService: AgenceBanqueService;
  let banqueService: BanqueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AgenceBanqueUpdateComponent],
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
      .overrideTemplate(AgenceBanqueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AgenceBanqueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    agenceBanqueFormService = TestBed.inject(AgenceBanqueFormService);
    agenceBanqueService = TestBed.inject(AgenceBanqueService);
    banqueService = TestBed.inject(BanqueService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Banque query and add missing value', () => {
      const agenceBanque: IAgenceBanque = { id: 456 };
      const banque: IBanque = { id: 23526 };
      agenceBanque.banque = banque;

      const banqueCollection: IBanque[] = [{ id: 14382 }];
      jest.spyOn(banqueService, 'query').mockReturnValue(of(new HttpResponse({ body: banqueCollection })));
      const additionalBanques = [banque];
      const expectedCollection: IBanque[] = [...additionalBanques, ...banqueCollection];
      jest.spyOn(banqueService, 'addBanqueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agenceBanque });
      comp.ngOnInit();

      expect(banqueService.query).toHaveBeenCalled();
      expect(banqueService.addBanqueToCollectionIfMissing).toHaveBeenCalledWith(
        banqueCollection,
        ...additionalBanques.map(expect.objectContaining)
      );
      expect(comp.banquesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const agenceBanque: IAgenceBanque = { id: 456 };
      const banque: IBanque = { id: 44443 };
      agenceBanque.banque = banque;

      activatedRoute.data = of({ agenceBanque });
      comp.ngOnInit();

      expect(comp.banquesSharedCollection).toContain(banque);
      expect(comp.agenceBanque).toEqual(agenceBanque);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgenceBanque>>();
      const agenceBanque = { id: 123 };
      jest.spyOn(agenceBanqueFormService, 'getAgenceBanque').mockReturnValue(agenceBanque);
      jest.spyOn(agenceBanqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agenceBanque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agenceBanque }));
      saveSubject.complete();

      // THEN
      expect(agenceBanqueFormService.getAgenceBanque).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(agenceBanqueService.update).toHaveBeenCalledWith(expect.objectContaining(agenceBanque));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgenceBanque>>();
      const agenceBanque = { id: 123 };
      jest.spyOn(agenceBanqueFormService, 'getAgenceBanque').mockReturnValue({ id: null });
      jest.spyOn(agenceBanqueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agenceBanque: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agenceBanque }));
      saveSubject.complete();

      // THEN
      expect(agenceBanqueFormService.getAgenceBanque).toHaveBeenCalled();
      expect(agenceBanqueService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgenceBanque>>();
      const agenceBanque = { id: 123 };
      jest.spyOn(agenceBanqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agenceBanque });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(agenceBanqueService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareBanque', () => {
      it('Should forward to banqueService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(banqueService, 'compareBanque');
        comp.compareBanque(entity, entity2);
        expect(banqueService.compareBanque).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
