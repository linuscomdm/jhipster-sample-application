import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommentaire } from '../commentaire.model';
import { CommentaireService } from '../service/commentaire.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './commentaire-delete-dialog.component.html',
})
export class CommentaireDeleteDialogComponent {
  commentaire?: ICommentaire;

  constructor(protected commentaireService: CommentaireService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commentaireService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
