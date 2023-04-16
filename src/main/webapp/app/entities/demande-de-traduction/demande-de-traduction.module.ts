import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DemandeDeTraductionComponent } from './list/demande-de-traduction.component';
import { DemandeDeTraductionDetailComponent } from './detail/demande-de-traduction-detail.component';
import { DemandeDeTraductionUpdateComponent } from './update/demande-de-traduction-update.component';
import { DemandeDeTraductionDeleteDialogComponent } from './delete/demande-de-traduction-delete-dialog.component';
import { DemandeDeTraductionRoutingModule } from './route/demande-de-traduction-routing.module';

@NgModule({
  imports: [SharedModule, DemandeDeTraductionRoutingModule],
  declarations: [
    DemandeDeTraductionComponent,
    DemandeDeTraductionDetailComponent,
    DemandeDeTraductionUpdateComponent,
    DemandeDeTraductionDeleteDialogComponent,
  ],
})
export class DemandeDeTraductionModule {}
