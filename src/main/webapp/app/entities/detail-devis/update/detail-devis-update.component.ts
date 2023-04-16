import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DetailDevisFormService, DetailDevisFormGroup } from './detail-devis-form.service';
import { IDetailDevis } from '../detail-devis.model';
import { DetailDevisService } from '../service/detail-devis.service';
import { IDevis } from 'app/entities/devis/devis.model';
import { DevisService } from 'app/entities/devis/service/devis.service';

@Component({
  selector: 'jhi-detail-devis-update',
  templateUrl: './detail-devis-update.component.html',
})
export class DetailDevisUpdateComponent implements OnInit {
  isSaving = false;
  detailDevis: IDetailDevis | null = null;

  devisSharedCollection: IDevis[] = [];

  editForm: DetailDevisFormGroup = this.detailDevisFormService.createDetailDevisFormGroup();

  constructor(
    protected detailDevisService: DetailDevisService,
    protected detailDevisFormService: DetailDevisFormService,
    protected devisService: DevisService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDevis = (o1: IDevis | null, o2: IDevis | null): boolean => this.devisService.compareDevis(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detailDevis }) => {
      this.detailDevis = detailDevis;
      if (detailDevis) {
        this.updateForm(detailDevis);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const detailDevis = this.detailDevisFormService.getDetailDevis(this.editForm);
    if (detailDevis.id !== null) {
      this.subscribeToSaveResponse(this.detailDevisService.update(detailDevis));
    } else {
      this.subscribeToSaveResponse(this.detailDevisService.create(detailDevis));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetailDevis>>): void {
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

  protected updateForm(detailDevis: IDetailDevis): void {
    this.detailDevis = detailDevis;
    this.detailDevisFormService.resetForm(this.editForm, detailDevis);

    this.devisSharedCollection = this.devisService.addDevisToCollectionIfMissing<IDevis>(this.devisSharedCollection, detailDevis.devis);
  }

  protected loadRelationshipsOptions(): void {
    this.devisService
      .query()
      .pipe(map((res: HttpResponse<IDevis[]>) => res.body ?? []))
      .pipe(map((devis: IDevis[]) => this.devisService.addDevisToCollectionIfMissing<IDevis>(devis, this.detailDevis?.devis)))
      .subscribe((devis: IDevis[]) => (this.devisSharedCollection = devis));
  }
}
