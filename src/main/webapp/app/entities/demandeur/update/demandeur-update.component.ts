import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DemandeurFormService, DemandeurFormGroup } from './demandeur-form.service';
import { IDemandeur } from '../demandeur.model';
import { DemandeurService } from '../service/demandeur.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IVille } from 'app/entities/ville/ville.model';
import { VilleService } from 'app/entities/ville/service/ville.service';
import { IBanque } from 'app/entities/banque/banque.model';
import { BanqueService } from 'app/entities/banque/service/banque.service';
import { IAgenceBanque } from 'app/entities/agence-banque/agence-banque.model';
import { AgenceBanqueService } from 'app/entities/agence-banque/service/agence-banque.service';
import { Civilite } from 'app/entities/enumerations/civilite.model';
import { EtatDemandeur } from 'app/entities/enumerations/etat-demandeur.model';

@Component({
  selector: 'jhi-demandeur-update',
  templateUrl: './demandeur-update.component.html',
})
export class DemandeurUpdateComponent implements OnInit {
  isSaving = false;
  demandeur: IDemandeur | null = null;
  civiliteValues = Object.keys(Civilite);
  etatDemandeurValues = Object.keys(EtatDemandeur);

  usersSharedCollection: IUser[] = [];
  villesSharedCollection: IVille[] = [];
  banquesSharedCollection: IBanque[] = [];
  agenceBanquesSharedCollection: IAgenceBanque[] = [];

  editForm: DemandeurFormGroup = this.demandeurFormService.createDemandeurFormGroup();

  constructor(
    protected demandeurService: DemandeurService,
    protected demandeurFormService: DemandeurFormService,
    protected userService: UserService,
    protected villeService: VilleService,
    protected banqueService: BanqueService,
    protected agenceBanqueService: AgenceBanqueService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareVille = (o1: IVille | null, o2: IVille | null): boolean => this.villeService.compareVille(o1, o2);

  compareBanque = (o1: IBanque | null, o2: IBanque | null): boolean => this.banqueService.compareBanque(o1, o2);

  compareAgenceBanque = (o1: IAgenceBanque | null, o2: IAgenceBanque | null): boolean =>
    this.agenceBanqueService.compareAgenceBanque(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeur }) => {
      this.demandeur = demandeur;
      if (demandeur) {
        this.updateForm(demandeur);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demandeur = this.demandeurFormService.getDemandeur(this.editForm);
    if (demandeur.id !== null) {
      this.subscribeToSaveResponse(this.demandeurService.update(demandeur));
    } else {
      this.subscribeToSaveResponse(this.demandeurService.create(demandeur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandeur>>): void {
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

  protected updateForm(demandeur: IDemandeur): void {
    this.demandeur = demandeur;
    this.demandeurFormService.resetForm(this.editForm, demandeur);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, demandeur.user);
    this.villesSharedCollection = this.villeService.addVilleToCollectionIfMissing<IVille>(this.villesSharedCollection, demandeur.ville);
    this.banquesSharedCollection = this.banqueService.addBanqueToCollectionIfMissing<IBanque>(
      this.banquesSharedCollection,
      demandeur.banque
    );
    this.agenceBanquesSharedCollection = this.agenceBanqueService.addAgenceBanqueToCollectionIfMissing<IAgenceBanque>(
      this.agenceBanquesSharedCollection,
      demandeur.agenceBanque
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.demandeur?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.villeService
      .query()
      .pipe(map((res: HttpResponse<IVille[]>) => res.body ?? []))
      .pipe(map((villes: IVille[]) => this.villeService.addVilleToCollectionIfMissing<IVille>(villes, this.demandeur?.ville)))
      .subscribe((villes: IVille[]) => (this.villesSharedCollection = villes));

    this.banqueService
      .query()
      .pipe(map((res: HttpResponse<IBanque[]>) => res.body ?? []))
      .pipe(map((banques: IBanque[]) => this.banqueService.addBanqueToCollectionIfMissing<IBanque>(banques, this.demandeur?.banque)))
      .subscribe((banques: IBanque[]) => (this.banquesSharedCollection = banques));

    this.agenceBanqueService
      .query()
      .pipe(map((res: HttpResponse<IAgenceBanque[]>) => res.body ?? []))
      .pipe(
        map((agenceBanques: IAgenceBanque[]) =>
          this.agenceBanqueService.addAgenceBanqueToCollectionIfMissing<IAgenceBanque>(agenceBanques, this.demandeur?.agenceBanque)
        )
      )
      .subscribe((agenceBanques: IAgenceBanque[]) => (this.agenceBanquesSharedCollection = agenceBanques));
  }
}
