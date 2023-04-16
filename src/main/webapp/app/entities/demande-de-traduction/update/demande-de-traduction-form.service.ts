import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDemandeDeTraduction, NewDemandeDeTraduction } from '../demande-de-traduction.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDemandeDeTraduction for edit and NewDemandeDeTraductionFormGroupInput for create.
 */
type DemandeDeTraductionFormGroupInput = IDemandeDeTraduction | PartialWithRequiredKeyOf<NewDemandeDeTraduction>;

type DemandeDeTraductionFormDefaults = Pick<NewDemandeDeTraduction, 'id'>;

type DemandeDeTraductionFormGroupContent = {
  id: FormControl<IDemandeDeTraduction['id'] | NewDemandeDeTraduction['id']>;
  modeEnvoiPreconise: FormControl<IDemandeDeTraduction['modeEnvoiPreconise']>;
  modeLivraisonExige: FormControl<IDemandeDeTraduction['modeLivraisonExige']>;
  delaiDeTraitemenSouhaite: FormControl<IDemandeDeTraduction['delaiDeTraitemenSouhaite']>;
  adresseLivraison: FormControl<IDemandeDeTraduction['adresseLivraison']>;
  delaiDeTraitemenPrestataire: FormControl<IDemandeDeTraduction['delaiDeTraitemenPrestataire']>;
  observation: FormControl<IDemandeDeTraduction['observation']>;
  dateCreation: FormControl<IDemandeDeTraduction['dateCreation']>;
  dateCloture: FormControl<IDemandeDeTraduction['dateCloture']>;
  etat: FormControl<IDemandeDeTraduction['etat']>;
  ville: FormControl<IDemandeDeTraduction['ville']>;
  demandeurService: FormControl<IDemandeDeTraduction['demandeurService']>;
};

export type DemandeDeTraductionFormGroup = FormGroup<DemandeDeTraductionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DemandeDeTraductionFormService {
  createDemandeDeTraductionFormGroup(demandeDeTraduction: DemandeDeTraductionFormGroupInput = { id: null }): DemandeDeTraductionFormGroup {
    const demandeDeTraductionRawValue = {
      ...this.getFormDefaults(),
      ...demandeDeTraduction,
    };
    return new FormGroup<DemandeDeTraductionFormGroupContent>({
      id: new FormControl(
        { value: demandeDeTraductionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      modeEnvoiPreconise: new FormControl(demandeDeTraductionRawValue.modeEnvoiPreconise),
      modeLivraisonExige: new FormControl(demandeDeTraductionRawValue.modeLivraisonExige),
      delaiDeTraitemenSouhaite: new FormControl(demandeDeTraductionRawValue.delaiDeTraitemenSouhaite),
      adresseLivraison: new FormControl(demandeDeTraductionRawValue.adresseLivraison, {
        validators: [Validators.maxLength(150)],
      }),
      delaiDeTraitemenPrestataire: new FormControl(demandeDeTraductionRawValue.delaiDeTraitemenPrestataire),
      observation: new FormControl(demandeDeTraductionRawValue.observation, {
        validators: [Validators.maxLength(500)],
      }),
      dateCreation: new FormControl(demandeDeTraductionRawValue.dateCreation),
      dateCloture: new FormControl(demandeDeTraductionRawValue.dateCloture),
      etat: new FormControl(demandeDeTraductionRawValue.etat),
      ville: new FormControl(demandeDeTraductionRawValue.ville),
      demandeurService: new FormControl(demandeDeTraductionRawValue.demandeurService),
    });
  }

  getDemandeDeTraduction(form: DemandeDeTraductionFormGroup): IDemandeDeTraduction | NewDemandeDeTraduction {
    return form.getRawValue() as IDemandeDeTraduction | NewDemandeDeTraduction;
  }

  resetForm(form: DemandeDeTraductionFormGroup, demandeDeTraduction: DemandeDeTraductionFormGroupInput): void {
    const demandeDeTraductionRawValue = { ...this.getFormDefaults(), ...demandeDeTraduction };
    form.reset(
      {
        ...demandeDeTraductionRawValue,
        id: { value: demandeDeTraductionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DemandeDeTraductionFormDefaults {
    return {
      id: null,
    };
  }
}
