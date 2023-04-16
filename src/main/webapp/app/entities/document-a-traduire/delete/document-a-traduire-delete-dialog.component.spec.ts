jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { DocumentATraduireService } from '../service/document-a-traduire.service';

import { DocumentATraduireDeleteDialogComponent } from './document-a-traduire-delete-dialog.component';

describe('DocumentATraduire Management Delete Component', () => {
  let comp: DocumentATraduireDeleteDialogComponent;
  let fixture: ComponentFixture<DocumentATraduireDeleteDialogComponent>;
  let service: DocumentATraduireService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DocumentATraduireDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(DocumentATraduireDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DocumentATraduireDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DocumentATraduireService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
