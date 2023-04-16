import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDetailDevis, NewDetailDevis } from '../detail-devis.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDetailDevis for edit and NewDetailDevisFormGroupInput for create.
 */
type DetailDevisFormGroupInput = IDetailDevis | PartialWithRequiredKeyOf<NewDetailDevis>;

type DetailDevisFormDefaults = Pick<NewDetailDevis, 'id'>;

type DetailDevisFormGroupContent = {
  id: FormControl<IDetailDevis['id'] | NewDetailDevis['id']>;
  qte: FormControl<IDetailDevis['qte']>;
  prixUnitaire: FormControl<IDetailDevis['prixUnitaire']>;
  prixTotal: FormControl<IDetailDevis['prixTotal']>;
  etat: FormControl<IDetailDevis['etat']>;
  devis: FormControl<IDetailDevis['devis']>;
};

export type DetailDevisFormGroup = FormGroup<DetailDevisFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DetailDevisFormService {
  createDetailDevisFormGroup(detailDevis: DetailDevisFormGroupInput = { id: null }): DetailDevisFormGroup {
    const detailDevisRawValue = {
      ...this.getFormDefaults(),
      ...detailDevis,
    };
    return new FormGroup<DetailDevisFormGroupContent>({
      id: new FormControl(
        { value: detailDevisRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      qte: new FormControl(detailDevisRawValue.qte, {
        validators: [Validators.required],
      }),
      prixUnitaire: new FormControl(detailDevisRawValue.prixUnitaire, {
        validators: [Validators.required],
      }),
      prixTotal: new FormControl(detailDevisRawValue.prixTotal, {
        validators: [Validators.required],
      }),
      etat: new FormControl(detailDevisRawValue.etat, {
        validators: [Validators.required],
      }),
      devis: new FormControl(detailDevisRawValue.devis),
    });
  }

  getDetailDevis(form: DetailDevisFormGroup): IDetailDevis | NewDetailDevis {
    return form.getRawValue() as IDetailDevis | NewDetailDevis;
  }

  resetForm(form: DetailDevisFormGroup, detailDevis: DetailDevisFormGroupInput): void {
    const detailDevisRawValue = { ...this.getFormDefaults(), ...detailDevis };
    form.reset(
      {
        ...detailDevisRawValue,
        id: { value: detailDevisRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DetailDevisFormDefaults {
    return {
      id: null,
    };
  }
}
