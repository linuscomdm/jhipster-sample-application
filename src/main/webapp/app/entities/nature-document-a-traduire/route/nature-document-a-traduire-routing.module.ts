import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NatureDocumentATraduireComponent } from '../list/nature-document-a-traduire.component';
import { NatureDocumentATraduireDetailComponent } from '../detail/nature-document-a-traduire-detail.component';
import { NatureDocumentATraduireUpdateComponent } from '../update/nature-document-a-traduire-update.component';
import { NatureDocumentATraduireRoutingResolveService } from './nature-document-a-traduire-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const natureDocumentATraduireRoute: Routes = [
  {
    path: '',
    component: NatureDocumentATraduireComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NatureDocumentATraduireDetailComponent,
    resolve: {
      natureDocumentATraduire: NatureDocumentATraduireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NatureDocumentATraduireUpdateComponent,
    resolve: {
      natureDocumentATraduire: NatureDocumentATraduireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NatureDocumentATraduireUpdateComponent,
    resolve: {
      natureDocumentATraduire: NatureDocumentATraduireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(natureDocumentATraduireRoute)],
  exports: [RouterModule],
})
export class NatureDocumentATraduireRoutingModule {}
