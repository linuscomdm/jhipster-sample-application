import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TerjemThequeFormService, TerjemThequeFormGroup } from './terjem-theque-form.service';
import { ITerjemTheque } from '../terjem-theque.model';
import { TerjemThequeService } from '../service/terjem-theque.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IPrestataire } from 'app/entities/prestataire/prestataire.model';
import { PrestataireService } from 'app/entities/prestataire/service/prestataire.service';
import { IDemandeur } from 'app/entities/demandeur/demandeur.model';
import { DemandeurService } from 'app/entities/demandeur/service/demandeur.service';
import { FormatDocTraduit } from 'app/entities/enumerations/format-doc-traduit.model';

@Component({
  selector: 'jhi-terjem-theque-update',
  templateUrl: './terjem-theque-update.component.html',
})
export class TerjemThequeUpdateComponent implements OnInit {
  isSaving = false;
  terjemTheque: ITerjemTheque | null = null;
  formatDocTraduitValues = Object.keys(FormatDocTraduit);

  prestatairesSharedCollection: IPrestataire[] = [];
  demandeursSharedCollection: IDemandeur[] = [];

  editForm: TerjemThequeFormGroup = this.terjemThequeFormService.createTerjemThequeFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected terjemThequeService: TerjemThequeService,
    protected terjemThequeFormService: TerjemThequeFormService,
    protected prestataireService: PrestataireService,
    protected demandeurService: DemandeurService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePrestataire = (o1: IPrestataire | null, o2: IPrestataire | null): boolean => this.prestataireService.comparePrestataire(o1, o2);

  compareDemandeur = (o1: IDemandeur | null, o2: IDemandeur | null): boolean => this.demandeurService.compareDemandeur(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ terjemTheque }) => {
      this.terjemTheque = terjemTheque;
      if (terjemTheque) {
        this.updateForm(terjemTheque);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertError>('jhipsterSampleApplicationApp.error', { ...err, key: 'error.file.' + err.key })
        ),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const terjemTheque = this.terjemThequeFormService.getTerjemTheque(this.editForm);
    if (terjemTheque.id !== null) {
      this.subscribeToSaveResponse(this.terjemThequeService.update(terjemTheque));
    } else {
      this.subscribeToSaveResponse(this.terjemThequeService.create(terjemTheque));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITerjemTheque>>): void {
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

  protected updateForm(terjemTheque: ITerjemTheque): void {
    this.terjemTheque = terjemTheque;
    this.terjemThequeFormService.resetForm(this.editForm, terjemTheque);

    this.prestatairesSharedCollection = this.prestataireService.addPrestataireToCollectionIfMissing<IPrestataire>(
      this.prestatairesSharedCollection,
      terjemTheque.prestataire
    );
    this.demandeursSharedCollection = this.demandeurService.addDemandeurToCollectionIfMissing<IDemandeur>(
      this.demandeursSharedCollection,
      terjemTheque.demandeur
    );
  }

  protected loadRelationshipsOptions(): void {
    this.prestataireService
      .query()
      .pipe(map((res: HttpResponse<IPrestataire[]>) => res.body ?? []))
      .pipe(
        map((prestataires: IPrestataire[]) =>
          this.prestataireService.addPrestataireToCollectionIfMissing<IPrestataire>(prestataires, this.terjemTheque?.prestataire)
        )
      )
      .subscribe((prestataires: IPrestataire[]) => (this.prestatairesSharedCollection = prestataires));

    this.demandeurService
      .query()
      .pipe(map((res: HttpResponse<IDemandeur[]>) => res.body ?? []))
      .pipe(
        map((demandeurs: IDemandeur[]) =>
          this.demandeurService.addDemandeurToCollectionIfMissing<IDemandeur>(demandeurs, this.terjemTheque?.demandeur)
        )
      )
      .subscribe((demandeurs: IDemandeur[]) => (this.demandeursSharedCollection = demandeurs));
  }
}
