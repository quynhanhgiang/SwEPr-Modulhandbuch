import { Assignment } from "../shared/Assignments";
import { FileStatus } from "../shared/FileStatus";
import { ModuleManual } from "../shared/module-manual";
import { Spo } from "../shared/spo";

export const sposMock: Spo[] = [
  {
    id: 1,
    link: "https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO%20B%20IF%204.pdf",
    startDate: "2020-10-01",
    endDate: null,
    course: "Informatik",
    degree: "Bachelor"
  },
  {
    id: 2,
    link: "https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO_B_IF_neu.pdf",
    startDate: "2014-10-01",
    endDate: "2020-09-30",
    course: "Informatik",
    degree: "Bachelor"
  },
  {
    id: 3,
    link: "https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO_B_IF_alt.pdf",
    startDate: "2009-07-22",
    endDate: "2014-09-30",
    course: "Informatik",
    degree: "Bachelor"
  },
  {
    id: 4,
    link: "https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO%20B%20EL%201_0.pdf",
    startDate: "2020-10-01",
    endDate: null,
    course: "Elektro- und Informationstechnik",
    degree: "Bachelor"
  },
  {
    id: 5,
    link: "https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO_B_EL_neu.pdf",
    startDate: "2017-10-01",
    endDate: "2020-09-30",
    course: "Elektro- und Informationstechnik",
    degree: "Bachelor"
  },
  {
    id: 6,
    link: "https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO%20B%20VC.pdf",
    startDate: "2020-10-01",
    endDate: null,
    course: "Visual Computing",
    degree: "Bachelor"
  },
  {
    id: 7,
    link: "https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO%20B%20EN%201.pdf",
    startDate: "2020-10-01",
    endDate: null,
    course: "Energietechnik und Erneuerbare Energien",
    degree: "Bachelor"
  },
  {
    id: 8,
    link: "https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO_B_EN.pdf",
    startDate: "2017-10-01",
    endDate: "2020-09-30",
    course: "Energietechnik und Erneuerbare Energien",
    degree: "Bachelor"
  },
  {
    id: 9,
    link: "https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO%20B%20AU%202_0.pdf",
    startDate: "2020-10-01",
    endDate: null,
    course: "Automatisierungstechnik und Robotik",
    degree: "Bachelor"
  },
  {
    id: 10,
    link: "",
    startDate: "2017-10-01",
    endDate: "2020-09-30",
    course: "Automatisierungstechnik und Robotik",
    degree: "Bachelor"
  }
];

export const moduleManualMock: ModuleManual = {
  id: 1,
  semester: "Sommersemester 2023",
  spo: sposMock[0]
};

export const availableStatusMock: FileStatus = {
  filename: "test-file.pdf",
  link: "https://test.url",
  timestamp: "01.12.2022 11:30 Uhr"
};

export const nullStatusMock: FileStatus = {
  filename: null,
  link: null,
  timestamp: null
};

export const segmentsMock: Assignment[] = [
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
];

export const moduleTypesMock: Assignment[] = [
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
];

export const requirementsMock: Assignment[] = [
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
];
