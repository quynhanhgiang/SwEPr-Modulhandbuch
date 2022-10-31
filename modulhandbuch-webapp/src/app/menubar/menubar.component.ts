import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-menubar',
  templateUrl: './menubar.component.html',
  styleUrls: ['./menubar.component.scss']
})
export class MenubarComponent implements OnInit {

  showSidebar: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  toggleSidebar() {
    this.showSidebar = !this.showSidebar;
  }
}
