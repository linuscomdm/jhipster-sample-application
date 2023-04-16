import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICommentaire } from '../commentaire.model';
import { CommentaireService } from '../service/commentaire.service';

@Injectable({ providedIn: 'root' })
export class CommentaireRoutingResolveService implements Resolve<ICommentaire | null> {
  constructor(protected service: CommentaireService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommentaire | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((commentaire: HttpResponse<ICommentaire>) => {
          if (commentaire.body) {
            return of(commentaire.body);
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
