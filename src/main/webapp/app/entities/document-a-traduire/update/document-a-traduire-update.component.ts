import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DocumentATraduireFormService, DocumentATraduireFormGroup } from './document-a-traduire-form.service';
import { IDocumentATraduire } from '../document-a-traduire.model';
import { DocumentATraduireService } from '../service/document-a-traduire.service';
import { ILangue } from 'app/entities/langue/langue.model';
import { LangueService } from 'app/entities/langue/service/langue.service';
import { INatureDocumentATraduire } from 'app/entities/nature-document-a-traduire/nature-document-a-traduire.model';
import { NatureDocumentATraduireService } from 'app/entities/nature-document-a-traduire/service/nature-document-a-traduire.service';
import { IDemandeDeTraduction } from 'app/entities/demande-de-traduction/demande-de-traduction.model';
import { DemandeDeTraductionService } from 'app/entities/demande-de-traduction/service/demande-de-traduction.service';

@Component({
  selector: 'jhi-document-a-traduire-update',
  templateUrl: './document-a-traduire-update.component.html',
})
export class DocumentATraduireUpdateComponent implements OnInit {
  isSaving = false;
  documentATraduire: IDocumentATraduire | null = null;

  languesSharedCollection: ILangue[] = [];
  natureDocumentATraduiresSharedCollection: INatureDocumentATraduire[] = [];
  demandeDeTraductionsSharedCollection: IDemandeDeTraduction[] = [];

  editForm: DocumentATraduireFormGroup = this.documentATraduireFormService.createDocumentATraduireFormGroup();

  constructor(
    protected documentATraduireService: DocumentATraduireService,
    protected documentATraduireFormService: DocumentATraduireFormService,
    protected langueService: LangueService,
    protected natureDocumentATraduireService: NatureDocumentATraduireService,
    protected demandeDeTraductionService: DemandeDeTraductionService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLangue = (o1: ILangue | null, o2: ILangue | null): boolean => this.langueService.compareLangue(o1, o2);

  compareNatureDocumentATraduire = (o1: INatureDocumentATraduire | null, o2: INatureDocumentATraduire | null): boolean =>
    this.natureDocumentATraduireService.compareNatureDocumentATraduire(o1, o2);

  compareDemandeDeTraduction = (o1: IDemandeDeTraduction | null, o2: IDemandeDeTraduction | null): boolean =>
    this.demandeDeTraductionService.compareDemandeDeTraduction(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentATraduire }) => {
      this.documentATraduire = documentATraduire;
      if (documentATraduire) {
        this.updateForm(documentATraduire);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documentATraduire = this.documentATraduireFormService.getDocumentATraduire(this.editForm);
    if (documentATraduire.id !== null) {
      this.subscribeToSaveResponse(this.documentATraduireService.update(documentATraduire));
    } else {
      this.subscribeToSaveResponse(this.documentATraduireService.create(documentATraduire));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentATraduire>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(documentATraduire: IDocumentATraduire): void {
    this.documentATraduire = documentATraduire;
    this.documentATraduireFormService.resetForm(this.editForm, documentATraduire);

    this.languesSharedCollection = this.langueService.addLangueToCollectionIfMissing<ILangue>(
      this.languesSharedCollection,
      documentATraduire.langueDestination
    );
    this.natureDocumentATraduiresSharedCollection =
      this.natureDocumentATraduireService.addNatureDocumentATraduireToCollectionIfMissing<INatureDocumentATraduire>(
        this.natureDocumentATraduiresSharedCollection,
        documentATraduire.typeDocument
      );
    this.demandeDeTraductionsSharedCollection =
      this.demandeDeTraductionService.addDemandeDeTraductionToCollectionIfMissing<IDemandeDeTraduction>(
        this.demandeDeTraductionsSharedCollection,
        documentATraduire.demandeTraductions
      );
  }

  protected loadRelationshipsOptions(): void {
    this.langueService
      .query()
      .pipe(map((res: HttpResponse<ILangue[]>) => res.body ?? []))
      .pipe(
        map((langues: ILangue[]) =>
          this.langueService.addLangueToCollectionIfMissing<ILangue>(langues, this.documentATraduire?.langueDestination)
        )
      )
      .subscribe((langues: ILangue[]) => (this.languesSharedCollection = langues));

    this.natureDocumentATraduireService
      .query()
      .pipe(map((res: HttpResponse<INatureDocumentATraduire[]>) => res.body ?? []))
      .pipe(
        map((natureDocumentATraduires: INatureDocumentATraduire[]) =>
          this.natureDocumentATraduireService.addNatureDocumentATraduireToCollectionIfMissing<INatureDocumentATraduire>(
            natureDocumentATraduires,
            this.documentATraduire?.typeDocument
          )
        )
      )
      .subscribe(
        (natureDocumentATraduires: INatureDocumentATraduire[]) => (this.natureDocumentATraduiresSharedCollection = natureDocumentATraduires)
      );

    this.demandeDeTraductionService
      .query()
      .pipe(map((res: HttpResponse<IDemandeDeTraduction[]>) => res.body ?? []))
      .pipe(
        map((demandeDeTraductions: IDemandeDeTraduction[]) =>
          this.demandeDeTraductionService.addDemandeDeTraductionToCollectionIfMissing<IDemandeDeTraduction>(
            demandeDeTraductions,
            this.documentATraduire?.demandeTraductions
          )
        )
      )
      .subscribe((demandeDeTraductions: IDemandeDeTraduction[]) => (this.demandeDeTraductionsSharedCollection = demandeDeTraductions));
  }
}
