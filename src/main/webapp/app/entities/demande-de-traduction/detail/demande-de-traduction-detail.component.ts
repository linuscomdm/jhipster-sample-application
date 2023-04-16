import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDemandeDeTraduction } from '../demande-de-traduction.model';

@Component({
  selector: 'jhi-demande-de-traduction-detail',
  templateUrl: './demande-de-traduction-detail.component.html',
})
export class DemandeDeTraductionDetailComponent implements OnInit {
  demandeDeTraduction: IDemandeDeTraduction | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeDeTraduction }) => {
      this.demandeDeTraduction = demandeDeTraduction;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
