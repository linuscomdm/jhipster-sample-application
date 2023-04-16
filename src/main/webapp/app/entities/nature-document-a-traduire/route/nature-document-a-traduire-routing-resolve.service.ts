import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INatureDocumentATraduire } from '../nature-document-a-traduire.model';
import { NatureDocumentATraduireService } from '../service/nature-document-a-traduire.service';

@Injectable({ providedIn: 'root' })
export class NatureDocumentATraduireRoutingResolveService implements Resolve<INatureDocumentATraduire | null> {
  constructor(protected service: NatureDocumentATraduireService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INatureDocumentATraduire | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((natureDocumentATraduire: HttpResponse<INatureDocumentATraduire>) => {
          if (natureDocumentATraduire.body) {
            return of(natureDocumentATraduire.body);
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
