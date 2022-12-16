import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-edit-manual',
  templateUrl: './edit-manual.component.html',
  styleUrls: ['./edit-manual.component.scss']
})
export class EditManualComponent implements OnInit {
  id: number = 0;
  idLoaded: boolean = false;
  private routeSub!: Subscription;

  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.routeSub = this.activatedRoute.params.subscribe(params => {
      this.id = params['id'] as number;
      this.idLoaded = true;
    });
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }
}
