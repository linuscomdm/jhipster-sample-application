import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILangue } from '../langue.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../langue.test-samples';

import { LangueService } from './langue.service';

const requireRestSample: ILangue = {
  ...sampleWithRequiredData,
};

describe('Langue Service', () => {
  let service: LangueService;
  let httpMock: HttpTestingController;
  let expectedResult: ILangue | ILangue[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LangueService);
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

    it('should create a Langue', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const langue = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(langue).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Langue', () => {
      const langue = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(langue).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Langue', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Langue', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Langue', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLangueToCollectionIfMissing', () => {
      it('should add a Langue to an empty array', () => {
        const langue: ILangue = sampleWithRequiredData;
        expectedResult = service.addLangueToCollectionIfMissing([], langue);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(langue);
      });

      it('should not add a Langue to an array that contains it', () => {
        const langue: ILangue = sampleWithRequiredData;
        const langueCollection: ILangue[] = [
          {
            ...langue,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLangueToCollectionIfMissing(langueCollection, langue);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Langue to an array that doesn't contain it", () => {
        const langue: ILangue = sampleWithRequiredData;
        const langueCollection: ILangue[] = [sampleWithPartialData];
        expectedResult = service.addLangueToCollectionIfMissing(langueCollection, langue);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(langue);
      });

      it('should add only unique Langue to an array', () => {
        const langueArray: ILangue[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const langueCollection: ILangue[] = [sampleWithRequiredData];
        expectedResult = service.addLangueToCollectionIfMissing(langueCollection, ...langueArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const langue: ILangue = sampleWithRequiredData;
        const langue2: ILangue = sampleWithPartialData;
        expectedResult = service.addLangueToCollectionIfMissing([], langue, langue2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(langue);
        expect(expectedResult).toContain(langue2);
      });

      it('should accept null and undefined values', () => {
        const langue: ILangue = sampleWithRequiredData;
        expectedResult = service.addLangueToCollectionIfMissing([], null, langue, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(langue);
      });

      it('should return initial array if no Langue is added', () => {
        const langueCollection: ILangue[] = [sampleWithRequiredData];
        expectedResult = service.addLangueToCollectionIfMissing(langueCollection, undefined, null);
        expect(expectedResult).toEqual(langueCollection);
      });
    });

    describe('compareLangue', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLangue(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLangue(entity1, entity2);
        const compareResult2 = service.compareLangue(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLangue(entity1, entity2);
        const compareResult2 = service.compareLangue(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLangue(entity1, entity2);
        const compareResult2 = service.compareLangue(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
