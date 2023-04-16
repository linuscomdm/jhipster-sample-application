import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DocumentATraduireComponent } from '../list/document-a-traduire.component';
import { DocumentATraduireDetailComponent } from '../detail/document-a-traduire-detail.component';
import { DocumentATraduireUpdateComponent } from '../update/document-a-traduire-update.component';
import { DocumentATraduireRoutingResolveService } from './document-a-traduire-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const documentATraduireRoute: Routes = [
  {
    path: '',
    component: DocumentATraduireComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DocumentATraduireDetailComponent,
    resolve: {
      documentATraduire: DocumentATraduireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DocumentATraduireUpdateComponent,
    resolve: {
      documentATraduire: DocumentATraduireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DocumentATraduireUpdateComponent,
    resolve: {
      documentATraduire: DocumentATraduireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(documentATraduireRoute)],
  exports: [RouterModule],
})
export class DocumentATraduireRoutingModule {}
