import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDocumentATraduire } from '../document-a-traduire.model';
import { DocumentATraduireService } from '../service/document-a-traduire.service';

@Injectable({ providedIn: 'root' })
export class DocumentATraduireRoutingResolveService implements Resolve<IDocumentATraduire | null> {
  constructor(protected service: DocumentATraduireService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocumentATraduire | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((documentATraduire: HttpResponse<IDocumentATraduire>) => {
          if (documentATraduire.body) {
            return of(documentATraduire.body);
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
