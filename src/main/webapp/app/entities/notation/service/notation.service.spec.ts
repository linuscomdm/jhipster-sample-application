import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { INotation } from '../notation.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../notation.test-samples';

import { NotationService, RestNotation } from './notation.service';

const requireRestSample: RestNotation = {
  ...sampleWithRequiredData,
  dateCreation: sampleWithRequiredData.dateCreation?.format(DATE_FORMAT),
};

describe('Notation Service', () => {
  let service: NotationService;
  let httpMock: HttpTestingController;
  let expectedResult: INotation | INotation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NotationService);
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

    it('should create a Notation', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const notation = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(notation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Notation', () => {
      const notation = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(notation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Notation', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Notation', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Notation', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNotationToCollectionIfMissing', () => {
      it('should add a Notation to an empty array', () => {
        const notation: INotation = sampleWithRequiredData;
        expectedResult = service.addNotationToCollectionIfMissing([], notation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(notation);
      });

      it('should not add a Notation to an array that contains it', () => {
        const notation: INotation = sampleWithRequiredData;
        const notationCollection: INotation[] = [
          {
            ...notation,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNotationToCollectionIfMissing(notationCollection, notation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Notation to an array that doesn't contain it", () => {
        const notation: INotation = sampleWithRequiredData;
        const notationCollection: INotation[] = [sampleWithPartialData];
        expectedResult = service.addNotationToCollectionIfMissing(notationCollection, notation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(notation);
      });

      it('should add only unique Notation to an array', () => {
        const notationArray: INotation[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const notationCollection: INotation[] = [sampleWithRequiredData];
        expectedResult = service.addNotationToCollectionIfMissing(notationCollection, ...notationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const notation: INotation = sampleWithRequiredData;
        const notation2: INotation = sampleWithPartialData;
        expectedResult = service.addNotationToCollectionIfMissing([], notation, notation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(notation);
        expect(expectedResult).toContain(notation2);
      });

      it('should accept null and undefined values', () => {
        const notation: INotation = sampleWithRequiredData;
        expectedResult = service.addNotationToCollectionIfMissing([], null, notation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(notation);
      });

      it('should return initial array if no Notation is added', () => {
        const notationCollection: INotation[] = [sampleWithRequiredData];
        expectedResult = service.addNotationToCollectionIfMissing(notationCollection, undefined, null);
        expect(expectedResult).toEqual(notationCollection);
      });
    });

    describe('compareNotation', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNotation(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNotation(entity1, entity2);
        const compareResult2 = service.compareNotation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareNotation(entity1, entity2);
        const compareResult2 = service.compareNotation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareNotation(entity1, entity2);
        const compareResult2 = service.compareNotation(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
