import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AgenceBanqueFormService, AgenceBanqueFormGroup } from './agence-banque-form.service';
import { IAgenceBanque } from '../agence-banque.model';
import { AgenceBanqueService } from '../service/agence-banque.service';
import { IBanque } from 'app/entities/banque/banque.model';
import { BanqueService } from 'app/entities/banque/service/banque.service';

@Component({
  selector: 'jhi-agence-banque-update',
  templateUrl: './agence-banque-update.component.html',
})
export class AgenceBanqueUpdateComponent implements OnInit {
  isSaving = false;
  agenceBanque: IAgenceBanque | null = null;

  banquesSharedCollection: IBanque[] = [];

  editForm: AgenceBanqueFormGroup = this.agenceBanqueFormService.createAgenceBanqueFormGroup();

  constructor(
    protected agenceBanqueService: AgenceBanqueService,
    protected agenceBanqueFormService: AgenceBanqueFormService,
    protected banqueService: BanqueService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareBanque = (o1: IBanque | null, o2: IBanque | null): boolean => this.banqueService.compareBanque(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agenceBanque }) => {
      this.agenceBanque = agenceBanque;
      if (agenceBanque) {
        this.updateForm(agenceBanque);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agenceBanque = this.agenceBanqueFormService.getAgenceBanque(this.editForm);
    if (agenceBanque.id !== null) {
      this.subscribeToSaveResponse(this.agenceBanqueService.update(agenceBanque));
    } else {
      this.subscribeToSaveResponse(this.agenceBanqueService.create(agenceBanque));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgenceBanque>>): void {
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

  protected updateForm(agenceBanque: IAgenceBanque): void {
    this.agenceBanque = agenceBanque;
    this.agenceBanqueFormService.resetForm(this.editForm, agenceBanque);

    this.banquesSharedCollection = this.banqueService.addBanqueToCollectionIfMissing<IBanque>(
      this.banquesSharedCollection,
      agenceBanque.banque
    );
  }

  protected loadRelationshipsOptions(): void {
    this.banqueService
      .query()
      .pipe(map((res: HttpResponse<IBanque[]>) => res.body ?? []))
      .pipe(map((banques: IBanque[]) => this.banqueService.addBanqueToCollectionIfMissing<IBanque>(banques, this.agenceBanque?.banque)))
      .subscribe((banques: IBanque[]) => (this.banquesSharedCollection = banques));
  }
}
