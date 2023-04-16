import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDemandeur } from '../demandeur.model';
import { DemandeurService } from '../service/demandeur.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './demandeur-delete-dialog.component.html',
})
export class DemandeurDeleteDialogComponent {
  demandeur?: IDemandeur;

  constructor(protected demandeurService: DemandeurService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.demandeurService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
