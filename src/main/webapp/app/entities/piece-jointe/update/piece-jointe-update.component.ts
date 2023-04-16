import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PieceJointeFormService, PieceJointeFormGroup } from './piece-jointe-form.service';
import { IPieceJointe } from '../piece-jointe.model';
import { PieceJointeService } from '../service/piece-jointe.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IDemandeDeTraduction } from 'app/entities/demande-de-traduction/demande-de-traduction.model';
import { DemandeDeTraductionService } from 'app/entities/demande-de-traduction/service/demande-de-traduction.service';
import { IPrestataire } from 'app/entities/prestataire/prestataire.model';
import { PrestataireService } from 'app/entities/prestataire/service/prestataire.service';

@Component({
  selector: 'jhi-piece-jointe-update',
  templateUrl: './piece-jointe-update.component.html',
})
export class PieceJointeUpdateComponent implements OnInit {
  isSaving = false;
  pieceJointe: IPieceJointe | null = null;

  demandeDeTraductionsSharedCollection: IDemandeDeTraduction[] = [];
  prestatairesSharedCollection: IPrestataire[] = [];

  editForm: PieceJointeFormGroup = this.pieceJointeFormService.createPieceJointeFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected pieceJointeService: PieceJointeService,
    protected pieceJointeFormService: PieceJointeFormService,
    protected demandeDeTraductionService: DemandeDeTraductionService,
    protected prestataireService: PrestataireService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDemandeDeTraduction = (o1: IDemandeDeTraduction | null, o2: IDemandeDeTraduction | null): boolean =>
    this.demandeDeTraductionService.compareDemandeDeTraduction(o1, o2);

  comparePrestataire = (o1: IPrestataire | null, o2: IPrestataire | null): boolean => this.prestataireService.comparePrestataire(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pieceJointe }) => {
      this.pieceJointe = pieceJointe;
      if (pieceJointe) {
        this.updateForm(pieceJointe);
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
    const pieceJointe = this.pieceJointeFormService.getPieceJointe(this.editForm);
    if (pieceJointe.id !== null) {
      this.subscribeToSaveResponse(this.pieceJointeService.update(pieceJointe));
    } else {
      this.subscribeToSaveResponse(this.pieceJointeService.create(pieceJointe));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPieceJointe>>): void {
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

  protected updateForm(pieceJointe: IPieceJointe): void {
    this.pieceJointe = pieceJointe;
    this.pieceJointeFormService.resetForm(this.editForm, pieceJointe);

    this.demandeDeTraductionsSharedCollection =
      this.demandeDeTraductionService.addDemandeDeTraductionToCollectionIfMissing<IDemandeDeTraduction>(
        this.demandeDeTraductionsSharedCollection,
        pieceJointe.pjDdeTraductions
      );
    this.prestatairesSharedCollection = this.prestataireService.addPrestataireToCollectionIfMissing<IPrestataire>(
      this.prestatairesSharedCollection,
      pieceJointe.prestataire
    );
  }

  protected loadRelationshipsOptions(): void {
    this.demandeDeTraductionService
      .query()
      .pipe(map((res: HttpResponse<IDemandeDeTraduction[]>) => res.body ?? []))
      .pipe(
        map((demandeDeTraductions: IDemandeDeTraduction[]) =>
          this.demandeDeTraductionService.addDemandeDeTraductionToCollectionIfMissing<IDemandeDeTraduction>(
            demandeDeTraductions,
            this.pieceJointe?.pjDdeTraductions
          )
        )
      )
      .subscribe((demandeDeTraductions: IDemandeDeTraduction[]) => (this.demandeDeTraductionsSharedCollection = demandeDeTraductions));

    this.prestataireService
      .query()
      .pipe(map((res: HttpResponse<IPrestataire[]>) => res.body ?? []))
      .pipe(
        map((prestataires: IPrestataire[]) =>
          this.prestataireService.addPrestataireToCollectionIfMissing<IPrestataire>(prestataires, this.pieceJointe?.prestataire)
        )
      )
      .subscribe((prestataires: IPrestataire[]) => (this.prestatairesSharedCollection = prestataires));
  }
}
