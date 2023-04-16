import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDocumentATraduire, NewDocumentATraduire } from '../document-a-traduire.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDocumentATraduire for edit and NewDocumentATraduireFormGroupInput for create.
 */
type DocumentATraduireFormGroupInput = IDocumentATraduire | PartialWithRequiredKeyOf<NewDocumentATraduire>;

type DocumentATraduireFormDefaults = Pick<NewDocumentATraduire, 'id'>;

type DocumentATraduireFormGroupContent = {
  id: FormControl<IDocumentATraduire['id'] | NewDocumentATraduire['id']>;
  nombreDePagesATraduire: FormControl<IDocumentATraduire['nombreDePagesATraduire']>;
  mentionTraitementParticulier: FormControl<IDocumentATraduire['mentionTraitementParticulier']>;
  remarques: FormControl<IDocumentATraduire['remarques']>;
  langueDestination: FormControl<IDocumentATraduire['langueDestination']>;
  typeDocument: FormControl<IDocumentATraduire['typeDocument']>;
  demandeTraductions: FormControl<IDocumentATraduire['demandeTraductions']>;
};

export type DocumentATraduireFormGroup = FormGroup<DocumentATraduireFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DocumentATraduireFormService {
  createDocumentATraduireFormGroup(documentATraduire: DocumentATraduireFormGroupInput = { id: null }): DocumentATraduireFormGroup {
    const documentATraduireRawValue = {
      ...this.getFormDefaults(),
      ...documentATraduire,
    };
    return new FormGroup<DocumentATraduireFormGroupContent>({
      id: new FormControl(
        { value: documentATraduireRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nombreDePagesATraduire: new FormControl(documentATraduireRawValue.nombreDePagesATraduire, {
        validators: [Validators.required],
      }),
      mentionTraitementParticulier: new FormControl(documentATraduireRawValue.mentionTraitementParticulier, {
        validators: [Validators.maxLength(500)],
      }),
      remarques: new FormControl(documentATraduireRawValue.remarques, {
        validators: [Validators.maxLength(500)],
      }),
      langueDestination: new FormControl(documentATraduireRawValue.langueDestination),
      typeDocument: new FormControl(documentATraduireRawValue.typeDocument),
      demandeTraductions: new FormControl(documentATraduireRawValue.demandeTraductions),
    });
  }

  getDocumentATraduire(form: DocumentATraduireFormGroup): IDocumentATraduire | NewDocumentATraduire {
    return form.getRawValue() as IDocumentATraduire | NewDocumentATraduire;
  }

  resetForm(form: DocumentATraduireFormGroup, documentATraduire: DocumentATraduireFormGroupInput): void {
    const documentATraduireRawValue = { ...this.getFormDefaults(), ...documentATraduire };
    form.reset(
      {
        ...documentATraduireRawValue,
        id: { value: documentATraduireRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DocumentATraduireFormDefaults {
    return {
      id: null,
    };
  }
}
