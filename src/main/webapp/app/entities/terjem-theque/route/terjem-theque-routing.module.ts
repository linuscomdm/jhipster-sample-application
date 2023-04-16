import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TerjemThequeComponent } from '../list/terjem-theque.component';
import { TerjemThequeDetailComponent } from '../detail/terjem-theque-detail.component';
import { TerjemThequeUpdateComponent } from '../update/terjem-theque-update.component';
import { TerjemThequeRoutingResolveService } from './terjem-theque-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const terjemThequeRoute: Routes = [
  {
    path: '',
    component: TerjemThequeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TerjemThequeDetailComponent,
    resolve: {
      terjemTheque: TerjemThequeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TerjemThequeUpdateComponent,
    resolve: {
      terjemTheque: TerjemThequeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TerjemThequeUpdateComponent,
    resolve: {
      terjemTheque: TerjemThequeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(terjemThequeRoute)],
  exports: [RouterModule],
})
export class TerjemThequeRoutingModule {}
