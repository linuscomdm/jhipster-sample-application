import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DemandeDeTraductionDetailComponent } from './demande-de-traduction-detail.component';

describe('DemandeDeTraduction Management Detail Component', () => {
  let comp: DemandeDeTraductionDetailComponent;
  let fixture: ComponentFixture<DemandeDeTraductionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DemandeDeTraductionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ demandeDeTraduction: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DemandeDeTraductionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DemandeDeTraductionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load demandeDeTraduction on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.demandeDeTraduction).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
