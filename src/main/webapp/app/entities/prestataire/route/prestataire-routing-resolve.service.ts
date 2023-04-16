import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPrestataire } from '../prestataire.model';
import { PrestataireService } from '../service/prestataire.service';

@Injectable({ providedIn: 'root' })
export class PrestataireRoutingResolveService implements Resolve<IPrestataire | null> {
  constructor(protected service: PrestataireService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrestataire | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((prestataire: HttpResponse<IPrestataire>) => {
          if (prestataire.body) {
            return of(prestataire.body);
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
