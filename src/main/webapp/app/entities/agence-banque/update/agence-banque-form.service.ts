import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAgenceBanque, NewAgenceBanque } from '../agence-banque.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAgenceBanque for edit and NewAgenceBanqueFormGroupInput for create.
 */
type AgenceBanqueFormGroupInput = IAgenceBanque | PartialWithRequiredKeyOf<NewAgenceBanque>;

type AgenceBanqueFormDefaults = Pick<NewAgenceBanque, 'id'>;

type AgenceBanqueFormGroupContent = {
  id: FormControl<IAgenceBanque['id'] | NewAgenceBanque['id']>;
  code: FormControl<IAgenceBanque['code']>;
  libelle: FormControl<IAgenceBanque['libelle']>;
  banque: FormControl<IAgenceBanque['banque']>;
};

export type AgenceBanqueFormGroup = FormGroup<AgenceBanqueFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AgenceBanqueFormService {
  createAgenceBanqueFormGroup(agenceBanque: AgenceBanqueFormGroupInput = { id: null }): AgenceBanqueFormGroup {
    const agenceBanqueRawValue = {
      ...this.getFormDefaults(),
      ...agenceBanque,
    };
    return new FormGroup<AgenceBanqueFormGroupContent>({
      id: new FormControl(
        { value: agenceBanqueRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(agenceBanqueRawValue.code, {
        validators: [Validators.required],
      }),
      libelle: new FormControl(agenceBanqueRawValue.libelle, {
        validators: [Validators.required],
      }),
      banque: new FormControl(agenceBanqueRawValue.banque),
    });
  }

  getAgenceBanque(form: AgenceBanqueFormGroup): IAgenceBanque | NewAgenceBanque {
    return form.getRawValue() as IAgenceBanque | NewAgenceBanque;
  }

  resetForm(form: AgenceBanqueFormGroup, agenceBanque: AgenceBanqueFormGroupInput): void {
    const agenceBanqueRawValue = { ...this.getFormDefaults(), ...agenceBanque };
    form.reset(
      {
        ...agenceBanqueRawValue,
        id: { value: agenceBanqueRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AgenceBanqueFormDefaults {
    return {
      id: null,
    };
  }
}
