import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INatureDocumentATraduire } from '../nature-document-a-traduire.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../nature-document-a-traduire.test-samples';

import { NatureDocumentATraduireService } from './nature-document-a-traduire.service';

const requireRestSample: INatureDocumentATraduire = {
  ...sampleWithRequiredData,
};

describe('NatureDocumentATraduire Service', () => {
  let service: NatureDocumentATraduireService;
  let httpMock: HttpTestingController;
  let expectedResult: INatureDocumentATraduire | INatureDocumentATraduire[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NatureDocumentATraduireService);
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

    it('should create a NatureDocumentATraduire', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const natureDocumentATraduire = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(natureDocumentATraduire).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NatureDocumentATraduire', () => {
      const natureDocumentATraduire = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(natureDocumentATraduire).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NatureDocumentATraduire', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NatureDocumentATraduire', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a NatureDocumentATraduire', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNatureDocumentATraduireToCollectionIfMissing', () => {
      it('should add a NatureDocumentATraduire to an empty array', () => {
        const natureDocumentATraduire: INatureDocumentATraduire = sampleWithRequiredData;
        expectedResult = service.addNatureDocumentATraduireToCollectionIfMissing([], natureDocumentATraduire);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(natureDocumentATraduire);
      });

      it('should not add a NatureDocumentATraduire to an array that contains it', () => {
        const natureDocumentATraduire: INatureDocumentATraduire = sampleWithRequiredData;
        const natureDocumentATraduireCollection: INatureDocumentATraduire[] = [
          {
            ...natureDocumentATraduire,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNatureDocumentATraduireToCollectionIfMissing(
          natureDocumentATraduireCollection,
          natureDocumentATraduire
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NatureDocumentATraduire to an array that doesn't contain it", () => {
        const natureDocumentATraduire: INatureDocumentATraduire = sampleWithRequiredData;
        const natureDocumentATraduireCollection: INatureDocumentATraduire[] = [sampleWithPartialData];
        expectedResult = service.addNatureDocumentATraduireToCollectionIfMissing(
          natureDocumentATraduireCollection,
          natureDocumentATraduire
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(natureDocumentATraduire);
      });

      it('should add only unique NatureDocumentATraduire to an array', () => {
        const natureDocumentATraduireArray: INatureDocumentATraduire[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const natureDocumentATraduireCollection: INatureDocumentATraduire[] = [sampleWithRequiredData];
        expectedResult = service.addNatureDocumentATraduireToCollectionIfMissing(
          natureDocumentATraduireCollection,
          ...natureDocumentATraduireArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const natureDocumentATraduire: INatureDocumentATraduire = sampleWithRequiredData;
        const natureDocumentATraduire2: INatureDocumentATraduire = sampleWithPartialData;
        expectedResult = service.addNatureDocumentATraduireToCollectionIfMissing([], natureDocumentATraduire, natureDocumentATraduire2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(natureDocumentATraduire);
        expect(expectedResult).toContain(natureDocumentATraduire2);
      });

      it('should accept null and undefined values', () => {
        const natureDocumentATraduire: INatureDocumentATraduire = sampleWithRequiredData;
        expectedResult = service.addNatureDocumentATraduireToCollectionIfMissing([], null, natureDocumentATraduire, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(natureDocumentATraduire);
      });

      it('should return initial array if no NatureDocumentATraduire is added', () => {
        const natureDocumentATraduireCollection: INatureDocumentATraduire[] = [sampleWithRequiredData];
        expectedResult = service.addNatureDocumentATraduireToCollectionIfMissing(natureDocumentATraduireCollection, undefined, null);
        expect(expectedResult).toEqual(natureDocumentATraduireCollection);
      });
    });

    describe('compareNatureDocumentATraduire', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNatureDocumentATraduire(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNatureDocumentATraduire(entity1, entity2);
        const compareResult2 = service.compareNatureDocumentATraduire(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareNatureDocumentATraduire(entity1, entity2);
        const compareResult2 = service.compareNatureDocumentATraduire(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareNatureDocumentATraduire(entity1, entity2);
        const compareResult2 = service.compareNatureDocumentATraduire(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
