import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetailDevis } from '../detail-devis.model';

@Component({
  selector: 'jhi-detail-devis-detail',
  templateUrl: './detail-devis-detail.component.html',
})
export class DetailDevisDetailComponent implements OnInit {
  detailDevis: IDetailDevis | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detailDevis }) => {
      this.detailDevis = detailDevis;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
