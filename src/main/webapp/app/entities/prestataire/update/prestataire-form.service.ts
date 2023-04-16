import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPrestataire, NewPrestataire } from '../prestataire.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPrestataire for edit and NewPrestataireFormGroupInput for create.
 */
type PrestataireFormGroupInput = IPrestataire | PartialWithRequiredKeyOf<NewPrestataire>;

type PrestataireFormDefaults = Pick<NewPrestataire, 'id'>;

type PrestataireFormGroupContent = {
  id: FormControl<IPrestataire['id'] | NewPrestataire['id']>;
  civilite: FormControl<IPrestataire['civilite']>;
  nom: FormControl<IPrestataire['nom']>;
  prenom: FormControl<IPrestataire['prenom']>;
  nomCommercial: FormControl<IPrestataire['nomCommercial']>;
  telephoneTravail: FormControl<IPrestataire['telephoneTravail']>;
  telephoneMobile: FormControl<IPrestataire['telephoneMobile']>;
  email: FormControl<IPrestataire['email']>;
  adresse: FormControl<IPrestataire['adresse']>;
  codePostal: FormControl<IPrestataire['codePostal']>;
  photoDeProfil: FormControl<IPrestataire['photoDeProfil']>;
  photoDeProfilContentType: FormControl<IPrestataire['photoDeProfilContentType']>;
  numeroPieceIdentite: FormControl<IPrestataire['numeroPieceIdentite']>;
  typeIdentiteProfessionnelle: FormControl<IPrestataire['typeIdentiteProfessionnelle']>;
  rattachIdentitePro: FormControl<IPrestataire['rattachIdentitePro']>;
  rattachIdentiteProContentType: FormControl<IPrestataire['rattachIdentiteProContentType']>;
  coordonneesBancaires: FormControl<IPrestataire['coordonneesBancaires']>;
  coordonneesBancairesContentType: FormControl<IPrestataire['coordonneesBancairesContentType']>;
  titulaireDuCompte: FormControl<IPrestataire['titulaireDuCompte']>;
  ribOuRip: FormControl<IPrestataire['ribOuRip']>;
  dateCreation: FormControl<IPrestataire['dateCreation']>;
  etat: FormControl<IPrestataire['etat']>;
  user: FormControl<IPrestataire['user']>;
  banque: FormControl<IPrestataire['banque']>;
  ville: FormControl<IPrestataire['ville']>;
  prestaDdeTraductions: FormControl<IPrestataire['prestaDdeTraductions']>;
  agenceBanque: FormControl<IPrestataire['agenceBanque']>;
};

export type PrestataireFormGroup = FormGroup<PrestataireFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PrestataireFormService {
  createPrestataireFormGroup(prestataire: PrestataireFormGroupInput = { id: null }): PrestataireFormGroup {
    const prestataireRawValue = {
      ...this.getFormDefaults(),
      ...prestataire,
    };
    return new FormGroup<PrestataireFormGroupContent>({
      id: new FormControl(
        { value: prestataireRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      civilite: new FormControl(prestataireRawValue.civilite),
      nom: new FormControl(prestataireRawValue.nom, {
        validators: [Validators.required, Validators.maxLength(80)],
      }),
      prenom: new FormControl(prestataireRawValue.prenom, {
        validators: [Validators.required, Validators.maxLength(80)],
      }),
      nomCommercial: new FormControl(prestataireRawValue.nomCommercial, {
        validators: [Validators.maxLength(80)],
      }),
      telephoneTravail: new FormControl(prestataireRawValue.telephoneTravail, {
        validators: [Validators.maxLength(30)],
      }),
      telephoneMobile: new FormControl(prestataireRawValue.telephoneMobile, {
        validators: [Validators.required, Validators.maxLength(30)],
      }),
      email: new FormControl(prestataireRawValue.email, {
        validators: [Validators.required, Validators.maxLength(50), Validators.pattern('^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}')],
      }),
      adresse: new FormControl(prestataireRawValue.adresse, {
        validators: [Validators.maxLength(70)],
      }),
      codePostal: new FormControl(prestataireRawValue.codePostal, {
        validators: [Validators.maxLength(10)],
      }),
      photoDeProfil: new FormControl(prestataireRawValue.photoDeProfil),
      photoDeProfilContentType: new FormControl(prestataireRawValue.photoDeProfilContentType),
      numeroPieceIdentite: new FormControl(prestataireRawValue.numeroPieceIdentite, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      typeIdentiteProfessionnelle: new FormControl(prestataireRawValue.typeIdentiteProfessionnelle),
      rattachIdentitePro: new FormControl(prestataireRawValue.rattachIdentitePro),
      rattachIdentiteProContentType: new FormControl(prestataireRawValue.rattachIdentiteProContentType),
      coordonneesBancaires: new FormControl(prestataireRawValue.coordonneesBancaires),
      coordonneesBancairesContentType: new FormControl(prestataireRawValue.coordonneesBancairesContentType),
      titulaireDuCompte: new FormControl(prestataireRawValue.titulaireDuCompte, {
        validators: [Validators.maxLength(80)],
      }),
      ribOuRip: new FormControl(prestataireRawValue.ribOuRip, {
        validators: [Validators.maxLength(20)],
      }),
      dateCreation: new FormControl(prestataireRawValue.dateCreation),
      etat: new FormControl(prestataireRawValue.etat),
      user: new FormControl(prestataireRawValue.user),
      banque: new FormControl(prestataireRawValue.banque),
      ville: new FormControl(prestataireRawValue.ville),
      prestaDdeTraductions: new FormControl(prestataireRawValue.prestaDdeTraductions),
      agenceBanque: new FormControl(prestataireRawValue.agenceBanque),
    });
  }

  getPrestataire(form: PrestataireFormGroup): IPrestataire | NewPrestataire {
    return form.getRawValue() as IPrestataire | NewPrestataire;
  }

  resetForm(form: PrestataireFormGroup, prestataire: PrestataireFormGroupInput): void {
    const prestataireRawValue = { ...this.getFormDefaults(), ...prestataire };
    form.reset(
      {
        ...prestataireRawValue,
        id: { value: prestataireRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PrestataireFormDefaults {
    return {
      id: null,
    };
  }
}
