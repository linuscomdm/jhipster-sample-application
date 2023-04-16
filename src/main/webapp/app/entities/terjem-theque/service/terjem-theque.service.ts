import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITerjemTheque, NewTerjemTheque } from '../terjem-theque.model';

export type PartialUpdateTerjemTheque = Partial<ITerjemTheque> & Pick<ITerjemTheque, 'id'>;

type RestOf<T extends ITerjemTheque | NewTerjemTheque> = Omit<T, 'dateCreation'> & {
  dateCreation?: string | null;
};

export type RestTerjemTheque = RestOf<ITerjemTheque>;

export type NewRestTerjemTheque = RestOf<NewTerjemTheque>;

export type PartialUpdateRestTerjemTheque = RestOf<PartialUpdateTerjemTheque>;

export type EntityResponseType = HttpResponse<ITerjemTheque>;
export type EntityArrayResponseType = HttpResponse<ITerjemTheque[]>;

@Injectable({ providedIn: 'root' })
export class TerjemThequeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/terjem-theques');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(terjemTheque: NewTerjemTheque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(terjemTheque);
    return this.http
      .post<RestTerjemTheque>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(terjemTheque: ITerjemTheque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(terjemTheque);
    return this.http
      .put<RestTerjemTheque>(`${this.resourceUrl}/${this.getTerjemThequeIdentifier(terjemTheque)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(terjemTheque: PartialUpdateTerjemTheque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(terjemTheque);
    return this.http
      .patch<RestTerjemTheque>(`${this.resourceUrl}/${this.getTerjemThequeIdentifier(terjemTheque)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTerjemTheque>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTerjemTheque[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTerjemThequeIdentifier(terjemTheque: Pick<ITerjemTheque, 'id'>): number {
    return terjemTheque.id;
  }

  compareTerjemTheque(o1: Pick<ITerjemTheque, 'id'> | null, o2: Pick<ITerjemTheque, 'id'> | null): boolean {
    return o1 && o2 ? this.getTerjemThequeIdentifier(o1) === this.getTerjemThequeIdentifier(o2) : o1 === o2;
  }

  addTerjemThequeToCollectionIfMissing<Type extends Pick<ITerjemTheque, 'id'>>(
    terjemThequeCollection: Type[],
    ...terjemThequesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const terjemTheques: Type[] = terjemThequesToCheck.filter(isPresent);
    if (terjemTheques.length > 0) {
      const terjemThequeCollectionIdentifiers = terjemThequeCollection.map(
        terjemThequeItem => this.getTerjemThequeIdentifier(terjemThequeItem)!
      );
      const terjemThequesToAdd = terjemTheques.filter(terjemThequeItem => {
        const terjemThequeIdentifier = this.getTerjemThequeIdentifier(terjemThequeItem);
        if (terjemThequeCollectionIdentifiers.includes(terjemThequeIdentifier)) {
          return false;
        }
        terjemThequeCollectionIdentifiers.push(terjemThequeIdentifier);
        return true;
      });
      return [...terjemThequesToAdd, ...terjemThequeCollection];
    }
    return terjemThequeCollection;
  }

  protected convertDateFromClient<T extends ITerjemTheque | NewTerjemTheque | PartialUpdateTerjemTheque>(terjemTheque: T): RestOf<T> {
    return {
      ...terjemTheque,
      dateCreation: terjemTheque.dateCreation?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restTerjemTheque: RestTerjemTheque): ITerjemTheque {
    return {
      ...restTerjemTheque,
      dateCreation: restTerjemTheque.dateCreation ? dayjs(restTerjemTheque.dateCreation) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTerjemTheque>): HttpResponse<ITerjemTheque> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTerjemTheque[]>): HttpResponse<ITerjemTheque[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
