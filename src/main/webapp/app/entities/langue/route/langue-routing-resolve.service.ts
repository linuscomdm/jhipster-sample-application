import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILangue } from '../langue.model';
import { LangueService } from '../service/langue.service';

@Injectable({ providedIn: 'root' })
export class LangueRoutingResolveService implements Resolve<ILangue | null> {
  constructor(protected service: LangueService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILangue | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((langue: HttpResponse<ILangue>) => {
          if (langue.body) {
            return of(langue.body);
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
