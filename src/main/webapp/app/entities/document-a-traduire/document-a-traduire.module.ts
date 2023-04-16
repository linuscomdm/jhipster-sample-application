import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DocumentATraduireComponent } from './list/document-a-traduire.component';
import { DocumentATraduireDetailComponent } from './detail/document-a-traduire-detail.component';
import { DocumentATraduireUpdateComponent } from './update/document-a-traduire-update.component';
import { DocumentATraduireDeleteDialogComponent } from './delete/document-a-traduire-delete-dialog.component';
import { DocumentATraduireRoutingModule } from './route/document-a-traduire-routing.module';

@NgModule({
  imports: [SharedModule, DocumentATraduireRoutingModule],
  declarations: [
    DocumentATraduireComponent,
    DocumentATraduireDetailComponent,
    DocumentATraduireUpdateComponent,
    DocumentATraduireDeleteDialogComponent,
  ],
})
export class DocumentATraduireModule {}
