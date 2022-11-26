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
    const hamburger = document.getElementById("btn-hamburger") as HTMLButtonElement;

    if (!hamburger.disabled) {
      hamburger.disabled = true;
      setTimeout(() => hamburger.disabled = false, 500);
    }

    this.showSidebar = !this.showSidebar;
  }
}
