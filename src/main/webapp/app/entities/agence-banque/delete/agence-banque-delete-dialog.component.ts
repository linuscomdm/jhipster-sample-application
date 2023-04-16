import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAgenceBanque } from '../agence-banque.model';
import { AgenceBanqueService } from '../service/agence-banque.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './agence-banque-delete-dialog.component.html',
})
export class AgenceBanqueDeleteDialogComponent {
  agenceBanque?: IAgenceBanque;

  constructor(protected agenceBanqueService: AgenceBanqueService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.agenceBanqueService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
