import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NatureDocumentATraduireDetailComponent } from './nature-document-a-traduire-detail.component';

describe('NatureDocumentATraduire Management Detail Component', () => {
  let comp: NatureDocumentATraduireDetailComponent;
  let fixture: ComponentFixture<NatureDocumentATraduireDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NatureDocumentATraduireDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ natureDocumentATraduire: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NatureDocumentATraduireDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NatureDocumentATraduireDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load natureDocumentATraduire on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.natureDocumentATraduire).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
