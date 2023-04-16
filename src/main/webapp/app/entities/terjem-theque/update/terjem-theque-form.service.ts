import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITerjemTheque, NewTerjemTheque } from '../terjem-theque.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITerjemTheque for edit and NewTerjemThequeFormGroupInput for create.
 */
type TerjemThequeFormGroupInput = ITerjemTheque | PartialWithRequiredKeyOf<NewTerjemTheque>;

type TerjemThequeFormDefaults = Pick<NewTerjemTheque, 'id'>;

type TerjemThequeFormGroupContent = {
  id: FormControl<ITerjemTheque['id'] | NewTerjemTheque['id']>;
  lienDownload: FormControl<ITerjemTheque['lienDownload']>;
  formatDocTraduit: FormControl<ITerjemTheque['formatDocTraduit']>;
  nomFichier: FormControl<ITerjemTheque['nomFichier']>;
  docTraduit: FormControl<ITerjemTheque['docTraduit']>;
  docTraduitContentType: FormControl<ITerjemTheque['docTraduitContentType']>;
  dateCreation: FormControl<ITerjemTheque['dateCreation']>;
  etat: FormControl<ITerjemTheque['etat']>;
  prestataire: FormControl<ITerjemTheque['prestataire']>;
  demandeur: FormControl<ITerjemTheque['demandeur']>;
};

export type TerjemThequeFormGroup = FormGroup<TerjemThequeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TerjemThequeFormService {
  createTerjemThequeFormGroup(terjemTheque: TerjemThequeFormGroupInput = { id: null }): TerjemThequeFormGroup {
    const terjemThequeRawValue = {
      ...this.getFormDefaults(),
      ...terjemTheque,
    };
    return new FormGroup<TerjemThequeFormGroupContent>({
      id: new FormControl(
        { value: terjemThequeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      lienDownload: new FormControl(terjemThequeRawValue.lienDownload, {
        validators: [Validators.required, Validators.maxLength(500)],
      }),
      formatDocTraduit: new FormControl(terjemThequeRawValue.formatDocTraduit, {
        validators: [Validators.required],
      }),
      nomFichier: new FormControl(terjemThequeRawValue.nomFichier, {
        validators: [Validators.required, Validators.maxLength(150)],
      }),
      docTraduit: new FormControl(terjemThequeRawValue.docTraduit),
      docTraduitContentType: new FormControl(terjemThequeRawValue.docTraduitContentType),
      dateCreation: new FormControl(terjemThequeRawValue.dateCreation),
      etat: new FormControl(terjemThequeRawValue.etat),
      prestataire: new FormControl(terjemThequeRawValue.prestataire),
      demandeur: new FormControl(terjemThequeRawValue.demandeur),
    });
  }

  getTerjemTheque(form: TerjemThequeFormGroup): ITerjemTheque | NewTerjemTheque {
    return form.getRawValue() as ITerjemTheque | NewTerjemTheque;
  }

  resetForm(form: TerjemThequeFormGroup, terjemTheque: TerjemThequeFormGroupInput): void {
    const terjemThequeRawValue = { ...this.getFormDefaults(), ...terjemTheque };
    form.reset(
      {
        ...terjemThequeRawValue,
        id: { value: terjemThequeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TerjemThequeFormDefaults {
    return {
      id: null,
    };
  }
}
