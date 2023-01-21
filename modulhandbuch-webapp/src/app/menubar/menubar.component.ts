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

  /**
   * Toggles the visibility of the sidebar.
   */
  toggleSidebar() {
    const hamburger = document.getElementById("btn-hamburger") as HTMLButtonElement;

    // debounce hamburger-button (disable for 500ms)
    if (!hamburger.disabled) {
      hamburger.disabled = true;
      setTimeout(() => hamburger.disabled = false, 500);
    }

    this.showSidebar = !this.showSidebar;
  }
}
