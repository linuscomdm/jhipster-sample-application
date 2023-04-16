import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LangueFormService, LangueFormGroup } from './langue-form.service';
import { ILangue } from '../langue.model';
import { LangueService } from '../service/langue.service';
import { IDocumentATraduire } from 'app/entities/document-a-traduire/document-a-traduire.model';
import { DocumentATraduireService } from 'app/entities/document-a-traduire/service/document-a-traduire.service';

@Component({
  selector: 'jhi-langue-update',
  templateUrl: './langue-update.component.html',
})
export class LangueUpdateComponent implements OnInit {
  isSaving = false;
  langue: ILangue | null = null;

  documentATraduiresSharedCollection: IDocumentATraduire[] = [];

  editForm: LangueFormGroup = this.langueFormService.createLangueFormGroup();

  constructor(
    protected langueService: LangueService,
    protected langueFormService: LangueFormService,
    protected documentATraduireService: DocumentATraduireService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDocumentATraduire = (o1: IDocumentATraduire | null, o2: IDocumentATraduire | null): boolean =>
    this.documentATraduireService.compareDocumentATraduire(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ langue }) => {
      this.langue = langue;
      if (langue) {
        this.updateForm(langue);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const langue = this.langueFormService.getLangue(this.editForm);
    if (langue.id !== null) {
      this.subscribeToSaveResponse(this.langueService.update(langue));
    } else {
      this.subscribeToSaveResponse(this.langueService.create(langue));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILangue>>): void {
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

  protected updateForm(langue: ILangue): void {
    this.langue = langue;
    this.langueFormService.resetForm(this.editForm, langue);

    this.documentATraduiresSharedCollection = this.documentATraduireService.addDocumentATraduireToCollectionIfMissing<IDocumentATraduire>(
      this.documentATraduiresSharedCollection,
      langue.docTraductions
    );
  }

  protected loadRelationshipsOptions(): void {
    this.documentATraduireService
      .query()
      .pipe(map((res: HttpResponse<IDocumentATraduire[]>) => res.body ?? []))
      .pipe(
        map((documentATraduires: IDocumentATraduire[]) =>
          this.documentATraduireService.addDocumentATraduireToCollectionIfMissing<IDocumentATraduire>(
            documentATraduires,
            this.langue?.docTraductions
          )
        )
      )
      .subscribe((documentATraduires: IDocumentATraduire[]) => (this.documentATraduiresSharedCollection = documentATraduires));
  }
}
