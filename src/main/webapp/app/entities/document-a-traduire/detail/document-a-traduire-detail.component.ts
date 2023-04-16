import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocumentATraduire } from '../document-a-traduire.model';

@Component({
  selector: 'jhi-document-a-traduire-detail',
  templateUrl: './document-a-traduire-detail.component.html',
})
export class DocumentATraduireDetailComponent implements OnInit {
  documentATraduire: IDocumentATraduire | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentATraduire }) => {
      this.documentATraduire = documentATraduire;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
