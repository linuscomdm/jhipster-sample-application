import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NatureDocumentATraduireComponent } from './list/nature-document-a-traduire.component';
import { NatureDocumentATraduireDetailComponent } from './detail/nature-document-a-traduire-detail.component';
import { NatureDocumentATraduireUpdateComponent } from './update/nature-document-a-traduire-update.component';
import { NatureDocumentATraduireDeleteDialogComponent } from './delete/nature-document-a-traduire-delete-dialog.component';
import { NatureDocumentATraduireRoutingModule } from './route/nature-document-a-traduire-routing.module';

@NgModule({
  imports: [SharedModule, NatureDocumentATraduireRoutingModule],
  declarations: [
    NatureDocumentATraduireComponent,
    NatureDocumentATraduireDetailComponent,
    NatureDocumentATraduireUpdateComponent,
    NatureDocumentATraduireDeleteDialogComponent,
  ],
})
export class NatureDocumentATraduireModule {}
