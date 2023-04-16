import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INotation, NewNotation } from '../notation.model';

export type PartialUpdateNotation = Partial<INotation> & Pick<INotation, 'id'>;

type RestOf<T extends INotation | NewNotation> = Omit<T, 'dateCreation'> & {
  dateCreation?: string | null;
};

export type RestNotation = RestOf<INotation>;

export type NewRestNotation = RestOf<NewNotation>;

export type PartialUpdateRestNotation = RestOf<PartialUpdateNotation>;

export type EntityResponseType = HttpResponse<INotation>;
export type EntityArrayResponseType = HttpResponse<INotation[]>;

@Injectable({ providedIn: 'root' })
export class NotationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/notations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(notation: NewNotation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notation);
    return this.http
      .post<RestNotation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(notation: INotation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notation);
    return this.http
      .put<RestNotation>(`${this.resourceUrl}/${this.getNotationIdentifier(notation)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(notation: PartialUpdateNotation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notation);
    return this.http
      .patch<RestNotation>(`${this.resourceUrl}/${this.getNotationIdentifier(notation)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestNotation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestNotation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNotationIdentifier(notation: Pick<INotation, 'id'>): number {
    return notation.id;
  }

  compareNotation(o1: Pick<INotation, 'id'> | null, o2: Pick<INotation, 'id'> | null): boolean {
    return o1 && o2 ? this.getNotationIdentifier(o1) === this.getNotationIdentifier(o2) : o1 === o2;
  }

  addNotationToCollectionIfMissing<Type extends Pick<INotation, 'id'>>(
    notationCollection: Type[],
    ...notationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const notations: Type[] = notationsToCheck.filter(isPresent);
    if (notations.length > 0) {
      const notationCollectionIdentifiers = notationCollection.map(notationItem => this.getNotationIdentifier(notationItem)!);
      const notationsToAdd = notations.filter(notationItem => {
        const notationIdentifier = this.getNotationIdentifier(notationItem);
        if (notationCollectionIdentifiers.includes(notationIdentifier)) {
          return false;
        }
        notationCollectionIdentifiers.push(notationIdentifier);
        return true;
      });
      return [...notationsToAdd, ...notationCollection];
    }
    return notationCollection;
  }

  protected convertDateFromClient<T extends INotation | NewNotation | PartialUpdateNotation>(notation: T): RestOf<T> {
    return {
      ...notation,
      dateCreation: notation.dateCreation?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restNotation: RestNotation): INotation {
    return {
      ...restNotation,
      dateCreation: restNotation.dateCreation ? dayjs(restNotation.dateCreation) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestNotation>): HttpResponse<INotation> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestNotation[]>): HttpResponse<INotation[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
