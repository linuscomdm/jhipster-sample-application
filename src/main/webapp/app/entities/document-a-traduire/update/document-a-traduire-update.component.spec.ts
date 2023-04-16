import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DocumentATraduireFormService } from './document-a-traduire-form.service';
import { DocumentATraduireService } from '../service/document-a-traduire.service';
import { IDocumentATraduire } from '../document-a-traduire.model';
import { ILangue } from 'app/entities/langue/langue.model';
import { LangueService } from 'app/entities/langue/service/langue.service';
import { INatureDocumentATraduire } from 'app/entities/nature-document-a-traduire/nature-document-a-traduire.model';
import { NatureDocumentATraduireService } from 'app/entities/nature-document-a-traduire/service/nature-document-a-traduire.service';
import { IDemandeDeTraduction } from 'app/entities/demande-de-traduction/demande-de-traduction.model';
import { DemandeDeTraductionService } from 'app/entities/demande-de-traduction/service/demande-de-traduction.service';

import { DocumentATraduireUpdateComponent } from './document-a-traduire-update.component';

describe('DocumentATraduire Management Update Component', () => {
  let comp: DocumentATraduireUpdateComponent;
  let fixture: ComponentFixture<DocumentATraduireUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let documentATraduireFormService: DocumentATraduireFormService;
  let documentATraduireService: DocumentATraduireService;
  let langueService: LangueService;
  let natureDocumentATraduireService: NatureDocumentATraduireService;
  let demandeDeTraductionService: DemandeDeTraductionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DocumentATraduireUpdateComponent],
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
      .overrideTemplate(DocumentATraduireUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DocumentATraduireUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    documentATraduireFormService = TestBed.inject(DocumentATraduireFormService);
    documentATraduireService = TestBed.inject(DocumentATraduireService);
    langueService = TestBed.inject(LangueService);
    natureDocumentATraduireService = TestBed.inject(NatureDocumentATraduireService);
    demandeDeTraductionService = TestBed.inject(DemandeDeTraductionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Langue query and add missing value', () => {
      const documentATraduire: IDocumentATraduire = { id: 456 };
      const langueDestination: ILangue = { id: 80861 };
      documentATraduire.langueDestination = langueDestination;

      const langueCollection: ILangue[] = [{ id: 23480 }];
      jest.spyOn(langueService, 'query').mockReturnValue(of(new HttpResponse({ body: langueCollection })));
      const additionalLangues = [langueDestination];
      const expectedCollection: ILangue[] = [...additionalLangues, ...langueCollection];
      jest.spyOn(langueService, 'addLangueToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ documentATraduire });
      comp.ngOnInit();

      expect(langueService.query).toHaveBeenCalled();
      expect(langueService.addLangueToCollectionIfMissing).toHaveBeenCalledWith(
        langueCollection,
        ...additionalLangues.map(expect.objectContaining)
      );
      expect(comp.languesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call NatureDocumentATraduire query and add missing value', () => {
      const documentATraduire: IDocumentATraduire = { id: 456 };
      const typeDocument: INatureDocumentATraduire = { id: 72832 };
      documentATraduire.typeDocument = typeDocument;

      const natureDocumentATraduireCollection: INatureDocumentATraduire[] = [{ id: 80938 }];
      jest
        .spyOn(natureDocumentATraduireService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: natureDocumentATraduireCollection })));
      const additionalNatureDocumentATraduires = [typeDocument];
      const expectedCollection: INatureDocumentATraduire[] = [...additionalNatureDocumentATraduires, ...natureDocumentATraduireCollection];
      jest.spyOn(natureDocumentATraduireService, 'addNatureDocumentATraduireToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ documentATraduire });
      comp.ngOnInit();

      expect(natureDocumentATraduireService.query).toHaveBeenCalled();
      expect(natureDocumentATraduireService.addNatureDocumentATraduireToCollectionIfMissing).toHaveBeenCalledWith(
        natureDocumentATraduireCollection,
        ...additionalNatureDocumentATraduires.map(expect.objectContaining)
      );
      expect(comp.natureDocumentATraduiresSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DemandeDeTraduction query and add missing value', () => {
      const documentATraduire: IDocumentATraduire = { id: 456 };
      const demandeTraductions: IDemandeDeTraduction = { id: 28558 };
      documentATraduire.demandeTraductions = demandeTraductions;

      const demandeDeTraductionCollection: IDemandeDeTraduction[] = [{ id: 25720 }];
      jest.spyOn(demandeDeTraductionService, 'query').mockReturnValue(of(new HttpResponse({ body: demandeDeTraductionCollection })));
      const additionalDemandeDeTraductions = [demandeTraductions];
      const expectedCollection: IDemandeDeTraduction[] = [...additionalDemandeDeTraductions, ...demandeDeTraductionCollection];
      jest.spyOn(demandeDeTraductionService, 'addDemandeDeTraductionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ documentATraduire });
      comp.ngOnInit();

      expect(demandeDeTraductionService.query).toHaveBeenCalled();
      expect(demandeDeTraductionService.addDemandeDeTraductionToCollectionIfMissing).toHaveBeenCalledWith(
        demandeDeTraductionCollection,
        ...additionalDemandeDeTraductions.map(expect.objectContaining)
      );
      expect(comp.demandeDeTraductionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const documentATraduire: IDocumentATraduire = { id: 456 };
      const langueDestination: ILangue = { id: 15660 };
      documentATraduire.langueDestination = langueDestination;
      const typeDocument: INatureDocumentATraduire = { id: 50469 };
      documentATraduire.typeDocument = typeDocument;
      const demandeTraductions: IDemandeDeTraduction = { id: 1209 };
      documentATraduire.demandeTraductions = demandeTraductions;

      activatedRoute.data = of({ documentATraduire });
      comp.ngOnInit();

      expect(comp.languesSharedCollection).toContain(langueDestination);
      expect(comp.natureDocumentATraduiresSharedCollection).toContain(typeDocument);
      expect(comp.demandeDeTraductionsSharedCollection).toContain(demandeTraductions);
      expect(comp.documentATraduire).toEqual(documentATraduire);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentATraduire>>();
      const documentATraduire = { id: 123 };
      jest.spyOn(documentATraduireFormService, 'getDocumentATraduire').mockReturnValue(documentATraduire);
      jest.spyOn(documentATraduireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentATraduire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentATraduire }));
      saveSubject.complete();

      // THEN
      expect(documentATraduireFormService.getDocumentATraduire).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(documentATraduireService.update).toHaveBeenCalledWith(expect.objectContaining(documentATraduire));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentATraduire>>();
      const documentATraduire = { id: 123 };
      jest.spyOn(documentATraduireFormService, 'getDocumentATraduire').mockReturnValue({ id: null });
      jest.spyOn(documentATraduireService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentATraduire: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: documentATraduire }));
      saveSubject.complete();

      // THEN
      expect(documentATraduireFormService.getDocumentATraduire).toHaveBeenCalled();
      expect(documentATraduireService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocumentATraduire>>();
      const documentATraduire = { id: 123 };
      jest.spyOn(documentATraduireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ documentATraduire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(documentATraduireService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLangue', () => {
      it('Should forward to langueService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(langueService, 'compareLangue');
        comp.compareLangue(entity, entity2);
        expect(langueService.compareLangue).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareNatureDocumentATraduire', () => {
      it('Should forward to natureDocumentATraduireService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(natureDocumentATraduireService, 'compareNatureDocumentATraduire');
        comp.compareNatureDocumentATraduire(entity, entity2);
        expect(natureDocumentATraduireService.compareNatureDocumentATraduire).toHaveBeenCalledWith(entity, entity2);
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
