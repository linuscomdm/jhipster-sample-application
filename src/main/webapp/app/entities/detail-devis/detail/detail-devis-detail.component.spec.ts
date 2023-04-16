import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DetailDevisDetailComponent } from './detail-devis-detail.component';

describe('DetailDevis Management Detail Component', () => {
  let comp: DetailDevisDetailComponent;
  let fixture: ComponentFixture<DetailDevisDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetailDevisDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ detailDevis: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DetailDevisDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DetailDevisDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load detailDevis on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.detailDevis).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
