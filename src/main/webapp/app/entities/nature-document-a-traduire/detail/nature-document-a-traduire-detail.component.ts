import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INatureDocumentATraduire } from '../nature-document-a-traduire.model';

@Component({
  selector: 'jhi-nature-document-a-traduire-detail',
  templateUrl: './nature-document-a-traduire-detail.component.html',
})
export class NatureDocumentATraduireDetailComponent implements OnInit {
  natureDocumentATraduire: INatureDocumentATraduire | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureDocumentATraduire }) => {
      this.natureDocumentATraduire = natureDocumentATraduire;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
