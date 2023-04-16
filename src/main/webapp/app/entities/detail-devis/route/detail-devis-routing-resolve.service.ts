import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDetailDevis } from '../detail-devis.model';
import { DetailDevisService } from '../service/detail-devis.service';

@Injectable({ providedIn: 'root' })
export class DetailDevisRoutingResolveService implements Resolve<IDetailDevis | null> {
  constructor(protected service: DetailDevisService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDetailDevis | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((detailDevis: HttpResponse<IDetailDevis>) => {
          if (detailDevis.body) {
            return of(detailDevis.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
