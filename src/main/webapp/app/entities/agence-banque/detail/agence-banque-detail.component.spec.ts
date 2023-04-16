import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AgenceBanqueDetailComponent } from './agence-banque-detail.component';

describe('AgenceBanque Management Detail Component', () => {
  let comp: AgenceBanqueDetailComponent;
  let fixture: ComponentFixture<AgenceBanqueDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AgenceBanqueDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ agenceBanque: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AgenceBanqueDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AgenceBanqueDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load agenceBanque on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.agenceBanque).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
