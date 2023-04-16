import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NotationComponent } from '../list/notation.component';
import { NotationDetailComponent } from '../detail/notation-detail.component';
import { NotationUpdateComponent } from '../update/notation-update.component';
import { NotationRoutingResolveService } from './notation-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const notationRoute: Routes = [
  {
    path: '',
    component: NotationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NotationDetailComponent,
    resolve: {
      notation: NotationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NotationUpdateComponent,
    resolve: {
      notation: NotationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NotationUpdateComponent,
    resolve: {
      notation: NotationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(notationRoute)],
  exports: [RouterModule],
})
export class NotationRoutingModule {}
