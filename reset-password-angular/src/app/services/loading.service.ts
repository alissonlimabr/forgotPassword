import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoadingService {
  constructor() {}

  private _loading = false;
  loadingStatus = new BehaviorSubject<boolean>(false);

  isLoading(): boolean {
    return this._loading;
  }

  setLoading(value: boolean) {
    this._loading = value;
    this.loadingStatus.next(value);
  }

  startLoading() {
    this.setLoading(true);
  }

  stopLoading() {
    this.setLoading(false);
  }
}
