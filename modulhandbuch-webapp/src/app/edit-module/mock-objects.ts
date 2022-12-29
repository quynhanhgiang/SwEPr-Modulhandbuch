import { Assignment } from "../shared/Assignments";
import { CollegeEmployee } from "../shared/CollegeEmployee";
import { Module } from "../shared/module";
import { ModuleManual } from "../shared/module-manual";

export const moduleManuals: ModuleManual[] = [
{
      id: 1,
      semester: "Wintersemester 2022/2023",
      spo: {
        id: 1,
        link: "https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO%20B%20IF%204.pdf",
        degree: "Bachelor",
        course: "Informatik",
        startDate: "2020-10-01",
        endDate: null
      }
    },
    {
      id: 2,
      semester: "Sommersemester 2023",
      spo: {
        id: 2,
        link: "https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO_B_IF_neu.pdf",
        degree: "Bachelor",
        course: "Informatik",
        startDate: "2014-10-01",
        endDate: "2020-09-30"
      }
    }
]

export const profs: CollegeEmployee[] = [
    {
        id: 1,
        firstName: "Dieter",
        lastName: "Landes",
        title: "Prof. Dr.",
        gender: "Sehr geehrter",
        email: "Dieter.Landes@hs-coburg.de"
      },
      {
        id: 2,
        firstName: "Volkard",
        lastName: "Pfeiffer",
        title: "Prof.",
        gender: "Sehr geehrter",
        email: "Volkard.Pfeiffer@hs-coburg.de"
      },
]

export const moduleOwners: CollegeEmployee[] = [
    {
        id: 1,
        firstName: "Dieter",
        lastName: "Landes",
        title: "Prof. Dr.",
        gender: "Sehr geehrter",
        email: "Dieter.Landes@hs-coburg.de"
      },
      {
        id: 2,
        firstName: "Volkard",
        lastName: "Pfeiffer",
        title: "Prof.",
        gender: "Sehr geehrter",
        email: "Volkard.Pfeiffer@hs-coburg.de"
      },
]

export const cycles: string[] = [
    "Halbjährlich",
    "Jährlich"
]

export const durations: string[] = [
    "Einsemestrig",
    "Zweisemestrig"
]

export const languages: string[] = [
    "Deutsch",
    "Englisch",
    "Spanisch"
]

export const maternityProtections: string[] = [
    "Grün",
    "Gelb",
    "Rot"
]

export const segments: Assignment[] = [
  {
    id: 0,
    value: "1. Studienabschnitt"
  },
  {
    id: 1,
    value: "2. Studienabschnitt"
  },
  {
    id: 2,
    value: "3. Studienabschnitt"
  }
]

export const requirements: Assignment[] = [
  {
    id: 0,
    value: "Zulassungsvorraussetzung nach §4 Abs. 1"
  },
  {
    id: 1,
    value: "Zulassungsvorraussetzung nach §4 Abs. 2"
  },
  {
    id: 2,
    value: "Zulassungsvorraussetzung nach §5 Abs. 1"
  },
  {
    id: 3,
    value: "Zulassungsvorraussetzung nach §5 Abs. 2"
  }
]

export const types: Assignment[] = [
  {
    id: 0,
    value: "Wahlpflichtfach"
  },
  {
    id: 1,
    value: "Pflichtfach"
  },
  {
    id: 2,
    value: "Praktikum"
  },
  {
    id: 3,
    value: "Schlüsselqualifikation"
  }
]

export const module: Module =
    {
      id: 0,
      moduleName: "Analysis",
      abbreviation: "Ana",
      variations: [
        {
          manual: {
            id: 1,
            semester: "Wintersemester 2022/2023",
            spo: {
              id: 1,
              link: "https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO%20B%20IF%204.pdf",
              degree: "Bachelor",
              course: "Informatik",
              startDate: "2020-10-01",
              endDate: null
            }
          },
          ects: 7,
          sws: 6,
          workLoad: "<p><span style=\"color: rgb(0, 0, 0);\">90 h Präsenz (Seminaristischer Unterricht mit integrierten Übungen)</span></p><p><br></p><p><span style=\"color: rgb(0, 0, 0);\">120 h Eigenarbeit (40 h Nachbereitung des Lehrstoffs, 30 h Bearbeitung von Übungsaufgaben, 50 h Prüfungsvorbereitung)</span></p>",
          semester: 1,
          moduleType: "Pflichtfach",
          admissionRequirement: "1",
          segment: "1. Studienabschnitt"
        }
      ],
      cycle: "Jährlich",
      duration: "Einsemestrig",
      moduleOwner: {
        id: 2,
        firstName: "Volkard",
        lastName: "Pfeiffer",
        title: "Prof.",
        gender: "Sehr geehrter",
        email: "Volkard.Pfeiffer@hs-coburg.de"
      },
      profs: [
        {
          id: 1,
          firstName: "Dieter",
          lastName: "Landes",
          title: "Prof. Dr.",
          gender: "Sehr geehrter",
          email: "Dieter.Landes@hs-coburg.de"
        }
      ],
      language: "Deutsch",
      usage: "",
      knowledgeRequirements: "",
      skills: "<p><span style=\"color: rgb(0, 0, 0);\">Studierende sollen wesentliche Grundlagen der Analysis bis hin zur Differentialrechnung kennen und anwenden können.</span></p>",
      content: "<p><span style=\"color: rgb(0, 0, 0);\">Logik, Mengenlehre, Vollständige Induktion, Kombinatorik, rationale und reelle Zahlen, komplexe Zahlen, Folgen und Grenzwerte, Reihen, Funktionen und Stetigkeit, Differenzierbarkeit, Sätze der Differenzialrechnung Extremwerte, Taylorentwicklung</span></p>",
      examType: "Schriftliche Prüfung (120 Minuten)",
      certificates: "",
      mediaType: "Tafel, Skript",
      literature: "<p><span style=\"color: rgb(0, 0, 0);\">T. Arens et al., „Mathematik“, Spektrum, Heidelberg, 2008 </span></p><p><span style=\"color: rgb(51, 51, 51);\">G. Teschl, S. Teschl, „Mathematik für Informatiker“, Band 1 und 2, Springer Spektrum Berlin, Heidelberg, 2013</span></p><p><span style=\"color: rgb(51, 51, 51);\">W. Struckmann, D. Wätjen, „Mathematik für Informatiker“, Springer Vieweg Berlin, Heidelberg, 2016</span></p><p><span style=\"color: rgb(51, 51, 51);\">R. Berghammer, „Mathematik für Informatiker“, Springer Vieweg Wiesbaden, 2014</span></p><p>&nbsp;</p><p><span style=\"color: rgb(51, 51, 51);\">E. Weitz, „Konkrete Mathematik (nicht nur) für Informatiker“, Springer Spektrum Wiesbaden, 2018</span></p><p>&nbsp;</p><p><span style=\"color: rgb(0, 0, 0);\">O. Forster, „Analysis 1“, Vieweg, Wiesbaden, 2004</span></p>",
      maternityProtection: "Grün"
    }
