import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDemandeur, NewDemandeur } from '../demandeur.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDemandeur for edit and NewDemandeurFormGroupInput for create.
 */
type DemandeurFormGroupInput = IDemandeur | PartialWithRequiredKeyOf<NewDemandeur>;

type DemandeurFormDefaults = Pick<NewDemandeur, 'id'>;

type DemandeurFormGroupContent = {
  id: FormControl<IDemandeur['id'] | NewDemandeur['id']>;
  civilite: FormControl<IDemandeur['civilite']>;
  nom: FormControl<IDemandeur['nom']>;
  prenom: FormControl<IDemandeur['prenom']>;
  dateDeNaissance: FormControl<IDemandeur['dateDeNaissance']>;
  telephone: FormControl<IDemandeur['telephone']>;
  email: FormControl<IDemandeur['email']>;
  adresse: FormControl<IDemandeur['adresse']>;
  dateCreation: FormControl<IDemandeur['dateCreation']>;
  etat: FormControl<IDemandeur['etat']>;
  user: FormControl<IDemandeur['user']>;
  ville: FormControl<IDemandeur['ville']>;
  banque: FormControl<IDemandeur['banque']>;
  agenceBanque: FormControl<IDemandeur['agenceBanque']>;
};

export type DemandeurFormGroup = FormGroup<DemandeurFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DemandeurFormService {
  createDemandeurFormGroup(demandeur: DemandeurFormGroupInput = { id: null }): DemandeurFormGroup {
    const demandeurRawValue = {
      ...this.getFormDefaults(),
      ...demandeur,
    };
    return new FormGroup<DemandeurFormGroupContent>({
      id: new FormControl(
        { value: demandeurRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      civilite: new FormControl(demandeurRawValue.civilite),
      nom: new FormControl(demandeurRawValue.nom, {
        validators: [Validators.required, Validators.maxLength(80)],
      }),
      prenom: new FormControl(demandeurRawValue.prenom, {
        validators: [Validators.required, Validators.maxLength(80)],
      }),
      dateDeNaissance: new FormControl(demandeurRawValue.dateDeNaissance),
      telephone: new FormControl(demandeurRawValue.telephone, {
        validators: [Validators.required, Validators.maxLength(30)],
      }),
      email: new FormControl(demandeurRawValue.email, {
        validators: [Validators.required, Validators.maxLength(50), Validators.pattern('^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}')],
      }),
      adresse: new FormControl(demandeurRawValue.adresse, {
        validators: [Validators.maxLength(50)],
      }),
      dateCreation: new FormControl(demandeurRawValue.dateCreation),
      etat: new FormControl(demandeurRawValue.etat),
      user: new FormControl(demandeurRawValue.user),
      ville: new FormControl(demandeurRawValue.ville),
      banque: new FormControl(demandeurRawValue.banque),
      agenceBanque: new FormControl(demandeurRawValue.agenceBanque),
    });
  }

  getDemandeur(form: DemandeurFormGroup): IDemandeur | NewDemandeur {
    return form.getRawValue() as IDemandeur | NewDemandeur;
  }

  resetForm(form: DemandeurFormGroup, demandeur: DemandeurFormGroupInput): void {
    const demandeurRawValue = { ...this.getFormDefaults(), ...demandeur };
    form.reset(
      {
        ...demandeurRawValue,
        id: { value: demandeurRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DemandeurFormDefaults {
    return {
      id: null,
    };
  }
}
