import { Component } from '@angular/core';
import { LoadingService } from 'src/app/services/loading.service';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss'],
})
export class LoadingComponent {
  loading = false;

  constructor(private loadingService: LoadingService) {
    this.loadingService.loadingStatus.subscribe((value) => {
      this.loading = value;
    });
  }
}
