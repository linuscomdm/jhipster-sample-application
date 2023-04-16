import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILangue, NewLangue } from '../langue.model';

export type PartialUpdateLangue = Partial<ILangue> & Pick<ILangue, 'id'>;

export type EntityResponseType = HttpResponse<ILangue>;
export type EntityArrayResponseType = HttpResponse<ILangue[]>;

@Injectable({ providedIn: 'root' })
export class LangueService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/langues');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(langue: NewLangue): Observable<EntityResponseType> {
    return this.http.post<ILangue>(this.resourceUrl, langue, { observe: 'response' });
  }

  update(langue: ILangue): Observable<EntityResponseType> {
    return this.http.put<ILangue>(`${this.resourceUrl}/${this.getLangueIdentifier(langue)}`, langue, { observe: 'response' });
  }

  partialUpdate(langue: PartialUpdateLangue): Observable<EntityResponseType> {
    return this.http.patch<ILangue>(`${this.resourceUrl}/${this.getLangueIdentifier(langue)}`, langue, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILangue>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILangue[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLangueIdentifier(langue: Pick<ILangue, 'id'>): number {
    return langue.id;
  }

  compareLangue(o1: Pick<ILangue, 'id'> | null, o2: Pick<ILangue, 'id'> | null): boolean {
    return o1 && o2 ? this.getLangueIdentifier(o1) === this.getLangueIdentifier(o2) : o1 === o2;
  }

  addLangueToCollectionIfMissing<Type extends Pick<ILangue, 'id'>>(
    langueCollection: Type[],
    ...languesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const langues: Type[] = languesToCheck.filter(isPresent);
    if (langues.length > 0) {
      const langueCollectionIdentifiers = langueCollection.map(langueItem => this.getLangueIdentifier(langueItem)!);
      const languesToAdd = langues.filter(langueItem => {
        const langueIdentifier = this.getLangueIdentifier(langueItem);
        if (langueCollectionIdentifiers.includes(langueIdentifier)) {
          return false;
        }
        langueCollectionIdentifiers.push(langueIdentifier);
        return true;
      });
      return [...languesToAdd, ...langueCollection];
    }
    return langueCollection;
  }
}
