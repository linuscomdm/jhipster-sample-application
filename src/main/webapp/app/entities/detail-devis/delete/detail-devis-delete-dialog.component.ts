import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDetailDevis } from '../detail-devis.model';
import { DetailDevisService } from '../service/detail-devis.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './detail-devis-delete-dialog.component.html',
})
export class DetailDevisDeleteDialogComponent {
  detailDevis?: IDetailDevis;

  constructor(protected detailDevisService: DetailDevisService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.detailDevisService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
