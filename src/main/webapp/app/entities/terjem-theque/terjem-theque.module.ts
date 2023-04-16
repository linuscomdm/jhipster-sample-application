import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TerjemThequeComponent } from './list/terjem-theque.component';
import { TerjemThequeDetailComponent } from './detail/terjem-theque-detail.component';
import { TerjemThequeUpdateComponent } from './update/terjem-theque-update.component';
import { TerjemThequeDeleteDialogComponent } from './delete/terjem-theque-delete-dialog.component';
import { TerjemThequeRoutingModule } from './route/terjem-theque-routing.module';

@NgModule({
  imports: [SharedModule, TerjemThequeRoutingModule],
  declarations: [TerjemThequeComponent, TerjemThequeDetailComponent, TerjemThequeUpdateComponent, TerjemThequeDeleteDialogComponent],
})
export class TerjemThequeModule {}
