import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDocumentATraduire } from '../document-a-traduire.model';
import { DocumentATraduireService } from '../service/document-a-traduire.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './document-a-traduire-delete-dialog.component.html',
})
export class DocumentATraduireDeleteDialogComponent {
  documentATraduire?: IDocumentATraduire;

  constructor(protected documentATraduireService: DocumentATraduireService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.documentATraduireService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
