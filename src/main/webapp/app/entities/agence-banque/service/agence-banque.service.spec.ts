import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAgenceBanque } from '../agence-banque.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../agence-banque.test-samples';

import { AgenceBanqueService } from './agence-banque.service';

const requireRestSample: IAgenceBanque = {
  ...sampleWithRequiredData,
};

describe('AgenceBanque Service', () => {
  let service: AgenceBanqueService;
  let httpMock: HttpTestingController;
  let expectedResult: IAgenceBanque | IAgenceBanque[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AgenceBanqueService);
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

    it('should create a AgenceBanque', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const agenceBanque = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(agenceBanque).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AgenceBanque', () => {
      const agenceBanque = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(agenceBanque).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AgenceBanque', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AgenceBanque', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AgenceBanque', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAgenceBanqueToCollectionIfMissing', () => {
      it('should add a AgenceBanque to an empty array', () => {
        const agenceBanque: IAgenceBanque = sampleWithRequiredData;
        expectedResult = service.addAgenceBanqueToCollectionIfMissing([], agenceBanque);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agenceBanque);
      });

      it('should not add a AgenceBanque to an array that contains it', () => {
        const agenceBanque: IAgenceBanque = sampleWithRequiredData;
        const agenceBanqueCollection: IAgenceBanque[] = [
          {
            ...agenceBanque,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAgenceBanqueToCollectionIfMissing(agenceBanqueCollection, agenceBanque);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AgenceBanque to an array that doesn't contain it", () => {
        const agenceBanque: IAgenceBanque = sampleWithRequiredData;
        const agenceBanqueCollection: IAgenceBanque[] = [sampleWithPartialData];
        expectedResult = service.addAgenceBanqueToCollectionIfMissing(agenceBanqueCollection, agenceBanque);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agenceBanque);
      });

      it('should add only unique AgenceBanque to an array', () => {
        const agenceBanqueArray: IAgenceBanque[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const agenceBanqueCollection: IAgenceBanque[] = [sampleWithRequiredData];
        expectedResult = service.addAgenceBanqueToCollectionIfMissing(agenceBanqueCollection, ...agenceBanqueArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const agenceBanque: IAgenceBanque = sampleWithRequiredData;
        const agenceBanque2: IAgenceBanque = sampleWithPartialData;
        expectedResult = service.addAgenceBanqueToCollectionIfMissing([], agenceBanque, agenceBanque2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agenceBanque);
        expect(expectedResult).toContain(agenceBanque2);
      });

      it('should accept null and undefined values', () => {
        const agenceBanque: IAgenceBanque = sampleWithRequiredData;
        expectedResult = service.addAgenceBanqueToCollectionIfMissing([], null, agenceBanque, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agenceBanque);
      });

      it('should return initial array if no AgenceBanque is added', () => {
        const agenceBanqueCollection: IAgenceBanque[] = [sampleWithRequiredData];
        expectedResult = service.addAgenceBanqueToCollectionIfMissing(agenceBanqueCollection, undefined, null);
        expect(expectedResult).toEqual(agenceBanqueCollection);
      });
    });

    describe('compareAgenceBanque', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAgenceBanque(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAgenceBanque(entity1, entity2);
        const compareResult2 = service.compareAgenceBanque(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAgenceBanque(entity1, entity2);
        const compareResult2 = service.compareAgenceBanque(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAgenceBanque(entity1, entity2);
        const compareResult2 = service.compareAgenceBanque(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
