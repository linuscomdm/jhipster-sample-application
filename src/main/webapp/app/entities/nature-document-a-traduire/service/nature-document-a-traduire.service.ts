import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INatureDocumentATraduire, NewNatureDocumentATraduire } from '../nature-document-a-traduire.model';

export type PartialUpdateNatureDocumentATraduire = Partial<INatureDocumentATraduire> & Pick<INatureDocumentATraduire, 'id'>;

export type EntityResponseType = HttpResponse<INatureDocumentATraduire>;
export type EntityArrayResponseType = HttpResponse<INatureDocumentATraduire[]>;

@Injectable({ providedIn: 'root' })
export class NatureDocumentATraduireService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nature-document-a-traduires');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(natureDocumentATraduire: NewNatureDocumentATraduire): Observable<EntityResponseType> {
    return this.http.post<INatureDocumentATraduire>(this.resourceUrl, natureDocumentATraduire, { observe: 'response' });
  }

  update(natureDocumentATraduire: INatureDocumentATraduire): Observable<EntityResponseType> {
    return this.http.put<INatureDocumentATraduire>(
      `${this.resourceUrl}/${this.getNatureDocumentATraduireIdentifier(natureDocumentATraduire)}`,
      natureDocumentATraduire,
      { observe: 'response' }
    );
  }

  partialUpdate(natureDocumentATraduire: PartialUpdateNatureDocumentATraduire): Observable<EntityResponseType> {
    return this.http.patch<INatureDocumentATraduire>(
      `${this.resourceUrl}/${this.getNatureDocumentATraduireIdentifier(natureDocumentATraduire)}`,
      natureDocumentATraduire,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INatureDocumentATraduire>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INatureDocumentATraduire[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNatureDocumentATraduireIdentifier(natureDocumentATraduire: Pick<INatureDocumentATraduire, 'id'>): number {
    return natureDocumentATraduire.id;
  }

  compareNatureDocumentATraduire(
    o1: Pick<INatureDocumentATraduire, 'id'> | null,
    o2: Pick<INatureDocumentATraduire, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getNatureDocumentATraduireIdentifier(o1) === this.getNatureDocumentATraduireIdentifier(o2) : o1 === o2;
  }

  addNatureDocumentATraduireToCollectionIfMissing<Type extends Pick<INatureDocumentATraduire, 'id'>>(
    natureDocumentATraduireCollection: Type[],
    ...natureDocumentATraduiresToCheck: (Type | null | undefined)[]
  ): Type[] {
    const natureDocumentATraduires: Type[] = natureDocumentATraduiresToCheck.filter(isPresent);
    if (natureDocumentATraduires.length > 0) {
      const natureDocumentATraduireCollectionIdentifiers = natureDocumentATraduireCollection.map(
        natureDocumentATraduireItem => this.getNatureDocumentATraduireIdentifier(natureDocumentATraduireItem)!
      );
      const natureDocumentATraduiresToAdd = natureDocumentATraduires.filter(natureDocumentATraduireItem => {
        const natureDocumentATraduireIdentifier = this.getNatureDocumentATraduireIdentifier(natureDocumentATraduireItem);
        if (natureDocumentATraduireCollectionIdentifiers.includes(natureDocumentATraduireIdentifier)) {
          return false;
        }
        natureDocumentATraduireCollectionIdentifiers.push(natureDocumentATraduireIdentifier);
        return true;
      });
      return [...natureDocumentATraduiresToAdd, ...natureDocumentATraduireCollection];
    }
    return natureDocumentATraduireCollection;
  }
}
