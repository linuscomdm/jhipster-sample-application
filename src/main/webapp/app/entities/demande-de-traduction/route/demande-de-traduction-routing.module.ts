import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DemandeDeTraductionComponent } from '../list/demande-de-traduction.component';
import { DemandeDeTraductionDetailComponent } from '../detail/demande-de-traduction-detail.component';
import { DemandeDeTraductionUpdateComponent } from '../update/demande-de-traduction-update.component';
import { DemandeDeTraductionRoutingResolveService } from './demande-de-traduction-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const demandeDeTraductionRoute: Routes = [
  {
    path: '',
    component: DemandeDeTraductionComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DemandeDeTraductionDetailComponent,
    resolve: {
      demandeDeTraduction: DemandeDeTraductionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DemandeDeTraductionUpdateComponent,
    resolve: {
      demandeDeTraduction: DemandeDeTraductionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DemandeDeTraductionUpdateComponent,
    resolve: {
      demandeDeTraduction: DemandeDeTraductionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(demandeDeTraductionRoute)],
  exports: [RouterModule],
})
export class DemandeDeTraductionRoutingModule {}
