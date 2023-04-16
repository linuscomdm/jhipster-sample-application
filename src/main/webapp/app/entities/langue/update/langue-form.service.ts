import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILangue, NewLangue } from '../langue.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILangue for edit and NewLangueFormGroupInput for create.
 */
type LangueFormGroupInput = ILangue | PartialWithRequiredKeyOf<NewLangue>;

type LangueFormDefaults = Pick<NewLangue, 'id'>;

type LangueFormGroupContent = {
  id: FormControl<ILangue['id'] | NewLangue['id']>;
  codeLangue: FormControl<ILangue['codeLangue']>;
  nomLangue: FormControl<ILangue['nomLangue']>;
  docTraductions: FormControl<ILangue['docTraductions']>;
};

export type LangueFormGroup = FormGroup<LangueFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LangueFormService {
  createLangueFormGroup(langue: LangueFormGroupInput = { id: null }): LangueFormGroup {
    const langueRawValue = {
      ...this.getFormDefaults(),
      ...langue,
    };
    return new FormGroup<LangueFormGroupContent>({
      id: new FormControl(
        { value: langueRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      codeLangue: new FormControl(langueRawValue.codeLangue),
      nomLangue: new FormControl(langueRawValue.nomLangue),
      docTraductions: new FormControl(langueRawValue.docTraductions),
    });
  }

  getLangue(form: LangueFormGroup): ILangue | NewLangue {
    return form.getRawValue() as ILangue | NewLangue;
  }

  resetForm(form: LangueFormGroup, langue: LangueFormGroupInput): void {
    const langueRawValue = { ...this.getFormDefaults(), ...langue };
    form.reset(
      {
        ...langueRawValue,
        id: { value: langueRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LangueFormDefaults {
    return {
      id: null,
    };
  }
}
