import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INatureDocumentATraduire } from '../nature-document-a-traduire.model';
import { NatureDocumentATraduireService } from '../service/nature-document-a-traduire.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './nature-document-a-traduire-delete-dialog.component.html',
})
export class NatureDocumentATraduireDeleteDialogComponent {
  natureDocumentATraduire?: INatureDocumentATraduire;

  constructor(protected natureDocumentATraduireService: NatureDocumentATraduireService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.natureDocumentATraduireService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
