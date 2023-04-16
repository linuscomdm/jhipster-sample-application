import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DevisFormService, DevisFormGroup } from './devis-form.service';
import { IDevis } from '../devis.model';
import { DevisService } from '../service/devis.service';
import { IPrestataire } from 'app/entities/prestataire/prestataire.model';
import { PrestataireService } from 'app/entities/prestataire/service/prestataire.service';
import { IDemandeDeTraduction } from 'app/entities/demande-de-traduction/demande-de-traduction.model';
import { DemandeDeTraductionService } from 'app/entities/demande-de-traduction/service/demande-de-traduction.service';

@Component({
  selector: 'jhi-devis-update',
  templateUrl: './devis-update.component.html',
})
export class DevisUpdateComponent implements OnInit {
  isSaving = false;
  devis: IDevis | null = null;

  prestatairesSharedCollection: IPrestataire[] = [];
  demandeDeTraductionsSharedCollection: IDemandeDeTraduction[] = [];

  editForm: DevisFormGroup = this.devisFormService.createDevisFormGroup();

  constructor(
    protected devisService: DevisService,
    protected devisFormService: DevisFormService,
    protected prestataireService: PrestataireService,
    protected demandeDeTraductionService: DemandeDeTraductionService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePrestataire = (o1: IPrestataire | null, o2: IPrestataire | null): boolean => this.prestataireService.comparePrestataire(o1, o2);

  compareDemandeDeTraduction = (o1: IDemandeDeTraduction | null, o2: IDemandeDeTraduction | null): boolean =>
    this.demandeDeTraductionService.compareDemandeDeTraduction(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ devis }) => {
      this.devis = devis;
      if (devis) {
        this.updateForm(devis);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const devis = this.devisFormService.getDevis(this.editForm);
    if (devis.id !== null) {
      this.subscribeToSaveResponse(this.devisService.update(devis));
    } else {
      this.subscribeToSaveResponse(this.devisService.create(devis));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDevis>>): void {
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

  protected updateForm(devis: IDevis): void {
    this.devis = devis;
    this.devisFormService.resetForm(this.editForm, devis);

    this.prestatairesSharedCollection = this.prestataireService.addPrestataireToCollectionIfMissing<IPrestataire>(
      this.prestatairesSharedCollection,
      devis.prestataire
    );
    this.demandeDeTraductionsSharedCollection =
      this.demandeDeTraductionService.addDemandeDeTraductionToCollectionIfMissing<IDemandeDeTraduction>(
        this.demandeDeTraductionsSharedCollection,
        devis.demandeDeTraduction
      );
  }

  protected loadRelationshipsOptions(): void {
    this.prestataireService
      .query()
      .pipe(map((res: HttpResponse<IPrestataire[]>) => res.body ?? []))
      .pipe(
        map((prestataires: IPrestataire[]) =>
          this.prestataireService.addPrestataireToCollectionIfMissing<IPrestataire>(prestataires, this.devis?.prestataire)
        )
      )
      .subscribe((prestataires: IPrestataire[]) => (this.prestatairesSharedCollection = prestataires));

    this.demandeDeTraductionService
      .query()
      .pipe(map((res: HttpResponse<IDemandeDeTraduction[]>) => res.body ?? []))
      .pipe(
        map((demandeDeTraductions: IDemandeDeTraduction[]) =>
          this.demandeDeTraductionService.addDemandeDeTraductionToCollectionIfMissing<IDemandeDeTraduction>(
            demandeDeTraductions,
            this.devis?.demandeDeTraduction
          )
        )
      )
      .subscribe((demandeDeTraductions: IDemandeDeTraduction[]) => (this.demandeDeTraductionsSharedCollection = demandeDeTraductions));
  }
}
