import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DetailDevisComponent } from './list/detail-devis.component';
import { DetailDevisDetailComponent } from './detail/detail-devis-detail.component';
import { DetailDevisUpdateComponent } from './update/detail-devis-update.component';
import { DetailDevisDeleteDialogComponent } from './delete/detail-devis-delete-dialog.component';
import { DetailDevisRoutingModule } from './route/detail-devis-routing.module';

@NgModule({
  imports: [SharedModule, DetailDevisRoutingModule],
  declarations: [DetailDevisComponent, DetailDevisDetailComponent, DetailDevisUpdateComponent, DetailDevisDeleteDialogComponent],
})
export class DetailDevisModule {}
