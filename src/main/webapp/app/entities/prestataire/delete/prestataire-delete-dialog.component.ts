import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrestataire } from '../prestataire.model';
import { PrestataireService } from '../service/prestataire.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './prestataire-delete-dialog.component.html',
})
export class PrestataireDeleteDialogComponent {
  prestataire?: IPrestataire;

  constructor(protected prestataireService: PrestataireService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.prestataireService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
