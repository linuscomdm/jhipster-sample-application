import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPieceJointe, NewPieceJointe } from '../piece-jointe.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPieceJointe for edit and NewPieceJointeFormGroupInput for create.
 */
type PieceJointeFormGroupInput = IPieceJointe | PartialWithRequiredKeyOf<NewPieceJointe>;

type PieceJointeFormDefaults = Pick<NewPieceJointe, 'id'>;

type PieceJointeFormGroupContent = {
  id: FormControl<IPieceJointe['id'] | NewPieceJointe['id']>;
  nomFichier: FormControl<IPieceJointe['nomFichier']>;
  chemin: FormControl<IPieceJointe['chemin']>;
  urlPiece: FormControl<IPieceJointe['urlPiece']>;
  description: FormControl<IPieceJointe['description']>;
  codePiece: FormControl<IPieceJointe['codePiece']>;
  libellePiece: FormControl<IPieceJointe['libellePiece']>;
  rattachPj: FormControl<IPieceJointe['rattachPj']>;
  rattachPjContentType: FormControl<IPieceJointe['rattachPjContentType']>;
  dateCreation: FormControl<IPieceJointe['dateCreation']>;
  pjDdeTraductions: FormControl<IPieceJointe['pjDdeTraductions']>;
  prestataire: FormControl<IPieceJointe['prestataire']>;
};

export type PieceJointeFormGroup = FormGroup<PieceJointeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PieceJointeFormService {
  createPieceJointeFormGroup(pieceJointe: PieceJointeFormGroupInput = { id: null }): PieceJointeFormGroup {
    const pieceJointeRawValue = {
      ...this.getFormDefaults(),
      ...pieceJointe,
    };
    return new FormGroup<PieceJointeFormGroupContent>({
      id: new FormControl(
        { value: pieceJointeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nomFichier: new FormControl(pieceJointeRawValue.nomFichier, {
        validators: [Validators.required, Validators.maxLength(150)],
      }),
      chemin: new FormControl(pieceJointeRawValue.chemin, {
        validators: [Validators.required, Validators.maxLength(500)],
      }),
      urlPiece: new FormControl(pieceJointeRawValue.urlPiece, {
        validators: [Validators.required, Validators.maxLength(500)],
      }),
      description: new FormControl(pieceJointeRawValue.description),
      codePiece: new FormControl(pieceJointeRawValue.codePiece),
      libellePiece: new FormControl(pieceJointeRawValue.libellePiece),
      rattachPj: new FormControl(pieceJointeRawValue.rattachPj),
      rattachPjContentType: new FormControl(pieceJointeRawValue.rattachPjContentType),
      dateCreation: new FormControl(pieceJointeRawValue.dateCreation),
      pjDdeTraductions: new FormControl(pieceJointeRawValue.pjDdeTraductions),
      prestataire: new FormControl(pieceJointeRawValue.prestataire),
    });
  }

  getPieceJointe(form: PieceJointeFormGroup): IPieceJointe | NewPieceJointe {
    return form.getRawValue() as IPieceJointe | NewPieceJointe;
  }

  resetForm(form: PieceJointeFormGroup, pieceJointe: PieceJointeFormGroupInput): void {
    const pieceJointeRawValue = { ...this.getFormDefaults(), ...pieceJointe };
    form.reset(
      {
        ...pieceJointeRawValue,
        id: { value: pieceJointeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PieceJointeFormDefaults {
    return {
      id: null,
    };
  }
}
