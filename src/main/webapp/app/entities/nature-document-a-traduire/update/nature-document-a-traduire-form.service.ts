import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INatureDocumentATraduire, NewNatureDocumentATraduire } from '../nature-document-a-traduire.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INatureDocumentATraduire for edit and NewNatureDocumentATraduireFormGroupInput for create.
 */
type NatureDocumentATraduireFormGroupInput = INatureDocumentATraduire | PartialWithRequiredKeyOf<NewNatureDocumentATraduire>;

type NatureDocumentATraduireFormDefaults = Pick<NewNatureDocumentATraduire, 'id'>;

type NatureDocumentATraduireFormGroupContent = {
  id: FormControl<INatureDocumentATraduire['id'] | NewNatureDocumentATraduire['id']>;
  codeType: FormControl<INatureDocumentATraduire['codeType']>;
  typeDocument: FormControl<INatureDocumentATraduire['typeDocument']>;
};

export type NatureDocumentATraduireFormGroup = FormGroup<NatureDocumentATraduireFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NatureDocumentATraduireFormService {
  createNatureDocumentATraduireFormGroup(
    natureDocumentATraduire: NatureDocumentATraduireFormGroupInput = { id: null }
  ): NatureDocumentATraduireFormGroup {
    const natureDocumentATraduireRawValue = {
      ...this.getFormDefaults(),
      ...natureDocumentATraduire,
    };
    return new FormGroup<NatureDocumentATraduireFormGroupContent>({
      id: new FormControl(
        { value: natureDocumentATraduireRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      codeType: new FormControl(natureDocumentATraduireRawValue.codeType),
      typeDocument: new FormControl(natureDocumentATraduireRawValue.typeDocument),
    });
  }

  getNatureDocumentATraduire(form: NatureDocumentATraduireFormGroup): INatureDocumentATraduire | NewNatureDocumentATraduire {
    return form.getRawValue() as INatureDocumentATraduire | NewNatureDocumentATraduire;
  }

  resetForm(form: NatureDocumentATraduireFormGroup, natureDocumentATraduire: NatureDocumentATraduireFormGroupInput): void {
    const natureDocumentATraduireRawValue = { ...this.getFormDefaults(), ...natureDocumentATraduire };
    form.reset(
      {
        ...natureDocumentATraduireRawValue,
        id: { value: natureDocumentATraduireRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NatureDocumentATraduireFormDefaults {
    return {
      id: null,
    };
  }
}
