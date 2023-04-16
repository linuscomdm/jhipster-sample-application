import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DetailDevisComponent } from '../list/detail-devis.component';
import { DetailDevisDetailComponent } from '../detail/detail-devis-detail.component';
import { DetailDevisUpdateComponent } from '../update/detail-devis-update.component';
import { DetailDevisRoutingResolveService } from './detail-devis-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const detailDevisRoute: Routes = [
  {
    path: '',
    component: DetailDevisComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DetailDevisDetailComponent,
    resolve: {
      detailDevis: DetailDevisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DetailDevisUpdateComponent,
    resolve: {
      detailDevis: DetailDevisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DetailDevisUpdateComponent,
    resolve: {
      detailDevis: DetailDevisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(detailDevisRoute)],
  exports: [RouterModule],
})
export class DetailDevisRoutingModule {}
