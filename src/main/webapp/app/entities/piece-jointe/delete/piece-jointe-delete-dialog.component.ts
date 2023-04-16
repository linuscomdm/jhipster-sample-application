import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPieceJointe } from '../piece-jointe.model';
import { PieceJointeService } from '../service/piece-jointe.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './piece-jointe-delete-dialog.component.html',
})
export class PieceJointeDeleteDialogComponent {
  pieceJointe?: IPieceJointe;

  constructor(protected pieceJointeService: PieceJointeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pieceJointeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
