import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDemandeDeTraduction } from '../demande-de-traduction.model';
import { DemandeDeTraductionService } from '../service/demande-de-traduction.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './demande-de-traduction-delete-dialog.component.html',
})
export class DemandeDeTraductionDeleteDialogComponent {
  demandeDeTraduction?: IDemandeDeTraduction;

  constructor(protected demandeDeTraductionService: DemandeDeTraductionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.demandeDeTraductionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
