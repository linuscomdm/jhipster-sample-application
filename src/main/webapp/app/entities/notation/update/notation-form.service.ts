import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INotation, NewNotation } from '../notation.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INotation for edit and NewNotationFormGroupInput for create.
 */
type NotationFormGroupInput = INotation | PartialWithRequiredKeyOf<NewNotation>;

type NotationFormDefaults = Pick<NewNotation, 'id'>;

type NotationFormGroupContent = {
  id: FormControl<INotation['id'] | NewNotation['id']>;
  notetation: FormControl<INotation['notetation']>;
  commentaire: FormControl<INotation['commentaire']>;
  dateCreation: FormControl<INotation['dateCreation']>;
  demandeur: FormControl<INotation['demandeur']>;
  prestataire: FormControl<INotation['prestataire']>;
};

export type NotationFormGroup = FormGroup<NotationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NotationFormService {
  createNotationFormGroup(notation: NotationFormGroupInput = { id: null }): NotationFormGroup {
    const notationRawValue = {
      ...this.getFormDefaults(),
      ...notation,
    };
    return new FormGroup<NotationFormGroupContent>({
      id: new FormControl(
        { value: notationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      notetation: new FormControl(notationRawValue.notetation, {
        validators: [Validators.required],
      }),
      commentaire: new FormControl(notationRawValue.commentaire, {
        validators: [Validators.maxLength(150)],
      }),
      dateCreation: new FormControl(notationRawValue.dateCreation),
      demandeur: new FormControl(notationRawValue.demandeur),
      prestataire: new FormControl(notationRawValue.prestataire),
    });
  }

  getNotation(form: NotationFormGroup): INotation | NewNotation {
    return form.getRawValue() as INotation | NewNotation;
  }

  resetForm(form: NotationFormGroup, notation: NotationFormGroupInput): void {
    const notationRawValue = { ...this.getFormDefaults(), ...notation };
    form.reset(
      {
        ...notationRawValue,
        id: { value: notationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NotationFormDefaults {
    return {
      id: null,
    };
  }
}
