import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBanque, NewBanque } from '../banque.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBanque for edit and NewBanqueFormGroupInput for create.
 */
type BanqueFormGroupInput = IBanque | PartialWithRequiredKeyOf<NewBanque>;

type BanqueFormDefaults = Pick<NewBanque, 'id'>;

type BanqueFormGroupContent = {
  id: FormControl<IBanque['id'] | NewBanque['id']>;
  code: FormControl<IBanque['code']>;
  libelle: FormControl<IBanque['libelle']>;
};

export type BanqueFormGroup = FormGroup<BanqueFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BanqueFormService {
  createBanqueFormGroup(banque: BanqueFormGroupInput = { id: null }): BanqueFormGroup {
    const banqueRawValue = {
      ...this.getFormDefaults(),
      ...banque,
    };
    return new FormGroup<BanqueFormGroupContent>({
      id: new FormControl(
        { value: banqueRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(banqueRawValue.code, {
        validators: [Validators.required],
      }),
      libelle: new FormControl(banqueRawValue.libelle, {
        validators: [Validators.required],
      }),
    });
  }

  getBanque(form: BanqueFormGroup): IBanque | NewBanque {
    return form.getRawValue() as IBanque | NewBanque;
  }

  resetForm(form: BanqueFormGroup, banque: BanqueFormGroupInput): void {
    const banqueRawValue = { ...this.getFormDefaults(), ...banque };
    form.reset(
      {
        ...banqueRawValue,
        id: { value: banqueRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): BanqueFormDefaults {
    return {
      id: null,
    };
  }
}
