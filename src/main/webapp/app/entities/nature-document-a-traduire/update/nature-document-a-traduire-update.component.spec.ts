import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NatureDocumentATraduireFormService } from './nature-document-a-traduire-form.service';
import { NatureDocumentATraduireService } from '../service/nature-document-a-traduire.service';
import { INatureDocumentATraduire } from '../nature-document-a-traduire.model';

import { NatureDocumentATraduireUpdateComponent } from './nature-document-a-traduire-update.component';

describe('NatureDocumentATraduire Management Update Component', () => {
  let comp: NatureDocumentATraduireUpdateComponent;
  let fixture: ComponentFixture<NatureDocumentATraduireUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let natureDocumentATraduireFormService: NatureDocumentATraduireFormService;
  let natureDocumentATraduireService: NatureDocumentATraduireService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NatureDocumentATraduireUpdateComponent],
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
      .overrideTemplate(NatureDocumentATraduireUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NatureDocumentATraduireUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    natureDocumentATraduireFormService = TestBed.inject(NatureDocumentATraduireFormService);
    natureDocumentATraduireService = TestBed.inject(NatureDocumentATraduireService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const natureDocumentATraduire: INatureDocumentATraduire = { id: 456 };

      activatedRoute.data = of({ natureDocumentATraduire });
      comp.ngOnInit();

      expect(comp.natureDocumentATraduire).toEqual(natureDocumentATraduire);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INatureDocumentATraduire>>();
      const natureDocumentATraduire = { id: 123 };
      jest.spyOn(natureDocumentATraduireFormService, 'getNatureDocumentATraduire').mockReturnValue(natureDocumentATraduire);
      jest.spyOn(natureDocumentATraduireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureDocumentATraduire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureDocumentATraduire }));
      saveSubject.complete();

      // THEN
      expect(natureDocumentATraduireFormService.getNatureDocumentATraduire).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(natureDocumentATraduireService.update).toHaveBeenCalledWith(expect.objectContaining(natureDocumentATraduire));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INatureDocumentATraduire>>();
      const natureDocumentATraduire = { id: 123 };
      jest.spyOn(natureDocumentATraduireFormService, 'getNatureDocumentATraduire').mockReturnValue({ id: null });
      jest.spyOn(natureDocumentATraduireService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureDocumentATraduire: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: natureDocumentATraduire }));
      saveSubject.complete();

      // THEN
      expect(natureDocumentATraduireFormService.getNatureDocumentATraduire).toHaveBeenCalled();
      expect(natureDocumentATraduireService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INatureDocumentATraduire>>();
      const natureDocumentATraduire = { id: 123 };
      jest.spyOn(natureDocumentATraduireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ natureDocumentATraduire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(natureDocumentATraduireService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
