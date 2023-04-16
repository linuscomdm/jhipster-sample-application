import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NotationDetailComponent } from './notation-detail.component';

describe('Notation Management Detail Component', () => {
  let comp: NotationDetailComponent;
  let fixture: ComponentFixture<NotationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NotationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ notation: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NotationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NotationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load notation on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.notation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
