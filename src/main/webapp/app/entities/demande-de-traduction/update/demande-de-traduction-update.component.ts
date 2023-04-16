import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DemandeDeTraductionFormService, DemandeDeTraductionFormGroup } from './demande-de-traduction-form.service';
import { IDemandeDeTraduction } from '../demande-de-traduction.model';
import { DemandeDeTraductionService } from '../service/demande-de-traduction.service';
import { IVille } from 'app/entities/ville/ville.model';
import { VilleService } from 'app/entities/ville/service/ville.service';
import { IDemandeur } from 'app/entities/demandeur/demandeur.model';
import { DemandeurService } from 'app/entities/demandeur/service/demandeur.service';
import { ModeEnvoi } from 'app/entities/enumerations/mode-envoi.model';
import { ModeLivraison } from 'app/entities/enumerations/mode-livraison.model';
import { EtatDemande } from 'app/entities/enumerations/etat-demande.model';

@Component({
  selector: 'jhi-demande-de-traduction-update',
  templateUrl: './demande-de-traduction-update.component.html',
})
export class DemandeDeTraductionUpdateComponent implements OnInit {
  isSaving = false;
  demandeDeTraduction: IDemandeDeTraduction | null = null;
  modeEnvoiValues = Object.keys(ModeEnvoi);
  modeLivraisonValues = Object.keys(ModeLivraison);
  etatDemandeValues = Object.keys(EtatDemande);

  villesSharedCollection: IVille[] = [];
  demandeursSharedCollection: IDemandeur[] = [];

  editForm: DemandeDeTraductionFormGroup = this.demandeDeTraductionFormService.createDemandeDeTraductionFormGroup();

  constructor(
    protected demandeDeTraductionService: DemandeDeTraductionService,
    protected demandeDeTraductionFormService: DemandeDeTraductionFormService,
    protected villeService: VilleService,
    protected demandeurService: DemandeurService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareVille = (o1: IVille | null, o2: IVille | null): boolean => this.villeService.compareVille(o1, o2);

  compareDemandeur = (o1: IDemandeur | null, o2: IDemandeur | null): boolean => this.demandeurService.compareDemandeur(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeDeTraduction }) => {
      this.demandeDeTraduction = demandeDeTraduction;
      if (demandeDeTraduction) {
        this.updateForm(demandeDeTraduction);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demandeDeTraduction = this.demandeDeTraductionFormService.getDemandeDeTraduction(this.editForm);
    if (demandeDeTraduction.id !== null) {
      this.subscribeToSaveResponse(this.demandeDeTraductionService.update(demandeDeTraduction));
    } else {
      this.subscribeToSaveResponse(this.demandeDeTraductionService.create(demandeDeTraduction));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandeDeTraduction>>): void {
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

  protected updateForm(demandeDeTraduction: IDemandeDeTraduction): void {
    this.demandeDeTraduction = demandeDeTraduction;
    this.demandeDeTraductionFormService.resetForm(this.editForm, demandeDeTraduction);

    this.villesSharedCollection = this.villeService.addVilleToCollectionIfMissing<IVille>(
      this.villesSharedCollection,
      demandeDeTraduction.ville
    );
    this.demandeursSharedCollection = this.demandeurService.addDemandeurToCollectionIfMissing<IDemandeur>(
      this.demandeursSharedCollection,
      demandeDeTraduction.demandeurService
    );
  }

  protected loadRelationshipsOptions(): void {
    this.villeService
      .query()
      .pipe(map((res: HttpResponse<IVille[]>) => res.body ?? []))
      .pipe(map((villes: IVille[]) => this.villeService.addVilleToCollectionIfMissing<IVille>(villes, this.demandeDeTraduction?.ville)))
      .subscribe((villes: IVille[]) => (this.villesSharedCollection = villes));

    this.demandeurService
      .query()
      .pipe(map((res: HttpResponse<IDemandeur[]>) => res.body ?? []))
      .pipe(
        map((demandeurs: IDemandeur[]) =>
          this.demandeurService.addDemandeurToCollectionIfMissing<IDemandeur>(demandeurs, this.demandeDeTraduction?.demandeurService)
        )
      )
      .subscribe((demandeurs: IDemandeur[]) => (this.demandeursSharedCollection = demandeurs));
  }
}
