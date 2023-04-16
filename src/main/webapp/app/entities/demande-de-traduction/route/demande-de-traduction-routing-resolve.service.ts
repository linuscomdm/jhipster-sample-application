import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDemandeDeTraduction } from '../demande-de-traduction.model';
import { DemandeDeTraductionService } from '../service/demande-de-traduction.service';

@Injectable({ providedIn: 'root' })
export class DemandeDeTraductionRoutingResolveService implements Resolve<IDemandeDeTraduction | null> {
  constructor(protected service: DemandeDeTraductionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDemandeDeTraduction | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((demandeDeTraduction: HttpResponse<IDemandeDeTraduction>) => {
          if (demandeDeTraduction.body) {
            return of(demandeDeTraduction.body);
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
