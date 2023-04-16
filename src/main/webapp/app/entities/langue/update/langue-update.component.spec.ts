import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LangueFormService } from './langue-form.service';
import { LangueService } from '../service/langue.service';
import { ILangue } from '../langue.model';
import { IDocumentATraduire } from 'app/entities/document-a-traduire/document-a-traduire.model';
import { DocumentATraduireService } from 'app/entities/document-a-traduire/service/document-a-traduire.service';

import { LangueUpdateComponent } from './langue-update.component';

describe('Langue Management Update Component', () => {
  let comp: LangueUpdateComponent;
  let fixture: ComponentFixture<LangueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let langueFormService: LangueFormService;
  let langueService: LangueService;
  let documentATraduireService: DocumentATraduireService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LangueUpdateComponent],
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
      .overrideTemplate(LangueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LangueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    langueFormService = TestBed.inject(LangueFormService);
    langueService = TestBed.inject(LangueService);
    documentATraduireService = TestBed.inject(DocumentATraduireService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DocumentATraduire query and add missing value', () => {
      const langue: ILangue = { id: 456 };
      const docTraductions: IDocumentATraduire = { id: 73735 };
      langue.docTraductions = docTraductions;

      const documentATraduireCollection: IDocumentATraduire[] = [{ id: 4910 }];
      jest.spyOn(documentATraduireService, 'query').mockReturnValue(of(new HttpResponse({ body: documentATraduireCollection })));
      const additionalDocumentATraduires = [docTraductions];
      const expectedCollection: IDocumentATraduire[] = [...additionalDocumentATraduires, ...documentATraduireCollection];
      jest.spyOn(documentATraduireService, 'addDocumentATraduireToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ langue });
      comp.ngOnInit();

      expect(documentATraduireService.query).toHaveBeenCalled();
      expect(documentATraduireService.addDocumentATraduireToCollectionIfMissing).toHaveBeenCalledWith(
        documentATraduireCollection,
        ...additionalDocumentATraduires.map(expect.objectContaining)
      );
      expect(comp.documentATraduiresSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const langue: ILangue = { id: 456 };
      const docTraductions: IDocumentATraduire = { id: 48036 };
      langue.docTraductions = docTraductions;

      activatedRoute.data = of({ langue });
      comp.ngOnInit();

      expect(comp.documentATraduiresSharedCollection).toContain(docTraductions);
      expect(comp.langue).toEqual(langue);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILangue>>();
      const langue = { id: 123 };
      jest.spyOn(langueFormService, 'getLangue').mockReturnValue(langue);
      jest.spyOn(langueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ langue });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: langue }));
      saveSubject.complete();

      // THEN
      expect(langueFormService.getLangue).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(langueService.update).toHaveBeenCalledWith(expect.objectContaining(langue));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILangue>>();
      const langue = { id: 123 };
      jest.spyOn(langueFormService, 'getLangue').mockReturnValue({ id: null });
      jest.spyOn(langueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ langue: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: langue }));
      saveSubject.complete();

      // THEN
      expect(langueFormService.getLangue).toHaveBeenCalled();
      expect(langueService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILangue>>();
      const langue = { id: 123 };
      jest.spyOn(langueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ langue });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(langueService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDocumentATraduire', () => {
      it('Should forward to documentATraduireService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(documentATraduireService, 'compareDocumentATraduire');
        comp.compareDocumentATraduire(entity, entity2);
        expect(documentATraduireService.compareDocumentATraduire).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
