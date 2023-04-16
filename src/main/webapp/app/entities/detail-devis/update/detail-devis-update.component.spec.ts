import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DetailDevisFormService } from './detail-devis-form.service';
import { DetailDevisService } from '../service/detail-devis.service';
import { IDetailDevis } from '../detail-devis.model';
import { IDevis } from 'app/entities/devis/devis.model';
import { DevisService } from 'app/entities/devis/service/devis.service';

import { DetailDevisUpdateComponent } from './detail-devis-update.component';

describe('DetailDevis Management Update Component', () => {
  let comp: DetailDevisUpdateComponent;
  let fixture: ComponentFixture<DetailDevisUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let detailDevisFormService: DetailDevisFormService;
  let detailDevisService: DetailDevisService;
  let devisService: DevisService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DetailDevisUpdateComponent],
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
      .overrideTemplate(DetailDevisUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DetailDevisUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    detailDevisFormService = TestBed.inject(DetailDevisFormService);
    detailDevisService = TestBed.inject(DetailDevisService);
    devisService = TestBed.inject(DevisService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Devis query and add missing value', () => {
      const detailDevis: IDetailDevis = { id: 456 };
      const devis: IDevis = { id: 78307 };
      detailDevis.devis = devis;

      const devisCollection: IDevis[] = [{ id: 82561 }];
      jest.spyOn(devisService, 'query').mockReturnValue(of(new HttpResponse({ body: devisCollection })));
      const additionalDevis = [devis];
      const expectedCollection: IDevis[] = [...additionalDevis, ...devisCollection];
      jest.spyOn(devisService, 'addDevisToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detailDevis });
      comp.ngOnInit();

      expect(devisService.query).toHaveBeenCalled();
      expect(devisService.addDevisToCollectionIfMissing).toHaveBeenCalledWith(
        devisCollection,
        ...additionalDevis.map(expect.objectContaining)
      );
      expect(comp.devisSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const detailDevis: IDetailDevis = { id: 456 };
      const devis: IDevis = { id: 85988 };
      detailDevis.devis = devis;

      activatedRoute.data = of({ detailDevis });
      comp.ngOnInit();

      expect(comp.devisSharedCollection).toContain(devis);
      expect(comp.detailDevis).toEqual(detailDevis);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetailDevis>>();
      const detailDevis = { id: 123 };
      jest.spyOn(detailDevisFormService, 'getDetailDevis').mockReturnValue(detailDevis);
      jest.spyOn(detailDevisService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailDevis });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detailDevis }));
      saveSubject.complete();

      // THEN
      expect(detailDevisFormService.getDetailDevis).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(detailDevisService.update).toHaveBeenCalledWith(expect.objectContaining(detailDevis));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetailDevis>>();
      const detailDevis = { id: 123 };
      jest.spyOn(detailDevisFormService, 'getDetailDevis').mockReturnValue({ id: null });
      jest.spyOn(detailDevisService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailDevis: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detailDevis }));
      saveSubject.complete();

      // THEN
      expect(detailDevisFormService.getDetailDevis).toHaveBeenCalled();
      expect(detailDevisService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetailDevis>>();
      const detailDevis = { id: 123 };
      jest.spyOn(detailDevisService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailDevis });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(detailDevisService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDevis', () => {
      it('Should forward to devisService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(devisService, 'compareDevis');
        comp.compareDevis(entity, entity2);
        expect(devisService.compareDevis).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
