import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDevis, NewDevis } from '../devis.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDevis for edit and NewDevisFormGroupInput for create.
 */
type DevisFormGroupInput = IDevis | PartialWithRequiredKeyOf<NewDevis>;

type DevisFormDefaults = Pick<NewDevis, 'id'>;

type DevisFormGroupContent = {
  id: FormControl<IDevis['id'] | NewDevis['id']>;
  numero: FormControl<IDevis['numero']>;
  date: FormControl<IDevis['date']>;
  prixTotal: FormControl<IDevis['prixTotal']>;
  etat: FormControl<IDevis['etat']>;
  prestataire: FormControl<IDevis['prestataire']>;
  demandeDeTraduction: FormControl<IDevis['demandeDeTraduction']>;
};

export type DevisFormGroup = FormGroup<DevisFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DevisFormService {
  createDevisFormGroup(devis: DevisFormGroupInput = { id: null }): DevisFormGroup {
    const devisRawValue = {
      ...this.getFormDefaults(),
      ...devis,
    };
    return new FormGroup<DevisFormGroupContent>({
      id: new FormControl(
        { value: devisRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      numero: new FormControl(devisRawValue.numero, {
        validators: [Validators.required, Validators.minLength(2), Validators.maxLength(30)],
      }),
      date: new FormControl(devisRawValue.date, {
        validators: [Validators.required],
      }),
      prixTotal: new FormControl(devisRawValue.prixTotal, {
        validators: [Validators.required],
      }),
      etat: new FormControl(devisRawValue.etat, {
        validators: [Validators.required],
      }),
      prestataire: new FormControl(devisRawValue.prestataire),
      demandeDeTraduction: new FormControl(devisRawValue.demandeDeTraduction),
    });
  }

  getDevis(form: DevisFormGroup): IDevis | NewDevis {
    return form.getRawValue() as IDevis | NewDevis;
  }

  resetForm(form: DevisFormGroup, devis: DevisFormGroupInput): void {
    const devisRawValue = { ...this.getFormDefaults(), ...devis };
    form.reset(
      {
        ...devisRawValue,
        id: { value: devisRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DevisFormDefaults {
    return {
      id: null,
    };
  }
}
