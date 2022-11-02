import { Injectable } from '@angular/core';

@Injectable()
export class ModuleService {

    getModules() {//Dummy Methode später Zugriff auf REST-API um die realen Daten abzufragen
        return [
            {id:0,name:"analysis",prof:"geisler",semester:1},
            {id:1,name:"prog1",prof:"Pfeifer",semester:1},
            {id:2,name:"prog2",prof:"Pfeifer",semester:2},
            {id:3,name:"fprog",prof:"Wieland",semester:3},
            {id:4,name:"SAT",prof:"Pfeifer",semester:4},
            {id:5,name:"SPI",prof:"Wieland",semester:6},
            {id:6,name:"CN",prof:"Wieland",semester:3},

            {id:7,name:"ITS",prof:"Wieland",semester:3},
            {id:8,name:"SAM",prof:"Landes",semester:4},
            {id:9,name:"Datenbanken",prof:"Terpin",semester:4},
            {id:10,name:"Grundlagen der Informatik",prof:"Landes",semester:1},
            {id:11,name:"Rechnerarchitektur",prof:"Meier",semester:1},
            {id:12,name:"Informatik-Seminar",prof:"Grubert",semester:4},
            {id:13,name:"Prog1",prof:"Pfeifer",semester:1}
          ];
    }
}