import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDemandeur } from '../demandeur.model';
import { DemandeurService } from '../service/demandeur.service';

@Injectable({ providedIn: 'root' })
export class DemandeurRoutingResolveService implements Resolve<IDemandeur | null> {
  constructor(protected service: DemandeurService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDemandeur | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((demandeur: HttpResponse<IDemandeur>) => {
          if (demandeur.body) {
            return of(demandeur.body);
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
