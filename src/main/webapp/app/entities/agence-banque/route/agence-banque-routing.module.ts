import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AgenceBanqueComponent } from '../list/agence-banque.component';
import { AgenceBanqueDetailComponent } from '../detail/agence-banque-detail.component';
import { AgenceBanqueUpdateComponent } from '../update/agence-banque-update.component';
import { AgenceBanqueRoutingResolveService } from './agence-banque-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const agenceBanqueRoute: Routes = [
  {
    path: '',
    component: AgenceBanqueComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AgenceBanqueDetailComponent,
    resolve: {
      agenceBanque: AgenceBanqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AgenceBanqueUpdateComponent,
    resolve: {
      agenceBanque: AgenceBanqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AgenceBanqueUpdateComponent,
    resolve: {
      agenceBanque: AgenceBanqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(agenceBanqueRoute)],
  exports: [RouterModule],
})
export class AgenceBanqueRoutingModule {}
