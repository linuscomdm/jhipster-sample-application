import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PrestataireFormService, PrestataireFormGroup } from './prestataire-form.service';
import { IPrestataire } from '../prestataire.model';
import { PrestataireService } from '../service/prestataire.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IBanque } from 'app/entities/banque/banque.model';
import { BanqueService } from 'app/entities/banque/service/banque.service';
import { IVille } from 'app/entities/ville/ville.model';
import { VilleService } from 'app/entities/ville/service/ville.service';
import { IDemandeDeTraduction } from 'app/entities/demande-de-traduction/demande-de-traduction.model';
import { DemandeDeTraductionService } from 'app/entities/demande-de-traduction/service/demande-de-traduction.service';
import { IAgenceBanque } from 'app/entities/agence-banque/agence-banque.model';
import { AgenceBanqueService } from 'app/entities/agence-banque/service/agence-banque.service';
import { Civilite } from 'app/entities/enumerations/civilite.model';
import { TypeIdentiteProfessionnelle } from 'app/entities/enumerations/type-identite-professionnelle.model';
import { EtatPrestataire } from 'app/entities/enumerations/etat-prestataire.model';

@Component({
  selector: 'jhi-prestataire-update',
  templateUrl: './prestataire-update.component.html',
})
export class PrestataireUpdateComponent implements OnInit {
  isSaving = false;
  prestataire: IPrestataire | null = null;
  civiliteValues = Object.keys(Civilite);
  typeIdentiteProfessionnelleValues = Object.keys(TypeIdentiteProfessionnelle);
  etatPrestataireValues = Object.keys(EtatPrestataire);

  usersSharedCollection: IUser[] = [];
  banquesSharedCollection: IBanque[] = [];
  villesSharedCollection: IVille[] = [];
  demandeDeTraductionsSharedCollection: IDemandeDeTraduction[] = [];
  agenceBanquesSharedCollection: IAgenceBanque[] = [];

  editForm: PrestataireFormGroup = this.prestataireFormService.createPrestataireFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected prestataireService: PrestataireService,
    protected prestataireFormService: PrestataireFormService,
    protected userService: UserService,
    protected banqueService: BanqueService,
    protected villeService: VilleService,
    protected demandeDeTraductionService: DemandeDeTraductionService,
    protected agenceBanqueService: AgenceBanqueService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareBanque = (o1: IBanque | null, o2: IBanque | null): boolean => this.banqueService.compareBanque(o1, o2);

  compareVille = (o1: IVille | null, o2: IVille | null): boolean => this.villeService.compareVille(o1, o2);

  compareDemandeDeTraduction = (o1: IDemandeDeTraduction | null, o2: IDemandeDeTraduction | null): boolean =>
    this.demandeDeTraductionService.compareDemandeDeTraduction(o1, o2);

  compareAgenceBanque = (o1: IAgenceBanque | null, o2: IAgenceBanque | null): boolean =>
    this.agenceBanqueService.compareAgenceBanque(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prestataire }) => {
      this.prestataire = prestataire;
      if (prestataire) {
        this.updateForm(prestataire);
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
    const prestataire = this.prestataireFormService.getPrestataire(this.editForm);
    if (prestataire.id !== null) {
      this.subscribeToSaveResponse(this.prestataireService.update(prestataire));
    } else {
      this.subscribeToSaveResponse(this.prestataireService.create(prestataire));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrestataire>>): void {
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

  protected updateForm(prestataire: IPrestataire): void {
    this.prestataire = prestataire;
    this.prestataireFormService.resetForm(this.editForm, prestataire);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, prestataire.user);
    this.banquesSharedCollection = this.banqueService.addBanqueToCollectionIfMissing<IBanque>(
      this.banquesSharedCollection,
      prestataire.banque
    );
    this.villesSharedCollection = this.villeService.addVilleToCollectionIfMissing<IVille>(this.villesSharedCollection, prestataire.ville);
    this.demandeDeTraductionsSharedCollection =
      this.demandeDeTraductionService.addDemandeDeTraductionToCollectionIfMissing<IDemandeDeTraduction>(
        this.demandeDeTraductionsSharedCollection,
        prestataire.prestaDdeTraductions
      );
    this.agenceBanquesSharedCollection = this.agenceBanqueService.addAgenceBanqueToCollectionIfMissing<IAgenceBanque>(
      this.agenceBanquesSharedCollection,
      prestataire.agenceBanque
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.prestataire?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.banqueService
      .query()
      .pipe(map((res: HttpResponse<IBanque[]>) => res.body ?? []))
      .pipe(map((banques: IBanque[]) => this.banqueService.addBanqueToCollectionIfMissing<IBanque>(banques, this.prestataire?.banque)))
      .subscribe((banques: IBanque[]) => (this.banquesSharedCollection = banques));

    this.villeService
      .query()
      .pipe(map((res: HttpResponse<IVille[]>) => res.body ?? []))
      .pipe(map((villes: IVille[]) => this.villeService.addVilleToCollectionIfMissing<IVille>(villes, this.prestataire?.ville)))
      .subscribe((villes: IVille[]) => (this.villesSharedCollection = villes));

    this.demandeDeTraductionService
      .query()
      .pipe(map((res: HttpResponse<IDemandeDeTraduction[]>) => res.body ?? []))
      .pipe(
        map((demandeDeTraductions: IDemandeDeTraduction[]) =>
          this.demandeDeTraductionService.addDemandeDeTraductionToCollectionIfMissing<IDemandeDeTraduction>(
            demandeDeTraductions,
            this.prestataire?.prestaDdeTraductions
          )
        )
      )
      .subscribe((demandeDeTraductions: IDemandeDeTraduction[]) => (this.demandeDeTraductionsSharedCollection = demandeDeTraductions));

    this.agenceBanqueService
      .query()
      .pipe(map((res: HttpResponse<IAgenceBanque[]>) => res.body ?? []))
      .pipe(
        map((agenceBanques: IAgenceBanque[]) =>
          this.agenceBanqueService.addAgenceBanqueToCollectionIfMissing<IAgenceBanque>(agenceBanques, this.prestataire?.agenceBanque)
        )
      )
      .subscribe((agenceBanques: IAgenceBanque[]) => (this.agenceBanquesSharedCollection = agenceBanques));
  }
}
