import { CollegeEmployee } from "../shared/CollegeEmployee";
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