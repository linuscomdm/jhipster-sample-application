import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDetailDevis, NewDetailDevis } from '../detail-devis.model';

export type PartialUpdateDetailDevis = Partial<IDetailDevis> & Pick<IDetailDevis, 'id'>;

export type EntityResponseType = HttpResponse<IDetailDevis>;
export type EntityArrayResponseType = HttpResponse<IDetailDevis[]>;

@Injectable({ providedIn: 'root' })
export class DetailDevisService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/detail-devis');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(detailDevis: NewDetailDevis): Observable<EntityResponseType> {
    return this.http.post<IDetailDevis>(this.resourceUrl, detailDevis, { observe: 'response' });
  }

  update(detailDevis: IDetailDevis): Observable<EntityResponseType> {
    return this.http.put<IDetailDevis>(`${this.resourceUrl}/${this.getDetailDevisIdentifier(detailDevis)}`, detailDevis, {
      observe: 'response',
    });
  }

  partialUpdate(detailDevis: PartialUpdateDetailDevis): Observable<EntityResponseType> {
    return this.http.patch<IDetailDevis>(`${this.resourceUrl}/${this.getDetailDevisIdentifier(detailDevis)}`, detailDevis, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDetailDevis>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDetailDevis[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDetailDevisIdentifier(detailDevis: Pick<IDetailDevis, 'id'>): number {
    return detailDevis.id;
  }

  compareDetailDevis(o1: Pick<IDetailDevis, 'id'> | null, o2: Pick<IDetailDevis, 'id'> | null): boolean {
    return o1 && o2 ? this.getDetailDevisIdentifier(o1) === this.getDetailDevisIdentifier(o2) : o1 === o2;
  }

  addDetailDevisToCollectionIfMissing<Type extends Pick<IDetailDevis, 'id'>>(
    detailDevisCollection: Type[],
    ...detailDevisToCheck: (Type | null | undefined)[]
  ): Type[] {
    const detailDevis: Type[] = detailDevisToCheck.filter(isPresent);
    if (detailDevis.length > 0) {
      const detailDevisCollectionIdentifiers = detailDevisCollection.map(
        detailDevisItem => this.getDetailDevisIdentifier(detailDevisItem)!
      );
      const detailDevisToAdd = detailDevis.filter(detailDevisItem => {
        const detailDevisIdentifier = this.getDetailDevisIdentifier(detailDevisItem);
        if (detailDevisCollectionIdentifiers.includes(detailDevisIdentifier)) {
          return false;
        }
        detailDevisCollectionIdentifiers.push(detailDevisIdentifier);
        return true;
      });
      return [...detailDevisToAdd, ...detailDevisCollection];
    }
    return detailDevisCollection;
  }
}
