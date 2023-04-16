import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AgenceBanqueComponent } from './list/agence-banque.component';
import { AgenceBanqueDetailComponent } from './detail/agence-banque-detail.component';
import { AgenceBanqueUpdateComponent } from './update/agence-banque-update.component';
import { AgenceBanqueDeleteDialogComponent } from './delete/agence-banque-delete-dialog.component';
import { AgenceBanqueRoutingModule } from './route/agence-banque-routing.module';

@NgModule({
  imports: [SharedModule, AgenceBanqueRoutingModule],
  declarations: [AgenceBanqueComponent, AgenceBanqueDetailComponent, AgenceBanqueUpdateComponent, AgenceBanqueDeleteDialogComponent],
})
export class AgenceBanqueModule {}
