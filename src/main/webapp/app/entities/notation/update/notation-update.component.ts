import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { NotationFormService, NotationFormGroup } from './notation-form.service';
import { INotation } from '../notation.model';
import { NotationService } from '../service/notation.service';
import { IDemandeur } from 'app/entities/demandeur/demandeur.model';
import { DemandeurService } from 'app/entities/demandeur/service/demandeur.service';
import { IPrestataire } from 'app/entities/prestataire/prestataire.model';
import { PrestataireService } from 'app/entities/prestataire/service/prestataire.service';

@Component({
  selector: 'jhi-notation-update',
  templateUrl: './notation-update.component.html',
})
export class NotationUpdateComponent implements OnInit {
  isSaving = false;
  notation: INotation | null = null;

  demandeursSharedCollection: IDemandeur[] = [];
  prestatairesSharedCollection: IPrestataire[] = [];

  editForm: NotationFormGroup = this.notationFormService.createNotationFormGroup();

  constructor(
    protected notationService: NotationService,
    protected notationFormService: NotationFormService,
    protected demandeurService: DemandeurService,
    protected prestataireService: PrestataireService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDemandeur = (o1: IDemandeur | null, o2: IDemandeur | null): boolean => this.demandeurService.compareDemandeur(o1, o2);

  comparePrestataire = (o1: IPrestataire | null, o2: IPrestataire | null): boolean => this.prestataireService.comparePrestataire(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notation }) => {
      this.notation = notation;
      if (notation) {
        this.updateForm(notation);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notation = this.notationFormService.getNotation(this.editForm);
    if (notation.id !== null) {
      this.subscribeToSaveResponse(this.notationService.update(notation));
    } else {
      this.subscribeToSaveResponse(this.notationService.create(notation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotation>>): void {
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

  protected updateForm(notation: INotation): void {
    this.notation = notation;
    this.notationFormService.resetForm(this.editForm, notation);

    this.demandeursSharedCollection = this.demandeurService.addDemandeurToCollectionIfMissing<IDemandeur>(
      this.demandeursSharedCollection,
      notation.demandeur
    );
    this.prestatairesSharedCollection = this.prestataireService.addPrestataireToCollectionIfMissing<IPrestataire>(
      this.prestatairesSharedCollection,
      notation.prestataire
    );
  }

  protected loadRelationshipsOptions(): void {
    this.demandeurService
      .query()
      .pipe(map((res: HttpResponse<IDemandeur[]>) => res.body ?? []))
      .pipe(
        map((demandeurs: IDemandeur[]) =>
          this.demandeurService.addDemandeurToCollectionIfMissing<IDemandeur>(demandeurs, this.notation?.demandeur)
        )
      )
      .subscribe((demandeurs: IDemandeur[]) => (this.demandeursSharedCollection = demandeurs));

    this.prestataireService
      .query()
      .pipe(map((res: HttpResponse<IPrestataire[]>) => res.body ?? []))
      .pipe(
        map((prestataires: IPrestataire[]) =>
          this.prestataireService.addPrestataireToCollectionIfMissing<IPrestataire>(prestataires, this.notation?.prestataire)
        )
      )
      .subscribe((prestataires: IPrestataire[]) => (this.prestatairesSharedCollection = prestataires));
  }
}
