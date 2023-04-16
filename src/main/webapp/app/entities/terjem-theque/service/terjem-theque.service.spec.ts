import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITerjemTheque } from '../terjem-theque.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../terjem-theque.test-samples';

import { TerjemThequeService, RestTerjemTheque } from './terjem-theque.service';

const requireRestSample: RestTerjemTheque = {
  ...sampleWithRequiredData,
  dateCreation: sampleWithRequiredData.dateCreation?.format(DATE_FORMAT),
};

describe('TerjemTheque Service', () => {
  let service: TerjemThequeService;
  let httpMock: HttpTestingController;
  let expectedResult: ITerjemTheque | ITerjemTheque[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TerjemThequeService);
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

    it('should create a TerjemTheque', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const terjemTheque = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(terjemTheque).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TerjemTheque', () => {
      const terjemTheque = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(terjemTheque).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TerjemTheque', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TerjemTheque', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TerjemTheque', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTerjemThequeToCollectionIfMissing', () => {
      it('should add a TerjemTheque to an empty array', () => {
        const terjemTheque: ITerjemTheque = sampleWithRequiredData;
        expectedResult = service.addTerjemThequeToCollectionIfMissing([], terjemTheque);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(terjemTheque);
      });

      it('should not add a TerjemTheque to an array that contains it', () => {
        const terjemTheque: ITerjemTheque = sampleWithRequiredData;
        const terjemThequeCollection: ITerjemTheque[] = [
          {
            ...terjemTheque,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTerjemThequeToCollectionIfMissing(terjemThequeCollection, terjemTheque);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TerjemTheque to an array that doesn't contain it", () => {
        const terjemTheque: ITerjemTheque = sampleWithRequiredData;
        const terjemThequeCollection: ITerjemTheque[] = [sampleWithPartialData];
        expectedResult = service.addTerjemThequeToCollectionIfMissing(terjemThequeCollection, terjemTheque);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(terjemTheque);
      });

      it('should add only unique TerjemTheque to an array', () => {
        const terjemThequeArray: ITerjemTheque[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const terjemThequeCollection: ITerjemTheque[] = [sampleWithRequiredData];
        expectedResult = service.addTerjemThequeToCollectionIfMissing(terjemThequeCollection, ...terjemThequeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const terjemTheque: ITerjemTheque = sampleWithRequiredData;
        const terjemTheque2: ITerjemTheque = sampleWithPartialData;
        expectedResult = service.addTerjemThequeToCollectionIfMissing([], terjemTheque, terjemTheque2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(terjemTheque);
        expect(expectedResult).toContain(terjemTheque2);
      });

      it('should accept null and undefined values', () => {
        const terjemTheque: ITerjemTheque = sampleWithRequiredData;
        expectedResult = service.addTerjemThequeToCollectionIfMissing([], null, terjemTheque, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(terjemTheque);
      });

      it('should return initial array if no TerjemTheque is added', () => {
        const terjemThequeCollection: ITerjemTheque[] = [sampleWithRequiredData];
        expectedResult = service.addTerjemThequeToCollectionIfMissing(terjemThequeCollection, undefined, null);
        expect(expectedResult).toEqual(terjemThequeCollection);
      });
    });

    describe('compareTerjemTheque', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTerjemTheque(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTerjemTheque(entity1, entity2);
        const compareResult2 = service.compareTerjemTheque(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTerjemTheque(entity1, entity2);
        const compareResult2 = service.compareTerjemTheque(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTerjemTheque(entity1, entity2);
        const compareResult2 = service.compareTerjemTheque(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
