import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAgenceBanque, NewAgenceBanque } from '../agence-banque.model';

export type PartialUpdateAgenceBanque = Partial<IAgenceBanque> & Pick<IAgenceBanque, 'id'>;

export type EntityResponseType = HttpResponse<IAgenceBanque>;
export type EntityArrayResponseType = HttpResponse<IAgenceBanque[]>;

@Injectable({ providedIn: 'root' })
export class AgenceBanqueService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/agence-banques');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(agenceBanque: NewAgenceBanque): Observable<EntityResponseType> {
    return this.http.post<IAgenceBanque>(this.resourceUrl, agenceBanque, { observe: 'response' });
  }

  update(agenceBanque: IAgenceBanque): Observable<EntityResponseType> {
    return this.http.put<IAgenceBanque>(`${this.resourceUrl}/${this.getAgenceBanqueIdentifier(agenceBanque)}`, agenceBanque, {
      observe: 'response',
    });
  }

  partialUpdate(agenceBanque: PartialUpdateAgenceBanque): Observable<EntityResponseType> {
    return this.http.patch<IAgenceBanque>(`${this.resourceUrl}/${this.getAgenceBanqueIdentifier(agenceBanque)}`, agenceBanque, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAgenceBanque>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAgenceBanque[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAgenceBanqueIdentifier(agenceBanque: Pick<IAgenceBanque, 'id'>): number {
    return agenceBanque.id;
  }

  compareAgenceBanque(o1: Pick<IAgenceBanque, 'id'> | null, o2: Pick<IAgenceBanque, 'id'> | null): boolean {
    return o1 && o2 ? this.getAgenceBanqueIdentifier(o1) === this.getAgenceBanqueIdentifier(o2) : o1 === o2;
  }

  addAgenceBanqueToCollectionIfMissing<Type extends Pick<IAgenceBanque, 'id'>>(
    agenceBanqueCollection: Type[],
    ...agenceBanquesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const agenceBanques: Type[] = agenceBanquesToCheck.filter(isPresent);
    if (agenceBanques.length > 0) {
      const agenceBanqueCollectionIdentifiers = agenceBanqueCollection.map(
        agenceBanqueItem => this.getAgenceBanqueIdentifier(agenceBanqueItem)!
      );
      const agenceBanquesToAdd = agenceBanques.filter(agenceBanqueItem => {
        const agenceBanqueIdentifier = this.getAgenceBanqueIdentifier(agenceBanqueItem);
        if (agenceBanqueCollectionIdentifiers.includes(agenceBanqueIdentifier)) {
          return false;
        }
        agenceBanqueCollectionIdentifiers.push(agenceBanqueIdentifier);
        return true;
      });
      return [...agenceBanquesToAdd, ...agenceBanqueCollection];
    }
    return agenceBanqueCollection;
  }
}
