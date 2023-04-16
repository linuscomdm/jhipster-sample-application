import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemandeDeTraduction, NewDemandeDeTraduction } from '../demande-de-traduction.model';

export type PartialUpdateDemandeDeTraduction = Partial<IDemandeDeTraduction> & Pick<IDemandeDeTraduction, 'id'>;

type RestOf<T extends IDemandeDeTraduction | NewDemandeDeTraduction> = Omit<T, 'dateCreation' | 'dateCloture'> & {
  dateCreation?: string | null;
  dateCloture?: string | null;
};

export type RestDemandeDeTraduction = RestOf<IDemandeDeTraduction>;

export type NewRestDemandeDeTraduction = RestOf<NewDemandeDeTraduction>;

export type PartialUpdateRestDemandeDeTraduction = RestOf<PartialUpdateDemandeDeTraduction>;

export type EntityResponseType = HttpResponse<IDemandeDeTraduction>;
export type EntityArrayResponseType = HttpResponse<IDemandeDeTraduction[]>;

@Injectable({ providedIn: 'root' })
export class DemandeDeTraductionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demande-de-traductions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(demandeDeTraduction: NewDemandeDeTraduction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeDeTraduction);
    return this.http
      .post<RestDemandeDeTraduction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(demandeDeTraduction: IDemandeDeTraduction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeDeTraduction);
    return this.http
      .put<RestDemandeDeTraduction>(`${this.resourceUrl}/${this.getDemandeDeTraductionIdentifier(demandeDeTraduction)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(demandeDeTraduction: PartialUpdateDemandeDeTraduction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandeDeTraduction);
    return this.http
      .patch<RestDemandeDeTraduction>(`${this.resourceUrl}/${this.getDemandeDeTraductionIdentifier(demandeDeTraduction)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDemandeDeTraduction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDemandeDeTraduction[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDemandeDeTraductionIdentifier(demandeDeTraduction: Pick<IDemandeDeTraduction, 'id'>): number {
    return demandeDeTraduction.id;
  }

  compareDemandeDeTraduction(o1: Pick<IDemandeDeTraduction, 'id'> | null, o2: Pick<IDemandeDeTraduction, 'id'> | null): boolean {
    return o1 && o2 ? this.getDemandeDeTraductionIdentifier(o1) === this.getDemandeDeTraductionIdentifier(o2) : o1 === o2;
  }

  addDemandeDeTraductionToCollectionIfMissing<Type extends Pick<IDemandeDeTraduction, 'id'>>(
    demandeDeTraductionCollection: Type[],
    ...demandeDeTraductionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const demandeDeTraductions: Type[] = demandeDeTraductionsToCheck.filter(isPresent);
    if (demandeDeTraductions.length > 0) {
      const demandeDeTraductionCollectionIdentifiers = demandeDeTraductionCollection.map(
        demandeDeTraductionItem => this.getDemandeDeTraductionIdentifier(demandeDeTraductionItem)!
      );
      const demandeDeTraductionsToAdd = demandeDeTraductions.filter(demandeDeTraductionItem => {
        const demandeDeTraductionIdentifier = this.getDemandeDeTraductionIdentifier(demandeDeTraductionItem);
        if (demandeDeTraductionCollectionIdentifiers.includes(demandeDeTraductionIdentifier)) {
          return false;
        }
        demandeDeTraductionCollectionIdentifiers.push(demandeDeTraductionIdentifier);
        return true;
      });
      return [...demandeDeTraductionsToAdd, ...demandeDeTraductionCollection];
    }
    return demandeDeTraductionCollection;
  }

  protected convertDateFromClient<T extends IDemandeDeTraduction | NewDemandeDeTraduction | PartialUpdateDemandeDeTraduction>(
    demandeDeTraduction: T
  ): RestOf<T> {
    return {
      ...demandeDeTraduction,
      dateCreation: demandeDeTraduction.dateCreation?.format(DATE_FORMAT) ?? null,
      dateCloture: demandeDeTraduction.dateCloture?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restDemandeDeTraduction: RestDemandeDeTraduction): IDemandeDeTraduction {
    return {
      ...restDemandeDeTraduction,
      dateCreation: restDemandeDeTraduction.dateCreation ? dayjs(restDemandeDeTraduction.dateCreation) : undefined,
      dateCloture: restDemandeDeTraduction.dateCloture ? dayjs(restDemandeDeTraduction.dateCloture) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDemandeDeTraduction>): HttpResponse<IDemandeDeTraduction> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDemandeDeTraduction[]>): HttpResponse<IDemandeDeTraduction[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
