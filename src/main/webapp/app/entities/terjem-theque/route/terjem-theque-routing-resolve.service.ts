import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITerjemTheque } from '../terjem-theque.model';
import { TerjemThequeService } from '../service/terjem-theque.service';

@Injectable({ providedIn: 'root' })
export class TerjemThequeRoutingResolveService implements Resolve<ITerjemTheque | null> {
  constructor(protected service: TerjemThequeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITerjemTheque | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((terjemTheque: HttpResponse<ITerjemTheque>) => {
          if (terjemTheque.body) {
            return of(terjemTheque.body);
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
