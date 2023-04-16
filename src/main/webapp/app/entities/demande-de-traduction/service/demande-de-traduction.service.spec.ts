import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDemandeDeTraduction } from '../demande-de-traduction.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../demande-de-traduction.test-samples';

import { DemandeDeTraductionService, RestDemandeDeTraduction } from './demande-de-traduction.service';

const requireRestSample: RestDemandeDeTraduction = {
  ...sampleWithRequiredData,
  dateCreation: sampleWithRequiredData.dateCreation?.format(DATE_FORMAT),
  dateCloture: sampleWithRequiredData.dateCloture?.format(DATE_FORMAT),
};

describe('DemandeDeTraduction Service', () => {
  let service: DemandeDeTraductionService;
  let httpMock: HttpTestingController;
  let expectedResult: IDemandeDeTraduction | IDemandeDeTraduction[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DemandeDeTraductionService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a DemandeDeTraduction', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const demandeDeTraduction = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(demandeDeTraduction).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DemandeDeTraduction', () => {
      const demandeDeTraduction = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(demandeDeTraduction).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DemandeDeTraduction', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DemandeDeTraduction', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DemandeDeTraduction', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDemandeDeTraductionToCollectionIfMissing', () => {
      it('should add a DemandeDeTraduction to an empty array', () => {
        const demandeDeTraduction: IDemandeDeTraduction = sampleWithRequiredData;
        expectedResult = service.addDemandeDeTraductionToCollectionIfMissing([], demandeDeTraduction);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demandeDeTraduction);
      });

      it('should not add a DemandeDeTraduction to an array that contains it', () => {
        const demandeDeTraduction: IDemandeDeTraduction = sampleWithRequiredData;
        const demandeDeTraductionCollection: IDemandeDeTraduction[] = [
          {
            ...demandeDeTraduction,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDemandeDeTraductionToCollectionIfMissing(demandeDeTraductionCollection, demandeDeTraduction);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DemandeDeTraduction to an array that doesn't contain it", () => {
        const demandeDeTraduction: IDemandeDeTraduction = sampleWithRequiredData;
        const demandeDeTraductionCollection: IDemandeDeTraduction[] = [sampleWithPartialData];
        expectedResult = service.addDemandeDeTraductionToCollectionIfMissing(demandeDeTraductionCollection, demandeDeTraduction);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demandeDeTraduction);
      });

      it('should add only unique DemandeDeTraduction to an array', () => {
        const demandeDeTraductionArray: IDemandeDeTraduction[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const demandeDeTraductionCollection: IDemandeDeTraduction[] = [sampleWithRequiredData];
        expectedResult = service.addDemandeDeTraductionToCollectionIfMissing(demandeDeTraductionCollection, ...demandeDeTraductionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const demandeDeTraduction: IDemandeDeTraduction = sampleWithRequiredData;
        const demandeDeTraduction2: IDemandeDeTraduction = sampleWithPartialData;
        expectedResult = service.addDemandeDeTraductionToCollectionIfMissing([], demandeDeTraduction, demandeDeTraduction2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demandeDeTraduction);
        expect(expectedResult).toContain(demandeDeTraduction2);
      });

      it('should accept null and undefined values', () => {
        const demandeDeTraduction: IDemandeDeTraduction = sampleWithRequiredData;
        expectedResult = service.addDemandeDeTraductionToCollectionIfMissing([], null, demandeDeTraduction, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demandeDeTraduction);
      });

      it('should return initial array if no DemandeDeTraduction is added', () => {
        const demandeDeTraductionCollection: IDemandeDeTraduction[] = [sampleWithRequiredData];
        expectedResult = service.addDemandeDeTraductionToCollectionIfMissing(demandeDeTraductionCollection, undefined, null);
        expect(expectedResult).toEqual(demandeDeTraductionCollection);
      });
    });

    describe('compareDemandeDeTraduction', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDemandeDeTraduction(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDemandeDeTraduction(entity1, entity2);
        const compareResult2 = service.compareDemandeDeTraduction(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDemandeDeTraduction(entity1, entity2);
        const compareResult2 = service.compareDemandeDeTraduction(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDemandeDeTraduction(entity1, entity2);
        const compareResult2 = service.compareDemandeDeTraduction(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
