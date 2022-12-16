import { FlatModule } from "../shared/FlatModule";
import { ManualVariation } from "../shared/ManualVariation";
import { ModuleVariation } from "../shared/ModuleVariation";

export const assignableModules: FlatModule[] = [
  {
    id: 0,
    abbreviation: "Ana",
    moduleName: "Analysis",
    moduleOwner: "Prof. Dr. Ada Bäumner"
  },
  {
    id: 1,
    abbreviation: "Prog1",
    moduleName: "Programmieren 1",
    moduleOwner: "Prof. Volkhard Pfeiffer"
  },
  {
    id: 2,
    abbreviation: "Prog2",
    moduleName: "Programmieren 2",
    moduleOwner: "Prof. Volkhard Pfeiffer"
  },
  {
    id: 3,
    abbreviation: "FProg",
    moduleName: "Fortgeschrittene Programmierung",
    moduleOwner: "Prof. Dr. Thomas Wieland"
  },
  {
    id: 4,
    abbreviation: "SAT",
    moduleName: "Software-Architekturen und -Testen",
    moduleOwner: "Prof. Volkhard Pfeiffer"
  },
  {
    id: 5,
    abbreviation: "SpI",
    moduleName: "Studienprojekt praktische Informatik",
    moduleOwner: "Prof. Dr. Thomas Wieland"
  },
]


export const invalidManualVariations: ManualVariation[] = [
  {
    module: {
      id: 6,
      abbreviation: "CN",
      moduleName: "Computernetze",
      moduleOwner: "Prof. Dr. Thomas Wieland"
    },
    semester: null,
    sws: 6,
    ects: 30,
    workLoad: "60h Präsenz, 90h Selbststudium",
    moduleType: "Pflichtfach",
    segment: "1. Studienabschnitt",
    admissionRequirement: "Zulassungsvorraussetzung nach §xy Abs. z",
    isAssigned: true
  },
  {
    module: {
      id: 7,
      abbreviation: "ITS",
      moduleName: "IT-Sicherheit",
      moduleOwner: "Prof. Dr. Thomas Wieland"
    },
    semester: null,
    sws: null,
    ects: null,
    workLoad: null,
    moduleType: null,
    segment: null,
    admissionRequirement: null,
    isAssigned: false
  },
];

export const assignedModules: ManualVariation[] = [
  {
    module: {
      id: 6,
      abbreviation: "CN",
      moduleName: "Computernetze",
      moduleOwner: "Prof. Dr. Thomas Wieland"
    },
    semester: 2,
    sws: 6,
    ects: 30,
    workLoad: "60h Präsenz, 90h Selbststudium",
    moduleType: "Pflichtfach",
    segment: "1. Studienabschnitt",
    admissionRequirement: "Zulassungsvorraussetzung nach §xy Abs. z",
    isAssigned: true
  },
  {
    module: {
      id: 7,
      abbreviation: "ITS",
      moduleName: "IT-Sicherheit",
      moduleOwner: "Prof. Dr. Thomas Wieland"
    },
    semester: 4,
    sws: 6,
    ects: 30,
    workLoad: "60h Präsenz, 90h Selbststudium",
    moduleType: "Wahlpflichtfach",
    segment: "2. Studienabschnitt",
    admissionRequirement: "Zulassungsvorraussetzung nach §xy Abs. z",
    isAssigned: true
  },
  {
    module: {
      id: 8,
      abbreviation: "SAM",
      moduleName: "Softwaren-Anforderungen und -Modellierung",
      moduleOwner: "Prof. Dr. Landes"
    },
    semester: 2,
    sws: 6,
    ects: 30,
    workLoad: "60h Präsenz, 90h Selbststudium",
    moduleType: "Wahlpflichtfach",
    segment: "2. Studienabschnitt",
    admissionRequirement: "Zulassungsvorraussetzung nach §xy Abs. z",
    isAssigned: true
  },
  {
    module: {
      id: 9,
      abbreviation: "Db",
      moduleName: "Datenbanksysteme",
      moduleOwner: " Prof. Dr. Jürgen Terpin"
    },
    semester: 2,
    sws: 6,
    ects: 30,
    workLoad: "60h Präsenz, 90h Selbststudium",
    moduleType: "Pflichtfach",
    segment: "1. Studienabschnitt",
    admissionRequirement: "Zulassungsvorraussetzung nach §xy Abs. z",
    isAssigned: true
  },
  {
    module: {
      id: 10,
      abbreviation: "GI",
      moduleName: "Grundlagen der Informatik",
      moduleOwner: "Prof. Dr. Dieter Landes"
    },
    semester: 1,
    sws: 6,
    ects: 30,
    workLoad: "60h Präsenz, 90h Selbststudium",
    moduleType: "Pflichtfach",
    segment: "1. Studienabschnitt",
    admissionRequirement: "Zulassungsvorraussetzung nach §xy Abs. z",
    isAssigned: true
  },
  {
    module: {
      id: 11,
      abbreviation: "RA",
      moduleName: "Rechnerarchitekturen",
      moduleOwner: "Prof. Dr. Quirin Meyer"
    },
    semester: 1,
    sws: 6,
    ects: 30,
    workLoad: "60h Präsenz, 90h Selbststudium",
    moduleType: "Pflichtfach",
    segment: "1. Studienabschnitt",
    admissionRequirement: "Zulassungsvorraussetzung nach §xy Abs. z",
    isAssigned: true
  },
  {
    module: {
      id: 12,
      abbreviation: "ISem",
      moduleName: "Informatik-Seminar",
      moduleOwner: "Prof. Volkhard Pfeiffer"
    },
    semester: 4,
    sws: 6,
    ects: 30,
    workLoad: "60h Präsenz, 90h Selbststudium",
    moduleType: "Pflichtfach",
    segment: "2. Studienabschnitt",
    admissionRequirement: "Zulassungsvorraussetzung nach §xy Abs. z",
    isAssigned: true
  }
]


