import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDetailDevis } from '../detail-devis.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../detail-devis.test-samples';

import { DetailDevisService } from './detail-devis.service';

const requireRestSample: IDetailDevis = {
  ...sampleWithRequiredData,
};

describe('DetailDevis Service', () => {
  let service: DetailDevisService;
  let httpMock: HttpTestingController;
  let expectedResult: IDetailDevis | IDetailDevis[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DetailDevisService);
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

    it('should create a DetailDevis', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const detailDevis = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(detailDevis).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DetailDevis', () => {
      const detailDevis = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(detailDevis).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DetailDevis', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DetailDevis', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DetailDevis', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDetailDevisToCollectionIfMissing', () => {
      it('should add a DetailDevis to an empty array', () => {
        const detailDevis: IDetailDevis = sampleWithRequiredData;
        expectedResult = service.addDetailDevisToCollectionIfMissing([], detailDevis);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detailDevis);
      });

      it('should not add a DetailDevis to an array that contains it', () => {
        const detailDevis: IDetailDevis = sampleWithRequiredData;
        const detailDevisCollection: IDetailDevis[] = [
          {
            ...detailDevis,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDetailDevisToCollectionIfMissing(detailDevisCollection, detailDevis);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DetailDevis to an array that doesn't contain it", () => {
        const detailDevis: IDetailDevis = sampleWithRequiredData;
        const detailDevisCollection: IDetailDevis[] = [sampleWithPartialData];
        expectedResult = service.addDetailDevisToCollectionIfMissing(detailDevisCollection, detailDevis);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detailDevis);
      });

      it('should add only unique DetailDevis to an array', () => {
        const detailDevisArray: IDetailDevis[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const detailDevisCollection: IDetailDevis[] = [sampleWithRequiredData];
        expectedResult = service.addDetailDevisToCollectionIfMissing(detailDevisCollection, ...detailDevisArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const detailDevis: IDetailDevis = sampleWithRequiredData;
        const detailDevis2: IDetailDevis = sampleWithPartialData;
        expectedResult = service.addDetailDevisToCollectionIfMissing([], detailDevis, detailDevis2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detailDevis);
        expect(expectedResult).toContain(detailDevis2);
      });

      it('should accept null and undefined values', () => {
        const detailDevis: IDetailDevis = sampleWithRequiredData;
        expectedResult = service.addDetailDevisToCollectionIfMissing([], null, detailDevis, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detailDevis);
      });

      it('should return initial array if no DetailDevis is added', () => {
        const detailDevisCollection: IDetailDevis[] = [sampleWithRequiredData];
        expectedResult = service.addDetailDevisToCollectionIfMissing(detailDevisCollection, undefined, null);
        expect(expectedResult).toEqual(detailDevisCollection);
      });
    });

    describe('compareDetailDevis', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDetailDevis(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDetailDevis(entity1, entity2);
        const compareResult2 = service.compareDetailDevis(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDetailDevis(entity1, entity2);
        const compareResult2 = service.compareDetailDevis(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDetailDevis(entity1, entity2);
        const compareResult2 = service.compareDetailDevis(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
