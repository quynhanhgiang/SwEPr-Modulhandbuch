import { Component, OnInit } from '@angular/core';
import { Router, Event, NavigationEnd} from '@angular/router';

@Component({
  selector: 'app-dummy',
  templateUrl: './dummy.component.html',
  styleUrls: ['./dummy.component.scss']
})
export class DummyComponent implements OnInit {

  title: String = "Startseite";

  constructor(private router: Router) { 
    this.router.events.subscribe((event: Event) => {
        if (event instanceof NavigationEnd) {
          console.log(event.url);
           switch (event.url) {
            case "/home":
              this.title = "Startseite";
              break;
            case "/module-manuals":
              this.title = "Modulhandbücher";
              break;
            case "/module-management":
              this.title = "Modulverwaltung";
              break;
            case "/user-management":
              this.title = "Nutzerverwaltung";
              break;
            case "/placeholder1":
              this.title = "Platzhalter Numero Uno";
              break;
            case "/placeholder2":
              this.title = "Ei Ei Ei Ei Ei";
              break;
           }
        }
    });
  }

  ngOnInit(): void {
  }

}
