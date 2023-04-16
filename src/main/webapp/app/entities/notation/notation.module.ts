import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NotationComponent } from './list/notation.component';
import { NotationDetailComponent } from './detail/notation-detail.component';
import { NotationUpdateComponent } from './update/notation-update.component';
import { NotationDeleteDialogComponent } from './delete/notation-delete-dialog.component';
import { NotationRoutingModule } from './route/notation-routing.module';

@NgModule({
  imports: [SharedModule, NotationRoutingModule],
  declarations: [NotationComponent, NotationDetailComponent, NotationUpdateComponent, NotationDeleteDialogComponent],
})
export class NotationModule {}
