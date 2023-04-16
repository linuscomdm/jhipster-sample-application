import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrestataire } from '../prestataire.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-prestataire-detail',
  templateUrl: './prestataire-detail.component.html',
})
export class PrestataireDetailComponent implements OnInit {
  prestataire: IPrestataire | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prestataire }) => {
      this.prestataire = prestataire;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
