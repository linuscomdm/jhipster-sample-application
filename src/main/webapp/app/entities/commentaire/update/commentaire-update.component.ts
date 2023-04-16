import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CommentaireFormService, CommentaireFormGroup } from './commentaire-form.service';
import { ICommentaire } from '../commentaire.model';
import { CommentaireService } from '../service/commentaire.service';
import { IDemandeDeTraduction } from 'app/entities/demande-de-traduction/demande-de-traduction.model';
import { DemandeDeTraductionService } from 'app/entities/demande-de-traduction/service/demande-de-traduction.service';
import { IPrestataire } from 'app/entities/prestataire/prestataire.model';
import { PrestataireService } from 'app/entities/prestataire/service/prestataire.service';
import { IDemandeur } from 'app/entities/demandeur/demandeur.model';
import { DemandeurService } from 'app/entities/demandeur/service/demandeur.service';

@Component({
  selector: 'jhi-commentaire-update',
  templateUrl: './commentaire-update.component.html',
})
export class CommentaireUpdateComponent implements OnInit {
  isSaving = false;
  commentaire: ICommentaire | null = null;

  demandeDeTraductionsSharedCollection: IDemandeDeTraduction[] = [];
  prestatairesSharedCollection: IPrestataire[] = [];
  demandeursSharedCollection: IDemandeur[] = [];

  editForm: CommentaireFormGroup = this.commentaireFormService.createCommentaireFormGroup();

  constructor(
    protected commentaireService: CommentaireService,
    protected commentaireFormService: CommentaireFormService,
    protected demandeDeTraductionService: DemandeDeTraductionService,
    protected prestataireService: PrestataireService,
    protected demandeurService: DemandeurService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDemandeDeTraduction = (o1: IDemandeDeTraduction | null, o2: IDemandeDeTraduction | null): boolean =>
    this.demandeDeTraductionService.compareDemandeDeTraduction(o1, o2);

  comparePrestataire = (o1: IPrestataire | null, o2: IPrestataire | null): boolean => this.prestataireService.comparePrestataire(o1, o2);

  compareDemandeur = (o1: IDemandeur | null, o2: IDemandeur | null): boolean => this.demandeurService.compareDemandeur(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commentaire }) => {
      this.commentaire = commentaire;
      if (commentaire) {
        this.updateForm(commentaire);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commentaire = this.commentaireFormService.getCommentaire(this.editForm);
    if (commentaire.id !== null) {
      this.subscribeToSaveResponse(this.commentaireService.update(commentaire));
    } else {
      this.subscribeToSaveResponse(this.commentaireService.create(commentaire));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommentaire>>): void {
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

  protected updateForm(commentaire: ICommentaire): void {
    this.commentaire = commentaire;
    this.commentaireFormService.resetForm(this.editForm, commentaire);

    this.demandeDeTraductionsSharedCollection =
      this.demandeDeTraductionService.addDemandeDeTraductionToCollectionIfMissing<IDemandeDeTraduction>(
        this.demandeDeTraductionsSharedCollection,
        commentaire.demandeDeTraduction
      );
    this.prestatairesSharedCollection = this.prestataireService.addPrestataireToCollectionIfMissing<IPrestataire>(
      this.prestatairesSharedCollection,
      commentaire.prestataire
    );
    this.demandeursSharedCollection = this.demandeurService.addDemandeurToCollectionIfMissing<IDemandeur>(
      this.demandeursSharedCollection,
      commentaire.demandeur
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
            this.commentaire?.demandeDeTraduction
          )
        )
      )
      .subscribe((demandeDeTraductions: IDemandeDeTraduction[]) => (this.demandeDeTraductionsSharedCollection = demandeDeTraductions));

    this.prestataireService
      .query()
      .pipe(map((res: HttpResponse<IPrestataire[]>) => res.body ?? []))
      .pipe(
        map((prestataires: IPrestataire[]) =>
          this.prestataireService.addPrestataireToCollectionIfMissing<IPrestataire>(prestataires, this.commentaire?.prestataire)
        )
      )
      .subscribe((prestataires: IPrestataire[]) => (this.prestatairesSharedCollection = prestataires));

    this.demandeurService
      .query()
      .pipe(map((res: HttpResponse<IDemandeur[]>) => res.body ?? []))
      .pipe(
        map((demandeurs: IDemandeur[]) =>
          this.demandeurService.addDemandeurToCollectionIfMissing<IDemandeur>(demandeurs, this.commentaire?.demandeur)
        )
      )
      .subscribe((demandeurs: IDemandeur[]) => (this.demandeursSharedCollection = demandeurs));
  }
}
