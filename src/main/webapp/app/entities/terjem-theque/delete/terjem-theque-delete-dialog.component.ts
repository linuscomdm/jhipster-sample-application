import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITerjemTheque } from '../terjem-theque.model';
import { TerjemThequeService } from '../service/terjem-theque.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './terjem-theque-delete-dialog.component.html',
})
export class TerjemThequeDeleteDialogComponent {
  terjemTheque?: ITerjemTheque;

  constructor(protected terjemThequeService: TerjemThequeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.terjemThequeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
