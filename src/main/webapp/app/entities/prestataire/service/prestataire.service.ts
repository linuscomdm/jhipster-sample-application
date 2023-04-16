import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPrestataire, NewPrestataire } from '../prestataire.model';

export type PartialUpdatePrestataire = Partial<IPrestataire> & Pick<IPrestataire, 'id'>;

type RestOf<T extends IPrestataire | NewPrestataire> = Omit<T, 'dateCreation'> & {
  dateCreation?: string | null;
};

export type RestPrestataire = RestOf<IPrestataire>;

export type NewRestPrestataire = RestOf<NewPrestataire>;

export type PartialUpdateRestPrestataire = RestOf<PartialUpdatePrestataire>;

export type EntityResponseType = HttpResponse<IPrestataire>;
export type EntityArrayResponseType = HttpResponse<IPrestataire[]>;

@Injectable({ providedIn: 'root' })
export class PrestataireService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/prestataires');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(prestataire: NewPrestataire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prestataire);
    return this.http
      .post<RestPrestataire>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(prestataire: IPrestataire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prestataire);
    return this.http
      .put<RestPrestataire>(`${this.resourceUrl}/${this.getPrestataireIdentifier(prestataire)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(prestataire: PartialUpdatePrestataire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prestataire);
    return this.http
      .patch<RestPrestataire>(`${this.resourceUrl}/${this.getPrestataireIdentifier(prestataire)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPrestataire>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPrestataire[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPrestataireIdentifier(prestataire: Pick<IPrestataire, 'id'>): number {
    return prestataire.id;
  }

  comparePrestataire(o1: Pick<IPrestataire, 'id'> | null, o2: Pick<IPrestataire, 'id'> | null): boolean {
    return o1 && o2 ? this.getPrestataireIdentifier(o1) === this.getPrestataireIdentifier(o2) : o1 === o2;
  }

  addPrestataireToCollectionIfMissing<Type extends Pick<IPrestataire, 'id'>>(
    prestataireCollection: Type[],
    ...prestatairesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const prestataires: Type[] = prestatairesToCheck.filter(isPresent);
    if (prestataires.length > 0) {
      const prestataireCollectionIdentifiers = prestataireCollection.map(
        prestataireItem => this.getPrestataireIdentifier(prestataireItem)!
      );
      const prestatairesToAdd = prestataires.filter(prestataireItem => {
        const prestataireIdentifier = this.getPrestataireIdentifier(prestataireItem);
        if (prestataireCollectionIdentifiers.includes(prestataireIdentifier)) {
          return false;
        }
        prestataireCollectionIdentifiers.push(prestataireIdentifier);
        return true;
      });
      return [...prestatairesToAdd, ...prestataireCollection];
    }
    return prestataireCollection;
  }

  protected convertDateFromClient<T extends IPrestataire | NewPrestataire | PartialUpdatePrestataire>(prestataire: T): RestOf<T> {
    return {
      ...prestataire,
      dateCreation: prestataire.dateCreation?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restPrestataire: RestPrestataire): IPrestataire {
    return {
      ...restPrestataire,
      dateCreation: restPrestataire.dateCreation ? dayjs(restPrestataire.dateCreation) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPrestataire>): HttpResponse<IPrestataire> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPrestataire[]>): HttpResponse<IPrestataire[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
