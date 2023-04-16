import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgenceBanque } from '../agence-banque.model';

@Component({
  selector: 'jhi-agence-banque-detail',
  templateUrl: './agence-banque-detail.component.html',
})
export class AgenceBanqueDetailComponent implements OnInit {
  agenceBanque: IAgenceBanque | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agenceBanque }) => {
      this.agenceBanque = agenceBanque;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
