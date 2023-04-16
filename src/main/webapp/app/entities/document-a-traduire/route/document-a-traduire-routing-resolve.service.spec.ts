import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDocumentATraduire } from '../document-a-traduire.model';
import { DocumentATraduireService } from '../service/document-a-traduire.service';

import { DocumentATraduireRoutingResolveService } from './document-a-traduire-routing-resolve.service';

describe('DocumentATraduire routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DocumentATraduireRoutingResolveService;
  let service: DocumentATraduireService;
  let resultDocumentATraduire: IDocumentATraduire | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(DocumentATraduireRoutingResolveService);
    service = TestBed.inject(DocumentATraduireService);
    resultDocumentATraduire = undefined;
  });

  describe('resolve', () => {
    it('should return IDocumentATraduire returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDocumentATraduire = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDocumentATraduire).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDocumentATraduire = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDocumentATraduire).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IDocumentATraduire>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDocumentATraduire = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDocumentATraduire).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
