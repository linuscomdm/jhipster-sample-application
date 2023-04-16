import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DocumentATraduireDetailComponent } from './document-a-traduire-detail.component';

describe('DocumentATraduire Management Detail Component', () => {
  let comp: DocumentATraduireDetailComponent;
  let fixture: ComponentFixture<DocumentATraduireDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DocumentATraduireDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ documentATraduire: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DocumentATraduireDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DocumentATraduireDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load documentATraduire on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.documentATraduire).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
