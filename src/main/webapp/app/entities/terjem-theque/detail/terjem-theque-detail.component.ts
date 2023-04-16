import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITerjemTheque } from '../terjem-theque.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-terjem-theque-detail',
  templateUrl: './terjem-theque-detail.component.html',
})
export class TerjemThequeDetailComponent implements OnInit {
  terjemTheque: ITerjemTheque | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ terjemTheque }) => {
      this.terjemTheque = terjemTheque;
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
