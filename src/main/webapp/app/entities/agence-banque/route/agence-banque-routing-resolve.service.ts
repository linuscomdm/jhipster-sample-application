import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAgenceBanque } from '../agence-banque.model';
import { AgenceBanqueService } from '../service/agence-banque.service';

@Injectable({ providedIn: 'root' })
export class AgenceBanqueRoutingResolveService implements Resolve<IAgenceBanque | null> {
  constructor(protected service: AgenceBanqueService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAgenceBanque | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((agenceBanque: HttpResponse<IAgenceBanque>) => {
          if (agenceBanque.body) {
            return of(agenceBanque.body);
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
