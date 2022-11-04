import { Injectable } from '@angular/core';

@Injectable()
export class ModuleService {

    getModules() {
        return [
            {id: 0, abbreviation: "Ana", moduleName: "Analysis", moduleOwner: "Prof. Dr. Ada Bäumner"},
            {id: 1, abbreviation: "Prog1", moduleName: "Programmieren 1", moduleOwner: "Prof. Volkhard Pfeiffer"},
            {id: 2, abbreviation: "Prog2", moduleName: "Programmieren 2", moduleOwner: "Prof. Volkhard Pfeiffer"},
            {id: 3, abbreviation: "FProg", moduleName: "Fortgeschrittene Programmierung", moduleOwner: "Prof. Dr. Thomas Wieland"},
            {id: 4, abbreviation: "SAT", moduleName: "Software-Architekturen und -Testen", moduleOwner: "Prof. Volkhard Pfeiffer"},
            {id: 5, abbreviation: "SpI", moduleName: "Studienprojekt praktische Informatik", moduleOwner: "Prof. Dr. Thomas Wieland"},
            {id: 6, abbreviation: "CN", moduleName: "Computernetze", moduleOwner: "Prof. Dr. Thomas Wieland"},
            {id: 7, abbreviation: "ITS", moduleName: "IT-Sicherheit", moduleOwner: "Prof. Dr. Thomas Wieland"},
            {id: 8, abbreviation: "SAM", moduleName: "Softwaren-Anforderungen und -Modellierung", moduleOwner: "Prof. Dr. Landes"},
            {id: 9, abbreviation: "Db", moduleName: "Datenbanksysteme", moduleOwner:" Prof. Dr. Jürgen Terpin"},
            {id: 10, abbreviation: "GI", moduleName: "Grundlagen der Informatik", moduleOwner: "Prof. Dr. Dieter Landes"},
            {id: 11, abbreviation: "RA", moduleName: "Rechnerarchitekturen", moduleOwner: "Prof. Dr. Quirin Meyer"},
            {id: 12, abbreviation: "ISem", moduleName: "Informatik-Seminar", moduleOwner: "Prof. Volkhard Pfeiffer"}
          ];
    }
}