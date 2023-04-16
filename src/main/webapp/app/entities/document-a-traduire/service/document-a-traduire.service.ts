import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDocumentATraduire, NewDocumentATraduire } from '../document-a-traduire.model';

export type PartialUpdateDocumentATraduire = Partial<IDocumentATraduire> & Pick<IDocumentATraduire, 'id'>;

export type EntityResponseType = HttpResponse<IDocumentATraduire>;
export type EntityArrayResponseType = HttpResponse<IDocumentATraduire[]>;

@Injectable({ providedIn: 'root' })
export class DocumentATraduireService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/document-a-traduires');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(documentATraduire: NewDocumentATraduire): Observable<EntityResponseType> {
    return this.http.post<IDocumentATraduire>(this.resourceUrl, documentATraduire, { observe: 'response' });
  }

  update(documentATraduire: IDocumentATraduire): Observable<EntityResponseType> {
    return this.http.put<IDocumentATraduire>(
      `${this.resourceUrl}/${this.getDocumentATraduireIdentifier(documentATraduire)}`,
      documentATraduire,
      { observe: 'response' }
    );
  }

  partialUpdate(documentATraduire: PartialUpdateDocumentATraduire): Observable<EntityResponseType> {
    return this.http.patch<IDocumentATraduire>(
      `${this.resourceUrl}/${this.getDocumentATraduireIdentifier(documentATraduire)}`,
      documentATraduire,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDocumentATraduire>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDocumentATraduire[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDocumentATraduireIdentifier(documentATraduire: Pick<IDocumentATraduire, 'id'>): number {
    return documentATraduire.id;
  }

  compareDocumentATraduire(o1: Pick<IDocumentATraduire, 'id'> | null, o2: Pick<IDocumentATraduire, 'id'> | null): boolean {
    return o1 && o2 ? this.getDocumentATraduireIdentifier(o1) === this.getDocumentATraduireIdentifier(o2) : o1 === o2;
  }

  addDocumentATraduireToCollectionIfMissing<Type extends Pick<IDocumentATraduire, 'id'>>(
    documentATraduireCollection: Type[],
    ...documentATraduiresToCheck: (Type | null | undefined)[]
  ): Type[] {
    const documentATraduires: Type[] = documentATraduiresToCheck.filter(isPresent);
    if (documentATraduires.length > 0) {
      const documentATraduireCollectionIdentifiers = documentATraduireCollection.map(
        documentATraduireItem => this.getDocumentATraduireIdentifier(documentATraduireItem)!
      );
      const documentATraduiresToAdd = documentATraduires.filter(documentATraduireItem => {
        const documentATraduireIdentifier = this.getDocumentATraduireIdentifier(documentATraduireItem);
        if (documentATraduireCollectionIdentifiers.includes(documentATraduireIdentifier)) {
          return false;
        }
        documentATraduireCollectionIdentifiers.push(documentATraduireIdentifier);
        return true;
      });
      return [...documentATraduiresToAdd, ...documentATraduireCollection];
    }
    return documentATraduireCollection;
  }
}
