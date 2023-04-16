import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { NatureDocumentATraduireFormService, NatureDocumentATraduireFormGroup } from './nature-document-a-traduire-form.service';
import { INatureDocumentATraduire } from '../nature-document-a-traduire.model';
import { NatureDocumentATraduireService } from '../service/nature-document-a-traduire.service';

@Component({
  selector: 'jhi-nature-document-a-traduire-update',
  templateUrl: './nature-document-a-traduire-update.component.html',
})
export class NatureDocumentATraduireUpdateComponent implements OnInit {
  isSaving = false;
  natureDocumentATraduire: INatureDocumentATraduire | null = null;

  editForm: NatureDocumentATraduireFormGroup = this.natureDocumentATraduireFormService.createNatureDocumentATraduireFormGroup();

  constructor(
    protected natureDocumentATraduireService: NatureDocumentATraduireService,
    protected natureDocumentATraduireFormService: NatureDocumentATraduireFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureDocumentATraduire }) => {
      this.natureDocumentATraduire = natureDocumentATraduire;
      if (natureDocumentATraduire) {
        this.updateForm(natureDocumentATraduire);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const natureDocumentATraduire = this.natureDocumentATraduireFormService.getNatureDocumentATraduire(this.editForm);
    if (natureDocumentATraduire.id !== null) {
      this.subscribeToSaveResponse(this.natureDocumentATraduireService.update(natureDocumentATraduire));
    } else {
      this.subscribeToSaveResponse(this.natureDocumentATraduireService.create(natureDocumentATraduire));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INatureDocumentATraduire>>): void {
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

  protected updateForm(natureDocumentATraduire: INatureDocumentATraduire): void {
    this.natureDocumentATraduire = natureDocumentATraduire;
    this.natureDocumentATraduireFormService.resetForm(this.editForm, natureDocumentATraduire);
  }
}
